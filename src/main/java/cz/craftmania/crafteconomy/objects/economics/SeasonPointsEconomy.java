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

public class SeasonPointsEconomy implements IEconomy<EconomyAPI> {

    private static final BasicManager manager = new BasicManager();

    @Override
    public @Nullable String name() {
        return "SeasonPoints";
    }

    @Override
    public @Nullable String databaseColumn() {
        return "seasonpoints";
    }

    @Override
    public long get(@NotNull String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getSeasonPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.SEASON_POINTS, player);
    }

    @Override
    public long get(@NotNull Player player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getPlayer().equals(player)) {
                return manager.getCraftPlayer(player1).getSeasonPoints();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.SEASON_POINTS, player.getUniqueId());
    }

    @Override
    public void give(@NotNull String player, long amountToAdd) {

    }

    @Override
    public void give(@NotNull Player player, long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveSeasonPoints zastaven!");
                return;
            }
            long actualPoints = manager.getCraftPlayer(player).getSeasonPoints();
            long finalPoints = actualPoints + amountToAdd;
            manager.getCraftPlayer(player).setSeasonPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.SEASON_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + amountToAdd + " SeasonPoints.");
            }
        });
    }

    @Override
    public void giveOffline(@NotNull String player, long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.SEASON_POINTS, player, amountToAdd);
        });
    }

    @Override
    public void take(@NotNull String player, long amountToTake) {

    }

    @Override
    public void take(@NotNull Player player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualPoints = manager.getCraftPlayer(player).getSeasonPoints();
            long finalPoints = actualPoints - amountToTake;
            if (finalPoints < 0) {
                return;
            }
            manager.getCraftPlayer(player).setSeasonPoints(finalPoints);
            Main.getInstance().getMySQL().setEconomy(EconomyType.SEASON_POINTS, player, finalPoints);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti odebráno §7" + amountToTake + " SeasonPoints.");
            }
        });
    }

    @Override
    public void takeOffline(@NotNull String player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().takeEconomy(EconomyType.SEASON_POINTS, player, amountToTake);
        });
    }

    @Override
    public void payBetween(@NotNull Player sender, @NotNull Player target, long amountToPay) {

    }
}
