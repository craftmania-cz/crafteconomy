package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.entity.Player;

public class EventPointsAPI {

    private static final BasicManager manager = new BasicManager();

    /**
     * Vrací hodnotu počtu Event points hráče
     *
     * @param player Vybraný hráč
     * @return počet event points
     */
    public static long getEventPoints(final Player player) {
        return manager.getCraftPlayer(player).getEventPoints();
    }

    /**
     * Vrací hodnotu počtu Event points hráče podle stringu
     *
     * @param player Vybraný hráč
     * @return počet event points
     */
    public static long getEventPoints(final String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getEventPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.EVENT_POINTS, player);
    }

    /**
     * Přidá požadovanému hráči Event Points podle zadaného počtu
     *
     * @param player Zvolený hráč
     * @param pointsToAdd Počet k přidání
     */
    public static void giveEventPoints(final Player player, final long pointsToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveEventPoints zastaven!");
                return;
            }
            long actualPoints = manager.getCraftPlayer(player).getEventPoints();
            long finalpoints = actualPoints + pointsToAdd;
            manager.getCraftPlayer(player).setEventPoints(finalpoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.EVENT_POINTS, player, finalpoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + finalpoints + " EventPoints.");
            }
        });
    }

    /**
     * Odebere požadovanému hráči Event Points podle požadovaného počtu
     *
     * @param player Zvolený hráč
     * @param pointsToTake Počet k odebrání
     */
    public static void takeEventPoints(final Player player, final long pointsToTake) {
        Main.getAsync().runAsync(() -> {
            long actualPoints = manager.getCraftPlayer(player).getEventPoints();
            long finalPoints = actualPoints - pointsToTake;
            if (finalPoints < 0) {
                return;
            }
            manager.getCraftPlayer(player).setEventPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.EVENT_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebrano §7" + pointsToTake + " EventPoints.");
            }
        });
    }


}
