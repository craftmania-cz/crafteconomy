package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * API pro správu Season Points
 */
public class SeasonPointsAPI {

    private static final BasicManager manager = new BasicManager();

    /**
     * Vrací počet season points podle objektu {@link Player}
     *
     * @param player Vybraný hráč
     * @return Počet season points, vrací 0 pokud neexistuje
     */
    public static long getSeasonPoints(@NonNull final Player player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getPlayer().equals(player)) {
                return manager.getCraftPlayer(player1).getSeasonPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.SEASON_POINTS, player.getUniqueId());
    }

    /**
     * Vrací počet season points podle zadaného nicku
     *
     * @param player Vybraný hráč
     * @return Počet season points, vrací 0 pokud neexistuje
     */
    public static long getSeasonPoints(@NonNull final String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getSeasonPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.SEASON_POINTS, player);
    }

    /**
     * Přidá počet season points zadanému hráči + pošle zprávu
     *
     * @param player Hráč
     * @param pointsToAdd Hodnota > 0
     */
    public static void giveSeasonPoints(@NonNull final Player player, final long pointsToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveSeasonPoints zastaven!");
                return;
            }
            long actualPoints = manager.getCraftPlayer(player).getSeasonPoints();
            long finalPoints = actualPoints + pointsToAdd;
            manager.getCraftPlayer(player).setSeasonPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.SEASON_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + pointsToAdd + " SeasonPoints.");
            }
        });
    }

    /**
     * Nastaví počet season points offline hráči
     *
     * @param player Hráč
     * @param pointsToAdd Hodnota > 0
     */
    public static void giveOfflineSeasonPoints(@NonNull final String player, final long pointsToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.SEASON_POINTS, player, pointsToAdd);
        });
    }

    /**
     * Vezme hráči požadovaný počet season points
     *
     * @param player Hráč
     * @param pointsToRemove Hodnota k odebrání > 0
     */
    public static void takeSeasonPoints(@NonNull final Player player, final long pointsToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualPoints = manager.getCraftPlayer(player).getSeasonPoints();
            long finalPoints = actualPoints - pointsToRemove;
            if (finalPoints < 0) {
                return;
            }
            manager.getCraftPlayer(player).setSeasonPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.SEASON_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebráno §7" + finalPoints + " SeasonPoints.");
            }
        });
    }

    /**
     * Vezme hráči požadovaný počet season points
     *
     * @param player Hráč
     * @param pointsToRemove Hodnota k odebrání > 0
     */
    public static void takeOfflineSeasonPoints(@NonNull final String player, final long pointsToRemove) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().takeEconomy(EconomyType.SEASON_POINTS, player, pointsToRemove);
        });
    }

    public static void resetSeasonPoints(@NonNull final Player player) {
        //TODO: Reset to zero
    }

}
