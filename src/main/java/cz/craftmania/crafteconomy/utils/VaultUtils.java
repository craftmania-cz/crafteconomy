package cz.craftmania.crafteconomy.utils;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class VaultUtils extends AbstractEconomy {

    private BasicManager manager = new BasicManager();

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
    public boolean hasAccount(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            return manager.getCraftPlayer(player).getMoney() > -1; // Pokud ma account, má 0$
        }
        return Main.getInstance().getMySQL().hasVaultEcoProfile(playerName);
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return this.hasAccount(playerName);
    }

    @Override
    public double getBalance(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            return manager.getCraftPlayer(player).getMoney();
        } else if (hasAccount(playerName)) {
            return Main.getInstance().getMySQL().getVaultEcoBalance(playerName);
        }
        return 0;
    }

    @Override
    public double getBalance(String playerName, String world) {
        return this.getBalance(playerName);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return this.has(playerName, amount);
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
            Main.getInstance().getMySQL().setVaultEcoBalance(playerName, (long) finalBalance);
            player.sendMessage("§e§l[*] §eBylo ti odebrano: §c" + amount + Main.getInstance().getCurrency());
        } else {
            Main.getInstance().getMySQL().setVaultEcoBalance(playerName, (long) finalBalance);
        }
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return this.withdrawPlayer(playerName, amount);
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
            Main.getInstance().getMySQL().setVaultEcoBalance(playerName, (long) finalBalance);
            player.sendMessage("§a§l[*] §aBylo ti pridano: " + amount + Main.getInstance().getCurrency());
        } else {
            Main.getInstance().getMySQL().setVaultEcoBalance(playerName, (long) finalBalance);
        }
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");

    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return this.depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
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
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
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
    public boolean createPlayerAccount(String playerName, String worldName) {
        return this.createPlayerAccount(playerName);
    }
}
