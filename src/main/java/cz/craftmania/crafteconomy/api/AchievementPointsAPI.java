package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.entity.Player;

public class AchievementPointsAPI {

    private static final BasicManager manager = new BasicManager();

    /**
     * Returns amount achievement points that player owns
     *
     * @param player Selected player
     * @return amount of achievement points
     */
    public static long getAchievementPoints(final Player player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getPlayer().equals(player)) {
                return manager.getCraftPlayer(player1).getAchievementPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.ACHIEVEMENT_POINTS, player.getUniqueId());
    }

    /**
     * Returns amount achievement points by player nick name
     *
     * @param player Selected player
     * @return amount of achievement points, returns 0 if player does not exists
     */
    public static long getAchievementPoints(final String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getAchievementPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.ACHIEVEMENT_POINTS, player);
    }

    /**
     * Sets achievement points for requested player + send message about receiving.
     *
     * @param player      Player
     * @param pointsToAdd Value to give
     */
    public static void giveAchievementPoints(final Player player, final long pointsToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveAchievementPoints zastaven!");
                return;
            }
            long actualPoints = manager.getCraftPlayer(player).getAchievementPoints();
            long finalPoints = actualPoints + pointsToAdd;
            manager.getCraftPlayer(player).setAchievementPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.ACHIEVEMENT_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + pointsToAdd + " AchievementPoints.");
            }
        });
    }

    /**
     * Sets achievement points for requested player
     *
     * @param player      Player name
     * @param pointsToAdd Value to give
     */
    public static void giveOfflineAchievementPoints(final String player, final long pointsToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.ACHIEVEMENT_POINTS, player, pointsToAdd);
        });
    }

    /**
     * Take selected amount of achievement points from player + send message about taking.
     *
     * @param player         Player
     * @param pointsToRemove Value to remove
     */
    public static void takeAchievementPoints(final Player player, final long pointsToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualPoints = manager.getCraftPlayer(player).getAchievementPoints();
            long finalPoints = actualPoints - pointsToRemove;
            if (finalPoints < 0) {
                return;
            }
            manager.getCraftPlayer(player).setAchievementPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.ACHIEVEMENT_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + finalPoints + " AchievementPoints.");
            }
        });
    }

    /**
     * Take selected amount of achievement points from player
     *
     * @param player        Player name
     * @param pointsToRemove Value to remove
     */
    public static void takeOfflineAchievementPoints(final String player, final long pointsToRemove) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().takeEconomy(EconomyType.ACHIEVEMENT_POINTS, player, pointsToRemove);
        });
    }

    public static void resetAchievementPoints(final Player player) {
        //TODO: Reset to zero
    }




}
