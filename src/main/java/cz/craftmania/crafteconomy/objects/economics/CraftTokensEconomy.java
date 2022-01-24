package cz.craftmania.crafteconomy.objects.economics;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.api.IEconomy;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CraftTokensEconomy implements IEconomy<EconomyAPI> {

    private static final BasicManager manager = new BasicManager();

    @Override
    public @Nullable String name() {
        return "CraftTokens";
    }

    @Override
    public @Nullable String databaseColumn() {
        return "crafttokens";
    }

    @Override
    public long get(@NotNull String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getTokens();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTTOKENS, player);
    }

    @Override
    public long get(@NotNull Player player) {
        return manager.getCraftPlayer(player).getTokens();
    }

    @Override
    public void give(@NotNull String player, long amountToAdd) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void give(@NotNull Player player, long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveTokens zastaven!");
                return;
            }
            long actualTokens = manager.getCraftPlayer(player).getTokens();
            long finalTokens = actualTokens + amountToAdd;
            manager.getCraftPlayer(player).setTokens(finalTokens);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTTOKENS, player, finalTokens);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + amountToAdd + " CraftTokens.");
            }
        });
    }

    @Override
    public void giveOffline(@NotNull String player, long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.CRAFTTOKENS, player, amountToAdd);
        });
    }

    @Override
    public void take(@NotNull String player, long amountToTake) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void take(@NotNull Player player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualTokens = manager.getCraftPlayer(player).getTokens();
            long finalTokens = actualTokens - amountToTake;
            if (finalTokens < 0) {
                return;
            }
            manager.getCraftPlayer(player).setTokens(finalTokens);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTTOKENS, player, finalTokens);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebrano §7" + amountToTake + " CraftTokens.");
            }
        });
    }

    @Override
    public void takeOffline(@NotNull String player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualTokens = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTTOKENS, player);
            long finalTokens = actualTokens - amountToTake;
            if (finalTokens < 0) {
                return; //TODO: Fail zpráva pro sendera?
            }
            Main.getInstance().getMySQL().takeEconomy(EconomyType.CRAFTTOKENS, player, amountToTake);
        });
    }

    @Override
    public void payBetween(@NotNull Player sender, @NotNull Player target, long amountToPay) {
        throw new Error("Tato funkce není implementována.");
    }
}
