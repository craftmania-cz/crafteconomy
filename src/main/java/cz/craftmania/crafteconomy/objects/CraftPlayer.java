package cz.craftmania.crafteconomy.objects;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.entity.Player;

import java.util.HashSet;

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

    // Others
    private long karma = 0;
    private long achievementPoints = 0;
    private HashSet<Multiplier> multipliers;

    public CraftPlayer() {
    }

    public CraftPlayer(final Player player) {
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
        recalculateGlobalLevel();
    }

    public CraftPlayer(final Player player, final long coins, final long tokens, final long voteTokens) {
        this.player = player;
        this.coins = coins;
        this.tokens = tokens;
        this.voteTokens = voteTokens;
        this.multipliers = new HashSet<>();
        recalculateGlobalLevel();
    }

    public Player getPlayer() {
        return player;
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

    public long getLevelByType(final LevelType type) {
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

    public void setLevelByType(final LevelType type, final long level) {
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

    public long getExperienceByType(final LevelType type) {
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

    public void setExperienceByType(final LevelType type, final long experience) {
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
     * @return
     */
    private long canBeAdded(long amount) {
        if (amount > 1) {
            return amount;
        }
        return 0;
    }
}
