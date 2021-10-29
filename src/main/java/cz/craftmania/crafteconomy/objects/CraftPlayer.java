package cz.craftmania.crafteconomy.objects;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.utils.Logger;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class CraftPlayer {

    // Player
    private Player player;

    // Economy
    private long coins = 0;
    private long tokens = 0;
    private long voteTokens = 0;

    // Experience and levels
    private long globalLevel = 1;
    private long survivalLevel = 1;
    private long survivalExperience = 0;
    private long skyblockLevel = 1;
    private long skyblockExperience = 0;
    private long creativeLevel = 1;
    private long creativeExperence = 0;
    private long prisonLevel = 1;
    private long prisonExperience = 0;
    private long vanillaLevel = 1;
    private long vanillaExperience = 0;
    private long anarchyLevel = 1;
    private long anarchyExperience = 0;

    // Old servers
    private long skycloudLevel = 1;
    private long skycloudExperience = 0;
    private long hardcoreVanillaLevel = 1;
    private long hardcoreVanillaExperience = 0;
    private long vanilla116Level = 1;
    private long vanilla116Experience = 0;

    // Votes
    private long weekVotes = 0;
    private long monthVotes = 0;
    private long totalVotes = 0;
    private long lastVote = 0;
    private long votePass = 0;

    // Vault economy
    private long serverMoney = -1;

    // Others
    private long karma = 0;
    private long questPoints = 0;
    private long eventPoints = 0;
    private long seasonPoints = 0;
    private HashSet<Multiplier> multipliers;
    private boolean isAfk = false;

    // Player settings
    private boolean payToggle = false;

    /**
     * Prázdný objekt
     */
    public CraftPlayer() {}

    /**
     * Vytvoří objekt {@link CraftPlayer} podle zadaného hráče a načte data z DB.
     * <b>Nepoužívat pro klasické použití.</b>
     *
     * @param player {@link Player}
     */
    @Deprecated
    public CraftPlayer(@NonNull final Player player) { //TODO: DDoS MySQL? xD
        this.player = player;
        this.multipliers = new HashSet<>();
        this.coins = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTCOINS, player.getUniqueId());
        this.tokens = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTTOKENS, player.getUniqueId());
        this.voteTokens = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.VOTETOKENS_2, player.getUniqueId());
        this.survivalLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SURVIVAL_LEVEL, player.getUniqueId());
        this.survivalExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SURVIVAL_EXPERIENCE, player.getUniqueId());
        this.skyblockLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SKYBLOCK_LEVEL, player.getUniqueId());
        this.skyblockExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SKYBLOCK_EXPERIENCE, player.getUniqueId());
        this.creativeLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.CREATIVE_LEVEL, player.getUniqueId());
        this.creativeExperence = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.CREATIVE_EXPERIENCE, player.getUniqueId());
        this.vanillaLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.VANILLA_LEVEL, player.getUniqueId());
        this.vanillaExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.VANILLA_EXPERIENCE, player.getUniqueId());
        this.skycloudLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SKYCLOUD_LEVEL, player.getUniqueId());
        this.skycloudExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SKYCLOUD_EXPERIENCE, player.getUniqueId());
        this.prisonLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.PRISON_LEVEL, player.getUniqueId());
        this.prisonExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.PRISON_EXPERIENCE, player.getUniqueId());
        this.questPoints = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.ACHIEVEMENT_POINTS, player.getUniqueId()); //TODO: Přepnout ve finále
        this.seasonPoints = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.SEASON_POINTS, player.getUniqueId());
        this.totalVotes = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.TOTAL_VOTES, player.getUniqueId());
        this.monthVotes = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.MONTH_VOTES, player.getUniqueId());
        this.weekVotes = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.WEEK_VOTES, player.getUniqueId());
        this.votePass = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.VOTE_PASS, player.getUniqueId());
        this.eventPoints = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.EVENT_POINTS, player.getUniqueId());
        this.hardcoreVanillaLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.HARDCORE_VANILLA_LEVEL, player.getUniqueId());
        this.hardcoreVanillaExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.HARDCORE_VANILLA_EXPERIENCE, player.getUniqueId());
        this.anarchyLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.ANARCHY_LEVEL, player.getUniqueId());
        this.anarchyExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.ANARCHY_EXPERIENCE, player.getUniqueId());
        this.vanilla116Level = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.VANILLA_116_LEVEL, player.getUniqueId());

        this.payToggle = (Main.getInstance().getMySQL().getSettings(player, "paytoggle") != 0);
        recalculateGlobalLevel();
    }

    /**
     * Metoda pro vytvoření objektu {@link CraftPlayer}
     *
     * @param player Zvolený hráč {@link Player}
     * @param coins Počáteční počet CraftCoins
     * @param tokens Počáteční počet CraftTokens
     * @param voteTokens Počáteční počet VoteTokens
     */
    public CraftPlayer(@NonNull final Player player, final long coins, final long tokens, final long voteTokens) {
        this.player = player;
        this.coins = coins;
        this.tokens = tokens;
        this.voteTokens = voteTokens;
        this.multipliers = new HashSet<>();
        recalculateGlobalLevel();
    }

    /**
     * Vrací bukkit hráče
     * @return {@link Player}
     */
    @NonNull
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Vrací UUID handlované serverem
     * @return {@link UUID}
     */
    @NonNull
    public UUID getUUID() {
        return this.player.getUniqueId();
    }

    @Deprecated
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Vrací počet CraftCoins
     *
     * @return {@link Long}
     */
    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getTokens() {
        return tokens;
    }

    public void setTokens(long tokens) {
        this.tokens = tokens;
    }

    public long getVoteTokens() {
        return voteTokens;
    }

    public void setVoteTokens(long voteTokens) {
        this.voteTokens = voteTokens;
    }

    public void setKarma(long karma) {
        this.karma = karma;
    }

    public long getKarma() {
        return karma;
    }

    public HashSet<Multiplier> getMultipliers() {
        return multipliers;
    }

    public void setMultipliers(HashSet<Multiplier> multipliers) {
        this.multipliers = multipliers;
    }

    public long getQuestPoints() {
        return questPoints;
    }

    public void setQuestPoints(long questPoints) {
        this.questPoints = questPoints;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public long getMonthVotes() {
        return monthVotes;
    }

    public long getWeekVotes() {
        return weekVotes;
    }

    public long getLastVoteTime() {
        return lastVote;
    }

    public long getEventPoints() {
        return eventPoints;
    }

    public void setEventPoints(long eventPoints) {
        this.eventPoints = eventPoints;
    }

    public long getSeasonPoints() {
        return seasonPoints;
    }

    public void setSeasonPoints(long seasonPoints) {
        this.seasonPoints = seasonPoints;
    }

    /**
     * Přidá 1 hlas k <b>week</b> votes v cache.
     */
    public void addWeekVote() {
        this.weekVotes++;
    }

    /**
     * Přidá 1 hlas k <b>month</b> votes v cache.
     */
    public void addMonthVote() {
        this.monthVotes++;
    }

    /**
     * Přidá 1 hlas k <b>total</b> votes v cache.
     */
    public void addTotalVote() {
        this.totalVotes++;
    }

    /**
     * Tato metoda přidá do cache všechny typy hasů - week, month, total a votepass
     * Alternative k {@link #addWeekVote()}, {@link #addMonthVote()} a {@link #addTotalVote()}.
     *
     */
    public void addVote() {
        this.weekVotes++;
        this.monthVotes++;
        this.totalVotes++;
        this.votePass++;
    }

    /**
     * Nastaví čas v ms posledního hlasování.
     * Používá se ke kontrole duplikací hlasů.
     *
     * @param last_vote Čas v ms
     */
    public void setLastVote(long last_vote) {
        this.lastVote = last_vote;
    }

    /**
     * Vrací hodnotu peněz na serveru z cache. (Vault)
     *
     * @return {@link Long}
     */
    public long getMoney() {
        return serverMoney;
    }

    /**
     * Nastavuje novou hodnotu peněz v cache. (Vault)
     *
     * @param serverMoney Počet peněz - musí být finální počet
     */
    public void setMoney(long serverMoney) {
        this.serverMoney = serverMoney;
    }

    /**
     * Vrací hodnotu, zda je hráč AFK nebo ne.
     *
     * @return {@link Boolean}
     */
    public boolean isAfk() {
        return isAfk;
    }

    /**
     * Nastaví zda je hráč AFK, když ano tak nebude dostávat expy
     * ve světě podle configu.
     *
     * @param afk {@link Boolean}
     */
    public void setAfk(boolean afk) {
        isAfk = afk;
    }

    public void setPayToggle(boolean payToggle) {
        this.payToggle = payToggle;
    }

    public boolean getPayToggle() {
        return payToggle;
    }

    /**
     * Vrací aktuální hodnotu VotePassu.
     * VotePass = 2x měsíční hlasy může tedy odpovídat hodnotě měsíčních hlasů.
     *
     * @return {@link Long}
     */
    public long getVotePass() {
        return votePass;
    }

    /**
     * Nastavuje počet hlasů pro aktuální VotePass
     *
     * @param votePass {@link Long}
     */
    public void setVotePass(long votePass) {
        this.votePass = votePass;
    }

    /**
     * Metoda rozlišující různé typy server levels.
     *
     * @param type Typ serveru {@link LevelType}
     * @return Level zvoleného serveru v {@link Long}
     * @throws IllegalStateException když {@link LevelType} neexistuje.
     */
    public long getLevelByType(@NonNull final LevelType type) {
        switch(type) {
            case GLOBAL_LEVEL:
                recalculateGlobalLevel();
                return this.globalLevel;
            case SURVIVAL_LEVEL:
                return this.survivalLevel;
            case SKYBLOCK_LEVEL:
                return this.skyblockLevel;
            case CREATIVE_LEVEL:
                return this.creativeLevel;
            case PRISON_LEVEL:
                return this.prisonLevel;
            case VANILLA_LEVEL:
                return this.vanillaLevel;
            case SKYCLOUD_LEVEL:
                return this.skycloudLevel;
            case HARDCORE_VANILLA_LEVEL:
                return this.hardcoreVanillaLevel;
            case ANARCHY_LEVEL:
                return this.anarchyLevel;
            case VANILLA_116_LEVEL:
                return this.vanilla116Level;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public void setLevelByType(@NonNull final LevelType type, final long level) {
        switch(type) {
            case GLOBAL_LEVEL:
                Logger.danger("Nelze nastavit global level!");
                break;
            case SURVIVAL_LEVEL:
                this.survivalLevel = level;
                break;
            case SKYBLOCK_LEVEL:
                this.skyblockLevel = level;
                break;
            case CREATIVE_LEVEL:
                this.creativeLevel = level;
                break;
            case PRISON_LEVEL:
                this.prisonLevel = level;
                break;
            case VANILLA_LEVEL:
                this.vanillaLevel = level;
                break;
            case SKYCLOUD_LEVEL:
                this.skycloudLevel = level;
                break;
            case HARDCORE_VANILLA_LEVEL:
                this.hardcoreVanillaLevel = level;
                break;
            case ANARCHY_LEVEL:
                this.anarchyLevel = level;
                break;
            case VANILLA_116_LEVEL:
                this.vanilla116Level = level;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public long getExperienceByType(@NonNull final LevelType type) {
        switch(type) {
            case SURVIVAL_EXPERIENCE:
                return this.survivalExperience;
            case SKYBLOCK_EXPERIENCE:
                return this.skyblockExperience;
            case CREATIVE_EXPERIENCE:
                return this.creativeExperence;
            case PRISON_EXPERIENCE:
                return this.prisonExperience;
            case VANILLA_EXPERIENCE:
                return this.vanillaExperience;
            case SKYCLOUD_EXPERIENCE:
                return this.skycloudExperience;
            case HARDCORE_VANILLA_EXPERIENCE:
                return this.hardcoreVanillaExperience;
            case ANARCHY_EXPERIENCE:
                return this.anarchyExperience;
            case VANILLA_116_EXPERIENCE:
                return this.vanilla116Experience;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public void setExperienceByType(@NonNull final LevelType type, final long experience) {
        switch(type) {
            case GLOBAL_EXPERIENCE:
                Logger.danger("Nelze nastavit global experience!");
                break;
            case SURVIVAL_EXPERIENCE:
                this.survivalExperience = experience;
                break;
            case SKYBLOCK_EXPERIENCE:
                this.skyblockExperience = experience;
                break;
            case CREATIVE_EXPERIENCE:
                this.creativeExperence = experience;
                break;
            case PRISON_EXPERIENCE:
                this.prisonExperience = experience;
                break;
            case VANILLA_EXPERIENCE:
                this.vanillaExperience = experience;
                break;
            case SKYCLOUD_EXPERIENCE:
                this.skycloudExperience = experience;
                break;
            case HARDCORE_VANILLA_EXPERIENCE:
                this.hardcoreVanillaExperience = experience;
                break;
            case ANARCHY_EXPERIENCE:
                this.anarchyExperience = experience;
                break;
            case VANILLA_116_EXPERIENCE:
                this.vanilla116Experience = experience;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    /**
     * Prepocitava globalni level v souctu (kde hrac musi mit min 2 level abys se hodnota změnila).
     */
    public void recalculateGlobalLevel() {
        long finalValue = 1;
        finalValue += canBeAdded(this.survivalLevel);
        finalValue += canBeAdded(this.skyblockLevel);
        finalValue += canBeAdded(this.creativeLevel);
        finalValue += canBeAdded(this.vanillaLevel);
        finalValue += canBeAdded(this.skycloudLevel);
        finalValue += canBeAdded(this.prisonLevel);
        finalValue += canBeAdded(this.hardcoreVanillaLevel);
        finalValue += canBeAdded(this.anarchyLevel);
        finalValue += canBeAdded(this.vanilla116Level);
        // Pokud je level větší jak 1, nezapočítávat default 1 level jinak by došlo k +1 navýšení získaných levelů.
        this.globalLevel = finalValue > 1 ? --finalValue : finalValue;
    }

    /**
     * Kontroluje zda se zadana hodnota muze precist k vysledny hodnote (global level)
     * @param amount Pocet levlu
     */
    private long canBeAdded(long amount) {
        if (amount > 1) {
            return amount;
        }
        return 0;
    }
}
