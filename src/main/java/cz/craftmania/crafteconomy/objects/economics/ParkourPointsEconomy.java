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

public class ParkourPointsEconomy implements IEconomy<EconomyAPI> {

    private static final BasicManager manager = new BasicManager();

    @Override
    public @Nullable String name() {
        return "ParkourPoints";
    }

    @Override
    public @Nullable String databaseColumn() {
        return "parkour_points";
    }

    @Override
    public long get(@NotNull String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getEconomyByType(EconomyType.PARKOUR_POINTS);
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.PARKOUR_POINTS, player);
    }

    @Override
    public long get(@NotNull Player player) {
        return manager.getCraftPlayer(player).getEconomyByType(EconomyType.PARKOUR_POINTS);
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
            long actualPoints = manager.getCraftPlayer(player).getEconomyByType(EconomyType.PARKOUR_POINTS);
            long finalpoints = actualPoints + amountToAdd;
            manager.getCraftPlayer(player).setEconomyByType(EconomyType.PARKOUR_POINTS, finalpoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.PARKOUR_POINTS, player, finalpoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + amountToAdd + " ParkourPoints.");
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
            long actualPoints = manager.getCraftPlayer(player).getEconomyByType(EconomyType.PARKOUR_POINTS);
            long finalPoints = actualPoints - amountToTake;
            if (finalPoints < 0) {
                return;
            }
            manager.getCraftPlayer(player).setEconomyByType(EconomyType.PARKOUR_POINTS, finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.PARKOUR_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti odebrano §7" + amountToTake + " ParkourPoints.");
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
