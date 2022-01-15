package cz.craftmania.crafteconomy.api;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.EconomyType;
import cz.craftmania.crafteconomy.utils.Logger;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CraftCoinsEconomy implements IEconomy {

    private static final BasicManager manager = new BasicManager();

    @Override
    public String name(){
        return "CraftCoins";
    }

    @Override
    public String databaseColumn() {
        return "craftcoins";
    }

    @Override
    public long get(@NonNull String player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getName().equals(player)) {
                return manager.getCraftPlayer(player1).getCoins();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTCOINS, player);
    }

    @Override
    public long get(@NonNull Player player) {
        for (Player player1 : BasicManager.getCraftPlayersCache().keySet()) {
            if (player1.getPlayer().equals(player)) {
                return manager.getCraftPlayer(player).getCoins();
            }
        }
        return Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTCOINS, player.getUniqueId());
    }

    @Override
    public void give(@NonNull String player, final long amountToAdd) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void give(@NonNull Player player, final long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(player)) {
                Logger.danger("Hrac " + player.getName() + " neni v cache giveCoins zastaven!");
                return;
            }
            long actualCoins = manager.getCraftPlayer(player).getCoins();
            long finalCoins = actualCoins + amountToAdd;
            manager.getCraftPlayer(player).setCoins(finalCoins);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTCOINS, player, finalCoins);
            if (player.isOnline()) {
                player.sendMessage("§aBylo ti pridano §7" + amountToAdd + " CraftCoins.");
            }
        });
    }

    @Override
    public void giveOffline(@NonNull String player, final long amountToAdd) {
        Main.getAsync().runAsync(() -> {
            Main.getInstance().getMySQL().addEconomy(EconomyType.CRAFTCOINS, player, amountToAdd);
        });
    }

    @Override
    public void take(@NotNull String player, long amountToTake) {
        throw new Error("Tato funkce není implementována.");
    }

    @Override
    public void take(@NotNull Player player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualCoins = manager.getCraftPlayer(player).getCoins();
            long finalCoins = actualCoins - amountToTake;
            if (finalCoins < 0) {
                return;
            }
            manager.getCraftPlayer(player).setCoins(finalCoins);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTCOINS, player, finalCoins);
            if (player.isOnline()) {
                player.sendMessage("§cBylo ti odebrano §7" + amountToTake + " CraftCoins.");
            }
        });
    }

    @Override
    public void takeOffline(@NotNull String player, long amountToTake) {
        Main.getAsync().runAsync(() -> {
            long actualCoins = Main.getInstance().getMySQL().getPlayerEconomy(EconomyType.CRAFTCOINS, player);
            long finalCoins = actualCoins - amountToTake;
            if (finalCoins < 0) {
                return;
            }
            Main.getInstance().getMySQL().takeEconomy(EconomyType.CRAFTCOINS, player, amountToTake);
        });
    }

    @Override
    public void payBetween(@NotNull Player sender, @NotNull Player target, long amountToPay) {
        if(this.get(sender) < amountToPay) {
            return;
        }
        Main.getAsync().runAsync(() -> {
            if (!BasicManager.getCraftPlayersCache().containsKey(sender)) {
                Logger.danger("Hráč " + sender.getName() + " není v cache giveCoins zastaven!");
                return;
            }

            long currentCoinsSender = manager.getCraftPlayer(sender).getCoins();
            long newCoinsSender = currentCoinsSender - amountToPay;

            long currentCoinsTarget = manager.getCraftPlayer(target).getCoins();
            long newCoinsTarget = currentCoinsTarget + amountToPay;

            manager.getCraftPlayer(sender).setCoins(newCoinsSender);
            manager.getCraftPlayer(target).setCoins(newCoinsTarget);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTCOINS, sender, newCoinsSender);
            Main.getInstance().getMySQL().setEconomy(EconomyType.CRAFTCOINS, target, newCoinsTarget);

            target.sendMessage("§aHráč " + sender.getName() + " ti poslal §7" + amountToPay + " §aCC. Nyní máš §7" + newCoinsTarget + " §aCC.");
        });
    }
}
