package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.entity.Player;

public class VoteTokensAPI {

    private static final Main plugin = Main.getInstance();
    private static final BasicManager manager = new BasicManager();

    public static long getVoteTokens(final Player player) { //TODO: Offline
        return manager.getCraftPlayer(player).getVoteTokens();
    }

    public static void giveVoteTokens(final Player player, final long voteTokensToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                BasicManager.getOrRegisterPlayer(player);
            }
            long actualVoteTokens = manager.getCraftPlayer(player).getVoteTokens();
            long finalVoteTokens = actualVoteTokens + voteTokensToAdd;
            manager.getCraftPlayer(player).setVoteTokens(finalVoteTokens);
            Main.getInstance().getMySQL().setEconomy("votetokens", player, finalVoteTokens);
            if (player.isOnline()) {
                player.sendMessage("§bBylo ti pridano " + voteTokensToAdd + " VoteTokens.");
            }
        });
    }

    public static void takeVoteTokens(final Player player, final long voteTokensToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualVoteTokens = manager.getCraftPlayer(player).getVoteTokens();
            long finalVoteTokens = actualVoteTokens - voteTokensToRemove;
            manager.getCraftPlayer(player).setVoteTokens(finalVoteTokens);
            Main.getInstance().getMySQL().setEconomy("votetokens", player, finalVoteTokens);
            if (player.isOnline()) {
                player.sendMessage("§bBylo ti odebrano " + voteTokensToRemove + " VoteTokens.");
            }
        });
    }
}
