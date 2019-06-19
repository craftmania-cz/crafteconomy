package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.entity.Player;

public class CraftTokensAPI {

    private static final BasicManager manager = new BasicManager();

    /**
     * Returns amount tokens that player owns
     *
     * @param player Selected player
     * @return amount of crafttokens
     */
    public static long getTokens(final Player player) {
        return manager.getCraftPlayer(player).getTokens();
    }

    /**
     * Returns amount tokens by player nick name
     *
     * @param player Selected player
     * @return amount of crafttokens, returns 0 if nick does not exists
     */
    public static long getTokens(final String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getTokens();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTTOKENS, player);
    }

    /**
     * Sets for requested player crafttokens + send message about receiving.
     *
     * @param player      Player
     * @param tokensToAdd Value to give
     */
    public static void giveTokens(final Player player, final long tokensToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveTokens zastaven!");
                return;
            }
            long actualTokens = manager.getCraftPlayer(player).getTokens();
            long finalTokens = actualTokens + tokensToAdd;
            manager.getCraftPlayer(player).setTokens(finalTokens);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTTOKENS, player, finalTokens);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + tokensToAdd + " CraftTokens.");
            }
        });
    }

    /**
     * Sets for requested player crafttokens
     *
     * @param player      Player name
     * @param tokensToAdd Value to give
     */
    public static void giveOfflineTokens(final String player, final long tokensToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.CRAFTTOKENS, player, tokensToAdd);
        });
    }

    /**
     * Rake selected amount of tokens from player + send message about taking.
     *
     * @param player         Player
     * @param tokensToRemove Value to remove
     */
    public static void takeTokens(final Player player, final long tokensToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualTokens = manager.getCraftPlayer(player).getTokens();
            long finalTokens = actualTokens - tokensToRemove;
            if (finalTokens < 0) {
                return;
            }
            manager.getCraftPlayer(player).setTokens(finalTokens);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTTOKENS, player, finalTokens);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebrano §7" + tokensToRemove + " CraftTokens.");
            }
        });
    }

    /**
     * Rake selected amount of tokens from player
     *
     * @param player         Player name
     * @param tokensToRemove Value to remove
     */
    public static void takeOfflineTokens(final String player, final long tokensToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualTokens = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTTOKENS, player);
            long finalTokens = actualTokens - tokensToRemove;
            if (finalTokens < 0) {
                return;
            }
            Main.getInstance().getMySQL().takeEconomy(EconomyType.CRAFTTOKENS, player, tokensToRemove);
        });
    }
}
