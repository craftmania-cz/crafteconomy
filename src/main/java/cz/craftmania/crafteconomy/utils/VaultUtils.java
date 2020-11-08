package cz.craftmania.crafteconomy.utils;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.vault.PlayerVaultDepositEvent;
import cz.craftmania.crafteconomy.events.vault.PlayerVaultWithdrawEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class VaultUtils extends AbstractEconomy {

    private BasicManager manager = new BasicManager();

    private <T extends Event> void callEvent(T event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    private <T extends Event> void callAsyncEvent(T event) {
        Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(event));
    }

    @Override
    public boolean isEnabled() {
        return Main.getInstance().isEnabled();
    }

    @Override
    public String getName() {
        return "CraftEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return String.valueOf(amount);
    }

    @Override
    public String currencyNamePlural() {
        return Main.getInstance().getCurrency();
    }

    @Override
    public String currencyNameSingular() {
        return Main.getInstance().getCurrency();
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return hasAccount(player.getName());
    }

    @Override
    public boolean hasAccount(String playerName) {
        if (playerName == null){
            return false;
        }
        CraftPlayer player = manager.getCraftPlayer(playerName);
        if (player != null && player.getMoney() >= 0) {
            return true;
        }
        return Main.getInstance().getMySQL().hasVaultEcoProfile(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player.getName(), worldName);
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public double getBalance(String playerName) {
        CraftPlayer player = manager.getCraftPlayer(playerName);
        if (player == null) {
            return Main.getInstance().getMySQL().getVaultEcoBalance(playerName);
        }
        return player.getMoney();
    }

    @Override
    public double getBalance(OfflinePlayer playerName) {
        if (playerName.isOnline()) {
            return manager.getCraftPlayer((Player) playerName).getMoney();
        } else if (hasAccount(playerName)) {
            return Main.getInstance().getMySQL().getVaultEcoBalance(playerName.getUniqueId());
        }
        return 0;
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player.getName(), world);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return has(player.getName(), amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player.getName(), worldName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (!hasAccount(playerName)){
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Tento hrac nema vytvoreny balance profil!");
        }
        if (amount < 0) {
            return new EconomyResponse(0.0, getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Hodnota je mensi nez 0!");
        }
        double actualBalance = getBalance(playerName);
        double finalBalance = actualBalance - amount;
        if (finalBalance < 0) {
            return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Hrac nema dostatek penez!");
        }
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            CraftPlayer craftPlayer = manager.getCraftPlayer(player);
            craftPlayer.setMoney((long) finalBalance);
            callAsyncEvent(new PlayerVaultWithdrawEvent(player, amount, EconomyResponse.ResponseType.SUCCESS));
            player.sendMessage("§e§l[*] §eBylo ti odebrano: §c" + Main.getInstance().getFormattedNumber((long) amount) + Main.getInstance().getCurrency());
        } else {
            Main.getInstance().getMySQL().setVaultEcoBalance(playerName, (long) finalBalance);
            callAsyncEvent(new PlayerVaultWithdrawEvent(Bukkit.getOfflinePlayer(playerName), amount, EconomyResponse.ResponseType.SUCCESS));
        }
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        return withdrawPlayer(player.getName(), amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player.getName(), worldName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        if (!hasAccount(playerName)){
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Tento hrac nema vytvoreny balance profil!");
        }
        if (amount < 0) {
            return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Hodnota je mensi nez 0!");
        }
        double actualBalance = getBalance(playerName);
        double finalBalance = actualBalance + amount;
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            CraftPlayer craftPlayer = manager.getCraftPlayer(player);
            craftPlayer.setMoney((long) finalBalance);
            callAsyncEvent(new PlayerVaultDepositEvent(player, amount, EconomyResponse.ResponseType.SUCCESS));
            player.sendMessage("§a§l[*] §aBylo ti pridano: " + Main.getInstance().getFormattedNumber((long) amount) + Main.getInstance().getCurrency());
        } else {
            Main.getInstance().getMySQL().setVaultEcoBalance(playerName, (long) finalBalance);
            callAsyncEvent(new PlayerVaultDepositEvent(Bukkit.getOfflinePlayer(playerName), amount, EconomyResponse.ResponseType.SUCCESS));
        }
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");

    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        return depositPlayer(player.getName(), amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player.getName(), worldName, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return createBank(name, player.getName());
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return isBankOwner(name, player.getName());
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return isBankMember(name, player.getName());
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    //TODO: Create
    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return createPlayerAccount(player.getName());
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player.getName(), worldName);
    }
}
