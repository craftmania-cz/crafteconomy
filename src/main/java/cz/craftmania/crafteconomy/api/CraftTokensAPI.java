package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.entity.Player;

public class CraftTokensAPI {

    private static final Main plugin = Main.getInstance();
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
            Main.getInstance().getMySQL().setEconomy("crafttokens", player, finalTokens);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + tokensToAdd + " CraftTokens.");
            }
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
            Main.getInstance().getMySQL().setEconomy("crafttokens", player, finalTokens);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebrano §7" + tokensToRemove + " CraftTokens.");
            }
        });
    }
}
