package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.entity.Player;

public class VoteTokensAPI {

    private static final BasicManager manager = new BasicManager();

    /**
     * Returns amount tokens that player owns
     *
     * @param player Selected player
     * @return amount of crafttokens
     */
    public static long getVoteTokens(final Player player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getPlayer().equals(player)) {
                return manager.getCraftPlayer(player1).getVoteTokens();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.VOTETOKENS_2, player.getUniqueId());
    }

    /**
     * Returns amount tokens by player nick name
     *
     * @param player Selected player
     * @return amount of votetokens, returns 0 if nick does not exists
     */
    public static long getVoteTokens(final String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getVoteTokens();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.VOTETOKENS_2, player);
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
                Logger.danger("Hrac " + player.getName() + " neni v cache giveVoteTokens zastaven!");
                return;
            }
            long actualVoteTokens = manager.getCraftPlayer(player).getVoteTokens();
            long finalVoteTokens = actualVoteTokens + voteTokensToAdd;
            manager.getCraftPlayer(player).setVoteTokens(finalVoteTokens);
            Main.getInstance().getMySQL().setEconomy(EconomyType.VOTETOKENS_2, player, finalVoteTokens);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti pridano §7" + voteTokensToAdd + " VoteTokens.");
            }
        });
    }

    /**
     * Sets for requested player votetokens
     *
     * @param player          Player name
     * @param voteTokensToAdd Value to give
     */
    public static void giveOfflineVoteTokens(final String player, final long voteTokensToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.VOTETOKENS_2, player, voteTokensToAdd);
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
            Main.getInstance().getMySQL().setEconomy(EconomyType.VOTETOKENS_2, player, finalVoteTokens);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti odebrano §7" + voteTokensToRemove + " VoteTokens.");
            }
        });
    }

    /**
     * Rake selected amount of tokens from player
     *
     * @param player             Player name
     * @param voteTokensToRemove Value to remove
     */
    public static void takeOfflineVoteTOkens(final String player, final long voteTokensToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualVoteTokens = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.VOTETOKENS_2, player);
            long finalVoteTokens = actualVoteTokens - voteTokensToRemove;
            if (finalVoteTokens < 0) {
                return;
            }
            Main.getInstance().getMySQL().takeEconomy(EconomyType.VOTETOKENS_2, player, voteTokensToRemove);
        });
    }
}
