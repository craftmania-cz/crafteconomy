package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.entity.Player;

public class CraftCoinsAPI {

    private static final Main plugin = Main.getInstance();
    private static final BasicManager manager = new BasicManager();

    /**
     * Returns amount coins that player owns
     *
     * @param player Selected player
     * @return amount of craftcoins
     */
    public static long getCoins(final Player player) { //TODO: Offline
        return manager.getCraftPlayer(player).getCoins();
    }

    /**
     * Sets for requested player craftcoins + send message about receiving.
     *
     * @param player     Player
     * @param coinsToAdd Value to give
     */
    public static void giveCoins(final Player player, final long coinsToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                BasicManager.getOrRegisterPlayer(player);
            }
            long actualCoins = manager.getCraftPlayer(player).getCoins();
            long finalCoins = actualCoins + coinsToAdd;
            manager.getCraftPlayer(player).setCoins(finalCoins);
            Main.getInstance().getMySQL().setEconomy("craftcoins", player, finalCoins);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + coinsToAdd + " CraftCoins.");
            }
        });
    }

    /**
     * Rake selected amount of coins from player + send message about taking.
     *
     * @param player        Player
     * @param coinsToRemove Value to remove
     */
    public static void takeCoins(final Player player, final long coinsToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualCoins = manager.getCraftPlayer(player).getCoins();
            long finalCoins = actualCoins - coinsToRemove;
            manager.getCraftPlayer(player).setCoins(finalCoins);
            Main.getInstance().getMySQL().setEconomy("craftcoins", player, finalCoins);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti odebrano §7" + coinsToRemove + " CraftCoins.");
            }
        });
    }


}
