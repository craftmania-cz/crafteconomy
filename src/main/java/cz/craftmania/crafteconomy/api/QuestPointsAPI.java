package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * API pro správu Quest Points
 */
public class QuestPointsAPI {

    private static final BasicManager manager = new BasicManager();

    /**
     * Vrací počet quest points podle objektu {@link Player}
     *
     * @param player Vybraný hráč
     * @return Počet quest points, vrací 0 pokud neexistuje
     */
    public static long getQuestPoints(@NonNull final Player player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getPlayer().equals(player)) {
                return manager.getCraftPlayer(player1).getQuestPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.ACHIEVEMENT_POINTS, player.getUniqueId());
    }

    /**
     * Vrací počet quest points podle zadaného nicku
     *
     * @param player Vybraný hráč
     * @return Počet quest points, vrací 0 pokud neexistuje
     */
    public static long getQuestPoints(@NonNull final String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getQuestPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.ACHIEVEMENT_POINTS, player);
    }

    /**
     * Přidá počet quest points zadanému hráči + pošle zprávu
     *
     * @param player Hráč
     * @param pointsToAdd Hodnota > 0
     */
    public static void giveQuestPoints(@NonNull final Player player, final long pointsToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveQuestPoints zastaven!");
                return;
            }
            long actualPoints = manager.getCraftPlayer(player).getQuestPoints();
            long finalPoints = actualPoints + pointsToAdd;
            manager.getCraftPlayer(player).setQuestPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.ACHIEVEMENT_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + pointsToAdd + " QuestPoints.");
            }
        });
    }

    /**
     * Nastaví počet quest points offline hráči
     *
     * @param player Hráč
     * @param pointsToAdd Hodnota > 0
     */
    public static void giveOfflineQuestPoints(@NonNull final String player, final long pointsToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.ACHIEVEMENT_POINTS, player, pointsToAdd);
        });
    }

    /**
     * Vezme hráči požadovaný počet quest points
     *
     * @param player Hráč
     * @param pointsToRemove Hodnota k odebrání > 0
     */
    public static void takeQuestPoints(@NonNull final Player player, final long pointsToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualPoints = manager.getCraftPlayer(player).getQuestPoints();
            long finalPoints = actualPoints - pointsToRemove;
            if (finalPoints < 0) {
                return;
            }
            manager.getCraftPlayer(player).setQuestPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.ACHIEVEMENT_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti odebráno §7" + pointsToRemove + " QuestPoints.");
            }
        });
    }

    /**
     * Vezme hráči požadovaný počet quest points
     *
     * @param player Hráč
     * @param pointsToRemove Hodnota k odebrání > 0
     */
    public static void takeOfflineQuestPoints(@NonNull final String player, final long pointsToRemove) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().takeEconomy(EconomyType.ACHIEVEMENT_POINTS, player, pointsToRemove);
        });
    }

    public static void resetQuestPoints(@NonNull final Player player) {
        //TODO: Reset to zero
    }

}
