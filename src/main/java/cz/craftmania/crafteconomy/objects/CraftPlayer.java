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
    private long skycloudLevel = 1;
    private long skycloudExperience = 0;

    // Votes
    private long week_votes = 0;
    private long month_votes = 0;
    private long total_votes = 0;
    private long last_vote = 0;

    // Vault economy
    private long serverMoney = -1;

    // Others
    private long karma = 0;
    private long achievementPoints = 0;
    private long eventPoints = 0;
    private HashSet<Multiplier> multipliers;
    private boolean isAfk = false;

    // Player settings
    private boolean payToggle = false;

    public CraftPlayer() {
    }

    public CraftPlayer(@NonNull final Player player) {
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
        this.achievementPoints = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.ACHIEVEMENT_POINTS, player.getUniqueId());
        this.total_votes = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.TOTAL_VOTES, player.getUniqueId());
        this.month_votes = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.MONTH_VOTES, player.getUniqueId());
        this.week_votes = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.WEEK_VOTES, player.getUniqueId());
        this.eventPoints = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.EVENT_POINTS, player.getUniqueId());

        this.payToggle = (Main.getInstance().getMySQL().getSettings(player, "paytoggle") != 0);
        recalculateGlobalLevel();
    }

    public CraftPlayer(@NonNull final Player player, final long coins, final long tokens, final long voteTokens) {
        this.player = player;
        this.coins = coins;
        this.tokens = tokens;
        this.voteTokens = voteTokens;
        this.multipliers = new HashSet<>();
        recalculateGlobalLevel();
    }

    public Player getPlayer() {
        return this.player;
    }

    public UUID getUUID() {
        return this.player.getUniqueId();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

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

    public long getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(long achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public long getTotalVotes() {
        return total_votes;
    }

    public long getMonthVotes() {
        return month_votes;
    }

    public long getWeekVotes() {
        return week_votes;
    }

    public long getLastVoteTime() {
        return last_vote;
    }

    public long getEventPoints() {
        return eventPoints;
    }

    public void setEventPoints(long eventPoints) {
        this.eventPoints = eventPoints;
    }

    public void addWeekVote() {
        this.week_votes++;
    }

    public void addMonthVote() {
        this.month_votes++;
    }

    public void addTotalVote() {
        this.total_votes++;
    }

    public void setLastVote(long last_vote) {
        this.last_vote = last_vote;
    }

    public long getMoney() {
        return serverMoney;
    }

    public void setMoney(long serverMoney) {
        this.serverMoney = serverMoney;
    }

    public boolean isAfk() {
        return isAfk;
    }

    public void setAfk(boolean afk) {
        isAfk = afk;
    }

    public void setPayToggle(boolean payToggle) {
        this.payToggle = payToggle;
    }

    public boolean getPayToggle() {
        return payToggle;
    }

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
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    /**
     * Prepocitava globalni level v souctu (kde hrac musi mit min 2 level abys se hodnota změnila).
     */
    private void recalculateGlobalLevel() {
        long finalValue = 1;
        finalValue += canBeAdded(this.survivalLevel);
        finalValue += canBeAdded(this.skyblockLevel);
        finalValue += canBeAdded(this.creativeLevel);
        finalValue += canBeAdded(this.vanillaLevel);
        finalValue += canBeAdded(this.skycloudLevel);
        finalValue += canBeAdded(this.prisonLevel);
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
