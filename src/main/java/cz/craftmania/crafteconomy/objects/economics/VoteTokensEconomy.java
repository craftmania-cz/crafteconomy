package cz.craftmania.crafteconomy.objects.economics;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.api.IEconomy;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VoteTokensEconomy implements IEconomy<EconomyAPI> {

    private static final BasicManager manager = new BasicManager();

    @Override
    public @Nullable String name() {
        return "VoteTokens";
    }

    @Override
    public @Nullable String databaseColumn() {
        return "votetokens";
    }

    @Override
    public long get(@NotNull String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getEconomyByType(Main.getInstance().getVoteTokensVersion());
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(Main.getInstance().getVoteTokensVersion(), player);
    }

    @Override
    public long get(@NotNull Player player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getPlayer().equals(player)) {
                return manager.getCraftPlayer(player1).getEconomyByType(Main.getInstance().getVoteTokensVersion());
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(Main.getInstance().getVoteTokensVersion(), player.getUniqueId());
    }

    @Override
    public void give(@NotNull String player, long amountToAdd) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void give(@NotNull Player player, long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveVoteTokens zastaven!");
                return;
            }
            long actualVoteTokens = manager.getCraftPlayer(player).getEconomyByType(Main.getInstance().getVoteTokensVersion());
            long finalVoteTokens = actualVoteTokens + amountToAdd;
            manager.getCraftPlayer(player).setEconomyByType(Main.getInstance().getVoteTokensVersion(), finalVoteTokens);
            Main.getInstance().getMySQL().setEconomy(Main.getInstance().getVoteTokensVersion(), player, finalVoteTokens);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + amountToAdd + " VoteTokens.");
            }
        });
    }

    @Override
    public void giveOffline(@NotNull String player, long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(Main.getInstance().getVoteTokensVersion(), player, amountToAdd);
        });
    }

    @Override
    public void take(@NotNull String player, long amountToTake) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void take(@NotNull Player player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualVoteTokens = manager.getCraftPlayer(player).getEconomyByType(Main.getInstance().getVoteTokensVersion());
            long finalVoteTokens = actualVoteTokens - amountToTake;
            if (finalVoteTokens < 0) {
                return;
            }
            manager.getCraftPlayer(player).setEconomyByType(Main.getInstance().getVoteTokensVersion(), finalVoteTokens);
            Main.getInstance().getMySQL().setEconomy(Main.getInstance().getVoteTokensVersion(), player, finalVoteTokens);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti odebrano §7" + amountToTake + " VoteTokens.");
            }
        });
    }

    @Override
    public void takeOffline(@NotNull String player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualVoteTokens = Main.getInstance().getMySQL().getPlayerEconomy(Main.getInstance().getVoteTokensVersion(), player);
            long finalVoteTokens = actualVoteTokens - amountToTake;
            if (finalVoteTokens < 0) {
                return;
            }
            Main.getInstance().getMySQL().takeEconomy(Main.getInstance().getVoteTokensVersion(), player, amountToTake);
        });
    }

    @Override
    public void payBetween(@NotNull Player sender, @NotNull Player target, long amountToPay) {
        throw new Error("Tato funkce není implementována.");
    }
}
