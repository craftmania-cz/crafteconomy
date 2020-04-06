package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * API for changing player's CraftCoins
 */
public class CraftCoinsAPI {

    private static final BasicManager manager = new BasicManager();

    /**
     * Returns amount coins by player nick name
     *
     * @param player Selected player
     * @return amount of craftcoins, returns 0 if nick does not exists
     */
    public static long getCoins(@NonNull final String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getCoins();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTCOINS, player);
    }

    /**
     * Returns amount of coins by player uuid
     *
     * @param player Selected Player
     * @return amount of craftcoins, returns 0 if player does not exists
     */
    public static long getCoins(@NonNull final Player player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getPlayer().equals(player)) {
                return manager.getCraftPlayer(player).getCoins();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTCOINS, player.getUniqueId());
    }

    /**
     * Sets for requested player craftcoins + send message about receiving.
     *
     * @param player     Player
     * @param coinsToAdd Value to give
     */
    public static void giveCoins(@NonNull final Player player, final long coinsToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveCoins zastaven!");
                return;
            }
            long actualCoins = manager.getCraftPlayer(player).getCoins();
            long finalCoins = actualCoins + coinsToAdd;
            manager.getCraftPlayer(player).setCoins(finalCoins);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTCOINS, player, finalCoins);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + coinsToAdd + " CraftCoins.");
            }
        });
    }

    /**
     * Sets for requested player craftcoins
     *
     * @param player     Player name
     * @param coinsToAdd Value to give
     */
    public static void giveOfflineCoins(@NonNull final String player, final long coinsToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.CRAFTCOINS, player, coinsToAdd);
        });
    }


    /**
     * Take selected amount of coins from player + send message about taking.
     *
     * @param player        Player
     * @param coinsToRemove Value to remove
     */
    public static void takeCoins(@NonNull final Player player, final long coinsToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualCoins = manager.getCraftPlayer(player).getCoins();
            long finalCoins = actualCoins - coinsToRemove;
            if (finalCoins < 0) {
                return;
            }
            manager.getCraftPlayer(player).setCoins(finalCoins);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTCOINS, player, finalCoins);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti odebrano §7" + coinsToRemove + " CraftCoins.");
            }
        });
    }

    /**
     * Take selected amount of coins from player
     *
     * @param player        Player name
     * @param coinsToRemove Value to remove
     */
    public static void takeOfflineCoins(@NonNull final String player, final long coinsToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualCoins = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTCOINS, player);
            long finalCoins = actualCoins - coinsToRemove;
            if (finalCoins < 0) {
                return;
            }
            Main.getInstance().getMySQL().takeEconomy(EconomyType.CRAFTCOINS, player, coinsToRemove);
        });
    }

    /**
     * Transfer CC from sender to target
     *
     * @param sender     Player (Sender)
     * @param target     Player (Reciever - Target)
     * @param coinsToPay Value to transter
     */
    public static void payCoins(Player sender, Player target, long coinsToPay) {
        if(CraftCoinsAPI.getCoins(sender) < coinsToPay) {
            return;
        }
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(sender)) {
                Logger.danger("Hráč " + sender.getName() + " není v cache giveCoins zastaven!");
                return;
            }

            long currentCoinsSender = manager.getCraftPlayer(sender).getCoins();
            long newCoinsSender = currentCoinsSender - coinsToPay;

            long currentCoinsTarget = manager.getCraftPlayer(target).getCoins();
            long newCoinsTarget = currentCoinsTarget + coinsToPay;

            manager.getCraftPlayer(sender).setCoins(newCoinsSender);
            manager.getCraftPlayer(target).setCoins(newCoinsTarget);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTCOINS, sender, newCoinsSender);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTCOINS, target, newCoinsTarget);

            target.sendMessage("§aHráč " + sender.getName() + " ti poslal §7" + coinsToPay + " §aCC. Nyní máš §7" + newCoinsTarget + " §aCC.");
        });
    }
}
