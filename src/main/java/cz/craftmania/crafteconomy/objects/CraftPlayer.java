package cz.craftmania.crafteconomy.objects;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.utils.Logger;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class CraftPlayer {

    // Player
    private Player player;

    // Economy
    private long craftCoins = 0;
    private long craftTokens = 0;
    private long voteTokens = 0;
    private long karmaPoints = 0;
    private long questPoints = 0;
    private long eventPoints = 0;
    private long seasonPoints = 0;
    private long parkourPoints = 0;

    // Experience and levels
    private long globalLevel = 1;
    private long survivalLevel = 1;
    private long survivalExperience = 0;
    private long survival118Level = 1;
    private long survival118Experience = 0;
    private long skyblockLevel = 1;
    private long skyblockExperience = 0;
    private long skyblock118Level = 1;
    private long skyblock118Experience = 0;
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
    private double serverMoney = -1;

    // Others
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
        this.craftCoins = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFT_COINS, player.getUniqueId());
        this.craftTokens = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFT_TOKENS, player.getUniqueId());
        this.voteTokens = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.VOTE_TOKENS_2, player.getUniqueId());
        this.survivalLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SURVIVAL_117_LEVEL, player.getUniqueId());
        this.survivalExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SURVIVAL_117_EXPERIENCE, player.getUniqueId());
        this.skyblockLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SKYBLOCK_117_LEVEL, player.getUniqueId());
        this.skyblockExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SKYBLOCK_117_EXPERIENCE, player.getUniqueId());
        this.creativeLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.CREATIVE_LEVEL, player.getUniqueId());
        this.creativeExperence = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.CREATIVE_EXPERIENCE, player.getUniqueId());
        this.vanillaLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.VANILLA_LEVEL, player.getUniqueId());
        this.vanillaExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.VANILLA_EXPERIENCE, player.getUniqueId());
        this.skycloudLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SKYCLOUD_LEVEL, player.getUniqueId());
        this.skycloudExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.SKYCLOUD_EXPERIENCE, player.getUniqueId());
        this.prisonLevel = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.PRISON_LEVEL, player.getUniqueId());
        this.prisonExperience = Main.getInstance().getMySQL().getPlayerEconomy(LevelType.PRISON_EXPERIENCE, player.getUniqueId());
        this.questPoints = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.QUEST_POINTS, player.getUniqueId()); //TODO: Přepnout ve finále
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
        this.craftCoins = coins;
        this.craftTokens = tokens;
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
    @Deprecated(since = "2.0.0")
    public long getCraftCoins() {
        return craftCoins;
    }

    @Deprecated(since = "2.0.0")
    public void setCraftCoins(long craftCoins) {
        this.craftCoins = craftCoins;
    }

    @Deprecated(since = "2.0.0")
    public long getCraftTokens() {
        return craftTokens;
    }

    @Deprecated(since = "2.0.0")
    public void setCraftTokens(long craftTokens) {
        this.craftTokens = craftTokens;
    }

    @Deprecated(since = "2.0.0")
    public long getVoteTokens() {
        return voteTokens;
    }

    @Deprecated(since = "2.0.0")
    public void setVoteTokens(long voteTokens) {
        this.voteTokens = voteTokens;
    }

    @Deprecated(since = "2.0.0")
    public void setKarmaPoints(long karmaPoints) {
        this.karmaPoints = karmaPoints;
    }

    @Deprecated(since = "2.0.0")
    public long getKarmaPoints() {
        return karmaPoints;
    }

    public HashSet<Multiplier> getMultipliers() {
        return multipliers;
    }

    public void setMultipliers(HashSet<Multiplier> multipliers) {
        this.multipliers = multipliers;
    }

    @Deprecated(since = "2.0.0")
    public long getQuestPoints() {
        return questPoints;
    }

    @Deprecated(since = "2.0.0")
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

    @Deprecated(since = "2.0.0")
    public long getEventPoints() {
        return eventPoints;
    }

    @Deprecated(since = "2.0.0")
    public void setEventPoints(long eventPoints) {
        this.eventPoints = eventPoints;
    }

    @Deprecated(since = "2.0.0")
    public long getSeasonPoints() {
        return seasonPoints;
    }

    @Deprecated(since = "2.0.0")
    public void setSeasonPoints(long seasonPoints) {
        this.seasonPoints = seasonPoints;
    }

    /** Vrací hodnoty z cache určené ekonomiky
     *
     * @param type {@link EconomyType}
     * @return {@link Long} hodnotu ekonomiky
     */
    public long getEconomyByType(final EconomyType type) {
        switch (type) {
            case CRAFT_COINS -> {
                return this.craftCoins;
            }
            case CRAFT_TOKENS -> {
                return this.craftTokens;
            }
            case VOTE_TOKENS, VOTE_TOKENS_2, VOTE_TOKENS_3 -> {
                return this.voteTokens;
            }
            case KARMA_POINTS -> {
                return this.karmaPoints;
            }
            case QUEST_POINTS -> {
                return this.questPoints;
            }
            case SEASON_POINTS -> {
                return this.seasonPoints;
            }
            case EVENT_POINTS -> {
                return this.eventPoints;
            }
            case PARKOUR_POINTS -> {
                return this.parkourPoints;
            }
            default -> throw new Error("Unsupported get type economy!");
        }
    }

    public void setEconomyByType(final EconomyType type, final long amount) {
        switch (type) {
            case CRAFT_COINS -> {
                this.craftCoins = amount;
            }
            case CRAFT_TOKENS -> {
                this.craftTokens = amount;
            }
            case VOTE_TOKENS, VOTE_TOKENS_2, VOTE_TOKENS_3 -> {
                this.voteTokens = amount;
            }
            case KARMA_POINTS -> {
                this.karmaPoints = amount;
            }
            case QUEST_POINTS -> {
                this.questPoints = amount;
            }
            case SEASON_POINTS -> {
                this.seasonPoints = amount;
            }
            case EVENT_POINTS -> {
                this.eventPoints = amount;
            }
            case PARKOUR_POINTS -> {
                this.parkourPoints = amount;
            }
            default -> throw new Error("Unsupported get type economy!");
        }
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
    public double getMoney() {
        return serverMoney;
    }

    /**
     * Nastavuje novou hodnotu peněz v cache. (Vault)
     *
     * @param serverMoney Počet peněz - musí být finální počet
     */
    public void setMoney(double serverMoney) {
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
     * Neodpovídá počtu hlasů za měsíc, jelikož VotePass trvá
     * déle jak měsíc.
     *
     * @return {@link Long}
     */
    public long getVotePassVotes() {
        return votePass;
    }

    /**
     * Nastavuje počet hlasů pro aktuální VotePass
     *
     * @param votePass {@link Long}
     */
    public void setVotePassVotes(long votePass) {
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
            case SURVIVAL_117_LEVEL:
                return this.survivalLevel;
            case SKYBLOCK_117_LEVEL:
                return this.skyblockLevel;
            case SURVIVAL_118_LEVEL:
                return this.survival118Level;
            case SKYBLOCK_118_LEVEL:
                return this.skyblock118Level;
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
            case SURVIVAL_117_LEVEL:
                this.survivalLevel = level;
                break;
            case SKYBLOCK_117_LEVEL:
                this.skyblockLevel = level;
                break;
            case SURVIVAL_118_LEVEL:
                this.survival118Level = level;
                break;
            case SKYBLOCK_118_LEVEL:
                this.skyblock118Level = level;
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
            case SURVIVAL_117_EXPERIENCE:
                return this.survivalExperience;
            case SKYBLOCK_117_EXPERIENCE:
                return this.skyblockExperience;
            case SURVIVAL_118_EXPERIENCE:
                return this.survival118Experience;
            case SKYBLOCK_118_EXPERIENCE:
                return this.skyblock118Experience;
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
            case SURVIVAL_117_EXPERIENCE:
                this.survivalExperience = experience;
                break;
            case SKYBLOCK_117_EXPERIENCE:
                this.skyblockExperience = experience;
                break;
            case SURVIVAL_118_EXPERIENCE:
                this.survival118Experience = experience;
                break;
            case SKYBLOCK_118_EXPERIENCE:
                this.skyblock118Experience = experience;
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
        finalValue += canBeAdded(this.survival118Level);
        finalValue += canBeAdded(this.skyblock118Level);
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
