package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.entity.Player;

public class VoteTokensAPI {

    private static final Main plugin = Main.getInstance();
    private static final BasicManager manager = new BasicManager();

    /**
     * Returns amount tokens that player owns
     *
     * @param player Selected player
     * @return amount of crafttokens
     */
    public static long getVoteTokens(final Player player) { //TODO: Offline
        return manager.getCraftPlayer(player).getVoteTokens();
    }

    /**
     * Sets for requested player votetokens + send message about receiving.
     *
     * @param player          Player
     * @param voteTokensToAdd Value to give
     */
    public static void giveVoteTokens(final Player player, final long voteTokensToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                return;
            }
            long actualVoteTokens = manager.getCraftPlayer(player).getVoteTokens();
            long finalVoteTokens = actualVoteTokens + voteTokensToAdd;
            manager.getCraftPlayer(player).setVoteTokens(finalVoteTokens);
            Main.getInstance().getMySQL().setEconomy("votetokens", player, finalVoteTokens);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti pridano §7" + voteTokensToAdd + " VoteTokens.");
            }
        });
    }

    /**
     * Rake selected amount of tokens from player + send message about taking.
     *
     * @param player             Player
     * @param voteTokensToRemove Value to remove
     */
    public static void takeVoteTokens(final Player player, final long voteTokensToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualVoteTokens = manager.getCraftPlayer(player).getVoteTokens();
            long finalVoteTokens = actualVoteTokens - voteTokensToRemove;
            if (finalVoteTokens < 0) {
                return;
            }
            manager.getCraftPlayer(player).setVoteTokens(finalVoteTokens);
            Main.getInstance().getMySQL().setEconomy("votetokens", player, finalVoteTokens);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti odebrano §7" + voteTokensToRemove + " VoteTokens.");
            }
        });
    }
}
