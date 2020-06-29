package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * API for changing player's Karma
 */
public class KarmaAPI {

    private static final BasicManager manager = new BasicManager();

    /**
     * Returns amount karma that player owns
     *
     * @param player Selected player
     * @return amount of karma
     */
    public static long getKarma(@NonNull final Player player) {
        return manager.getCraftPlayer(player).getKarma();
    }

    /**
     * Returns amount karma by player nick name
     *
     * @param player Selected player
     * @return amount of karma, returns 0 if nick does not exists
     */
    public static long getKarma(@NonNull final String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getKarma();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.KARMA, player);
    }

    /**
     * Sets for requested player karma + send message about receiving.
     *
     * @param player      Player
     * @param karmaToAdd Value to give
     */
    public static void giveKarma(@NonNull final Player player, final long karmaToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveKarma zastaven!");
                return;
            }
            long actualKarma = manager.getCraftPlayer(player).getKarma();
            long finalKarma = actualKarma + karmaToAdd;
            manager.getCraftPlayer(player).setKarma(finalKarma);
            Main.getInstance().getMySQL().setEconomy(EconomyType.KARMA, player, finalKarma);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti přidáno §7" + karmaToAdd + " Karmy.");
            }
        });
    }

    /**
     * Sets for requested player karma
     *
     * @param player      Player name
     * @param karmaToAdd Value to give
     */
    public static void giveOfflineKarma(@NonNull final String player, final long karmaToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.KARMA, player, karmaToAdd);
        });
    }

    /**
     * Rake selected amount of karma from player + send message about taking.
     *
     * @param player         Player
     * @param karmaToRemove  Value to remove
     */
    public static void takeKarma(@NonNull final Player player, final long karmaToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualKarma = manager.getCraftPlayer(player).getKarma();
            long finalKarma = actualKarma - karmaToRemove;
            if (finalKarma < 0) {
                return;
            }
            manager.getCraftPlayer(player).setKarma(finalKarma);
            Main.getInstance().getMySQL().setEconomy(EconomyType.KARMA, player, finalKarma);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebráno §7" + karmaToRemove + " Karmy.");
            }
        });
    }

    /**
     * Rake selected amount of karma from player
     *
     * @param player         Player name
     * @param karmaToRemove Value to remove
     */
    public static void takeOfflineKarma(@NonNull final String player, final long karmaToRemove) {
        Main.getAsync().runAsync(() -> {
            long actualKarma = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.KARMA, player);
            long finalKarma = actualKarma - karmaToRemove;
            if (finalKarma < 0) {
                return;
            }
            Main.getInstance().getMySQL().takeEconomy(EconomyType.KARMA, player, karmaToRemove);
        });
    }
}
