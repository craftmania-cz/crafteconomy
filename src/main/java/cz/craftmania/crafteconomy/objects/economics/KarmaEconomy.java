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

public class KarmaEconomy implements IEconomy<EconomyAPI> {

    private static final BasicManager manager = new BasicManager();

    @Override
    public @Nullable String name() {
        return "Karma";
    }

    @Override
    public @Nullable String databaseColumn() {
        return "karma";
    }

    @Override
    public long get(@NotNull String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getEconomyByType(EconomyType.KARMA_POINTS);
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.KARMA_POINTS, player);
    }

    @Override
    public long get(@NotNull Player player) {
        return manager.getCraftPlayer(player).getEconomyByType(EconomyType.KARMA_POINTS);
    }

    @Override
    public void give(@NotNull String player, long amountToAdd) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void give(@NotNull Player player, long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache give zastaven!");
                return;
            }
            long actualKarmaPoints = manager.getCraftPlayer(player).getEconomyByType(EconomyType.KARMA_POINTS);
            long finalpoints = actualKarmaPoints + amountToAdd;
            manager.getCraftPlayer(player).setEconomyByType(EconomyType.KARMA_POINTS, finalpoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.KARMA_POINTS, player, finalpoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti přidáno §7" + amountToAdd + " Karmy.");
            }
        });
    }

    @Override
    public void giveOffline(@NotNull String player, long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.KARMA_POINTS, player, amountToAdd);
        });
    }

    @Override
    public void take(@NotNull String player, long amountToTake) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void take(@NotNull Player player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualKarma = manager.getCraftPlayer(player).getEconomyByType(EconomyType.KARMA_POINTS);
            long finalKarma = actualKarma - amountToTake;
            if (finalKarma < 0) {
                return;
            }
            manager.getCraftPlayer(player).setEconomyByType(EconomyType.KARMA_POINTS, finalKarma);
            Main.getInstance().getMySQL().setEconomy(EconomyType.KARMA_POINTS, player, finalKarma);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebráno §7" + amountToTake + " Karmy.");
            }
        });
    }

    @Override
    public void takeOffline(@NotNull String player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualKarma = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.KARMA_POINTS, player);
            long finalKarma = actualKarma - amountToTake;
            if (finalKarma < 0) {
                return;
            }
            Main.getInstance().getMySQL().takeEconomy(EconomyType.KARMA_POINTS, player, amountToTake);
        });
    }

    @Override
    public void payBetween(@NotNull Player sender, @NotNull Player target, long amountToPay) {
        throw new Error("Tato funkce není implementována.");
    }
}
