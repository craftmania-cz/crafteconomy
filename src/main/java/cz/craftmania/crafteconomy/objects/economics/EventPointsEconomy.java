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

public class EventPointsEconomy implements IEconomy<EconomyAPI> {

    private static final BasicManager manager = new BasicManager();

    @Override
    public @Nullable String name() {
        return "EventPoints";
    }

    @Override
    public @Nullable String databaseColumn() {
        return "eventpoints";
    }

    @Override
    public long get(@NotNull String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getEventPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.EVENT_POINTS, player);
    }

    @Override
    public long get(@NotNull Player player) {
        return manager.getCraftPlayer(player).getEventPoints();
    }

    @Override
    public void give(@NotNull String player, long amountToAdd) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void give(@NotNull Player player, long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveEventPoints zastaven!");
                return;
            }
            long actualPoints = manager.getCraftPlayer(player).getEventPoints();
            long finalpoints = actualPoints + amountToAdd;
            manager.getCraftPlayer(player).setEventPoints(finalpoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.EVENT_POINTS, player, finalpoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + amountToAdd + " EventPoints.");
            }
        });
    }

    @Override
    public void giveOffline(@NotNull String player, long amountToAdd) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void take(@NotNull String player, long amountToTake) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void take(@NotNull Player player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualPoints = manager.getCraftPlayer(player).getEventPoints();
            long finalPoints = actualPoints - amountToTake;
            if (finalPoints < 0) {
                return;
            }
            manager.getCraftPlayer(player).setEventPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.EVENT_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebrano §7" + amountToTake + " EventPoints.");
            }
        });
    }

    @Override
    public void takeOffline(@NotNull String player, long amountToTake) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void payBetween(@NotNull Player sender, @NotNull Player target, long amountToPay) {
        throw new Error("Tato funkce není implementována.");
    }
}
