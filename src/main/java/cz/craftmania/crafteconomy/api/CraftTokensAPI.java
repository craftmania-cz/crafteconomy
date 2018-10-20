package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.entity.Player;

public class CraftTokensAPI {

    private static final Main plugin = Main.getInstance();
    private static final BasicManager manager = new BasicManager();

    public static long getTokens(final Player player) {
        return manager.getCraftPlayer(player).getTokens();
    }

    public static void giveTokens(final Player player, final long tokensToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                BasicManager.getOrRegisterPlayer(player);
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

    public static void takeTokens(final Player player, final long tokensToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualTokens = manager.getCraftPlayer(player).getTokens();
            long finalTokens = actualTokens - tokensToRemove;
            manager.getCraftPlayer(player).setTokens(finalTokens);
            Main.getInstance().getMySQL().setEconomy("crafttokens", player, finalTokens);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebrano §7" + tokensToRemove + " CraftTokens.");
            }
        });
    }
}
