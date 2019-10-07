package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.PlayerExpGainEvent;
import cz.craftmania.crafteconomy.events.PlayerLevelUpEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * API for changing player's experience and levels
 */
public class LevelAPI {

    private static final Main plugin = Main.getInstance();
    private static final BasicManager manager = new BasicManager();

    /**
     * Returns player's level
     *
     * @param player selected player
     * @return player's level
     */
    public static long getLevel(final Player player, final LevelType type) {
        return manager.getCraftPlayer(player).getLevelByType(type);
    }

    public static Long getExp(final Player player, final LevelType type) {
        return manager.getCraftPlayer(player).getExperienceByType(type);
    }

    /**
     * Add selected amount of levels to player
     *
     * @param player player
     * @param levelsToAdd value to take
     */
    public static void addLevel(final Player player, final LevelType type, final int levelsToAdd) {
        if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
            Logger.danger("Hrac " + player.getName() + " neni v cache addLevel zastaven!");
            return;
        }
        long actualLevel = manager.getCraftPlayer(player).getLevelByType(type);
        long finalLevel = actualLevel + levelsToAdd; // final level
        manager.getCraftPlayer(player).setLevelByType(type, finalLevel);
        Main.getInstance().getMySQL().setEconomy(type, player, finalLevel);
        Main.getAsync().runSync(() -> Bukkit.getPluginManager().callEvent(new PlayerLevelUpEvent(manager.getCraftPlayer(player), levelsToAdd, finalLevel)));
    }

    /**
     * Add selected amount of levels to player
     *
     * @param player player
     * @param levelsToAdd value to take
     */
    public static void addOfflineLevel(final String player, final LevelType type, final int levelsToAdd) {
        Main.getInstance().getMySQL().addEconomy(type, player, levelsToAdd);
    }

    /**
     * Take selected amount of levels to player
     *
     * @param player player
     * @param levelsToTake value to add
     */
    public static void takeLevel(final Player player, final LevelType type, final int levelsToTake) {
        if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
            Logger.danger("Hrac " + player.getName() + " neni v cache takeLevel zastaven!");
            return;
        }
        long actualLevel = manager.getCraftPlayer(player).getLevelByType(type);
        long finalLevel = actualLevel - levelsToTake;
        if (finalLevel < 0 ) return;
        manager.getCraftPlayer(player).setLevelByType(type, finalLevel);
        Main.getInstance().getMySQL().setEconomy(type, player, finalLevel);
    }

    /**
     * Take selected amount of levels to player
     *
     * @param player player name
     * @param levelsToTake value to add
     */
    public static void takeOfflineLevel(final String player, final LevelType type, final int levelsToTake) {
        long actualLevel = Main.getInstance().getMySQL().getPlayerEconomy(type, player);
        long finalLevel = actualLevel - levelsToTake;
        if (finalLevel < 0 ) return;

        Main.getInstance().getMySQL().takeEconomy(type, player, levelsToTake);
    }

    /**
     * Add selected amount of exp to player
     *
     * @param player player
     * @param expToAdd value to add
     */
    public static void addExp(final Player player, final LevelType type, final int expToAdd) {
        if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
            Logger.danger("Hrac " + player.getName() + " neni v cache addExp zastaven!");
            return;
        }
        long actualExp = manager.getCraftPlayer(player).getExperienceByType(type);
        long finalExp = actualExp + expToAdd;
        manager.getCraftPlayer(player).setExperienceByType(type, finalExp);
        Main.getInstance().getMySQL().setEconomy(type, player, finalExp);
        Main.getAsync().runSync(() -> Bukkit.getPluginManager().callEvent(new PlayerExpGainEvent(manager.getCraftPlayer(player), expToAdd)));
    }

    /**
     * Add selected amount of exp to player
     *
     * @param player player name
     * @param expToAdd value to add
     */
    public static void addOfflineExp(final String player, final LevelType type, final int expToAdd) {
        Main.getInstance().getMySQL().addEconomy(type, player, expToAdd);
    }

    /**
     * Take selected amount of exp to player
     *
     * @param player player
     * @param expToTake value to take
     */
    public static void takeExp(final Player player, final LevelType type, final int expToTake) {
        if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
            Logger.danger("Hrac " + player.getName() + " neni v cache takeExp zastaven!");
            return;
        }
        long actualExp = manager.getCraftPlayer(player).getExperienceByType(type);
        long finalExp = actualExp - expToTake;
        if (finalExp < 0 ) return;
        manager.getCraftPlayer(player).setExperienceByType(type, finalExp);
        Main.getInstance().getMySQL().setEconomy(type, player, finalExp);
    }

    /**
     * Take selected amount of exp to player
     *
     * @param player player
     * @param expToTake value to take
     */
    public static void takeOfflineExp(final String player, final LevelType type, final int expToTake) {
        long actualExp = Main.getInstance().getMySQL().getPlayerEconomy(type, player);
        long finalExp = actualExp - expToTake;
        if (finalExp < 0 ) return;
        Main.getInstance().getMySQL().takeEconomy(type, player, expToTake);
    }
}
