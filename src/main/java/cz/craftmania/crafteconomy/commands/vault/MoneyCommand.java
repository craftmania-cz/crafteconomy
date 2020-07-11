package cz.craftmania.crafteconomy.commands.vault;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyMoneyGiveEvent;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyMoneySetEvent;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyMoneyTakeEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandAlias("money|bal|eco|balance|penize")
@Description("Umoznuje ti zobrazit tvoje penize")
public class MoneyCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lMoney commands:");
        help.showHelp();
    }

    @Default
    public void showMoney(CommandSender sender) {
        if (sender instanceof Player)
            sender.sendMessage("§e§l[*] §eAktuálně máš " + Main.getInstance().getFormattedNumber((long) Main.getVaultEconomy().getBalance((Player) sender)) + Main.getInstance().getCurrency());
    }

    @Default
    @CommandCompletion("@players")
    @Syntax("[nick]")
    public void showOthersMoney(CommandSender sender, String targetPlayer) {
        if (Main.getInstance().getMySQL().hasDataByNick(targetPlayer)) {
            sender.sendMessage("§e§l[*] §eHráč " + targetPlayer + " má na účtě: §f" + Main.getInstance().getFormattedNumber((long) Main.getVaultEconomy().getBalance(targetPlayer)) + Main.getInstance().getCurrency());
        } else {
            sender.sendMessage("§c§l[!] §cTento hráč zde ještě nehrál!");
        }
    }

    @Default
    @CommandPermission("crafteconomy.command.money.edit")
    @CommandCompletion("give|take|set @players [mnozstvi]")
    @Syntax("[give|take|set] [hrac] [mnozstvi]")
    public void adminGiveMoney(CommandSender sender, String action, String targetPlayer, long moneyToEdit) {
        Player player = Bukkit.getPlayer(targetPlayer);
        switch (action) {
            case "give": {
                // Checks
                if (moneyToEdit <= 0) {
                    sender.sendMessage("§c§l[!] §cNelze přidávat nulovou nebo zápornou hodnotu!");
                    return;
                }
                // Give
                if (player != null) {
                    Main.getVaultEconomy().depositPlayer(player, moneyToEdit);
                    sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + targetPlayer + " §7- §6" + Main.getInstance().getFormattedNumber(moneyToEdit) + Main.getInstance().getCurrency() + ".");
                    Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyGiveEvent(sender.getName(), targetPlayer, moneyToEdit)));
                } else {
                    UUID playerUUID = null;
                    try {
                        playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(targetPlayer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerUUID);
                    Main.getInstance().getMySQL().setVaultEcoBalance(targetPlayer, actualMoney + moneyToEdit);
                    sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + targetPlayer + " §7- §6" + Main.getInstance().getFormattedNumber(moneyToEdit) + Main.getInstance().getCurrency() + ".");
                    Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyGiveEvent(sender.getName(), targetPlayer, moneyToEdit)));
                }
                break;
            }
            case "take": {
                // Checks
                if (moneyToEdit <= 0) {
                    sender.sendMessage("§c§l[!] §cNelze odebírat nulovou nebo zápornou hodnotu!");
                    return;
                }
                // Take
                if (player != null) {
                    long money = manager.getCraftPlayer(player).getMoney();
                    if ((money - moneyToEdit) < 0) {
                        sender.sendMessage("§c§l[!] §cHráč nemá dostatek peněz. Vlastní: " + Main.getInstance().getFormattedNumber(money) + Main.getInstance().getCurrency());
                        return;
                    }
                    Main.getVaultEconomy().withdrawPlayer(player, moneyToEdit);
                    sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + targetPlayer + " §7- §6" + Main.getInstance().getFormattedNumber(moneyToEdit) + Main.getInstance().getCurrency() + ".");
                    Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyTakeEvent(sender.getName(), targetPlayer, moneyToEdit)));
                } else {
                    UUID playerUUID = null;
                    try {
                        playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(targetPlayer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerUUID);
                    if ((actualMoney - moneyToEdit) < 0) {
                        sender.sendMessage("§c§l[!] §cHráč nemá dostatek peněz. Vlastní: " + Main.getInstance().getFormattedNumber(actualMoney) + Main.getInstance().getCurrency());
                        return;
                    }
                    Main.getInstance().getMySQL().setVaultEcoBalance(targetPlayer, actualMoney - moneyToEdit);
                    sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + targetPlayer + " §7- §6" + Main.getInstance().getFormattedNumber(moneyToEdit) + Main.getInstance().getCurrency() + ".");
                    Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyTakeEvent(sender.getName(), targetPlayer, moneyToEdit)));
                }
                break;
            }
            case "set": {
                if (moneyToEdit <= 0) {
                    sender.sendMessage("§c§l[!] §cNelze nastavovat nulovou nebo zápornou hodnotu!");
                    return;
                }
                if (player != null) {
                    long oldBalance = manager.getCraftPlayer(player).getMoney();
                    manager.getCraftPlayer(player).setMoney(moneyToEdit);
                    sender.sendMessage("§e§l[*] §eNastavil jsi hráči " + targetPlayer + " počet peněz na §7- §b" + Main.getInstance().getFormattedNumber(moneyToEdit) + Main.getInstance().getCurrency());
                    player.sendMessage("§e§l[*] §eTvoje peníze byly nastaveny na §7- §f" + Main.getInstance().getFormattedNumber(moneyToEdit) + Main.getInstance().getCurrency());
                    Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneySetEvent(sender.getName(), targetPlayer, oldBalance, moneyToEdit)));
                } else {
                    UUID playerUUID = null;
                    try {
                        playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(targetPlayer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    long oldBalance = Main.getInstance().getMySQL().getVaultEcoBalance(playerUUID);
                    Main.getInstance().getMySQL().setVaultEcoBalance(targetPlayer, moneyToEdit);
                    sender.sendMessage("§e§l[*] §eNastavil jsi hráči " + targetPlayer + " počet peněz na §7- §b" + Main.getInstance().getFormattedNumber(moneyToEdit) + Main.getInstance().getCurrency());
                    Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneySetEvent(sender.getName(), targetPlayer, oldBalance, moneyToEdit)));
                }
                break;
            }
        }
    }

    /*
    public static void register() {
        // Admin prikaz: /money give|take [player] [value]
        LinkedHashMap<String, Argument> moneyAdminArgumenets = new LinkedHashMap<>();
        moneyAdminArgumenets.put("prikaz", new StringArgument().overrideSuggestions("give", "take").withPermission(CommandPermission.fromString("crafteconomy.command.money.edit")));
        moneyAdminArgumenets.put("hrac", new DynamicSuggestedStringArgument(() -> Bukkit.getOnlinePlayers().stream().map(p1 -> p1.getName()).toArray(String[]::new)));
        moneyAdminArgumenets.put("hodnota", new IntegerArgument());

        CommandAPI.getInstance().register("money", new String[]{"eco", "bal", "balance"}, moneyAdminArgumenets, (sender, args) -> {

            String subCommand = (String) args[0];
            String playerName = (String) args[1];
            Player player = Bukkit.getPlayer(playerName);

            switch (subCommand.toLowerCase()) {
                case "give":
                    long moneyToGive = Long.valueOf((Integer) args[2]);
                    // Checks
                    if (moneyToGive <= 0) {
                        sender.sendMessage("§c§l[!] §cNelze přidávat nulovou nebo zápornou hodnotu!");
                        return;
                    }
                    // Give
                    if (player != null) {
                        Main.getVaultEconomy().depositPlayer(player, moneyToGive);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + Main.getInstance().getFormattedNumber(moneyToGive) + Main.getInstance().getCurrency() + ".");
                        Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyGiveEvent(sender.getName(), playerName, moneyToGive)));
                    } else {
                        UUID playerUUID = null;
                        try {
                            playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(playerName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerUUID);
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, actualMoney + moneyToGive);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + Main.getInstance().getFormattedNumber(moneyToGive) + Main.getInstance().getCurrency() + ".");
                        Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyGiveEvent(sender.getName(), playerName, moneyToGive)));
                    }
                    break;
                case "take":
                    long moneyToTake = Long.valueOf((Integer) args[2]);
                    // Checks
                    if (moneyToTake <= 0) {
                        sender.sendMessage("§c§l[!] §cNelze odebírat nulovou nebo zápornou hodnotu!");
                        return;
                    }
                    // Take
                    if (player != null) {
                        long money = manager.getCraftPlayer(player).getMoney();
                        if ((money - moneyToTake) < 0) {
                            sender.sendMessage("§c§l[!] §cHráč nemá dostatek peněz. Vlastní: " + Main.getInstance().getFormattedNumber(money) + Main.getInstance().getCurrency());
                            return;
                        }
                        Main.getVaultEconomy().withdrawPlayer(player, moneyToTake);
                        sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName + " §7- §6" + Main.getInstance().getFormattedNumber(moneyToTake) + Main.getInstance().getCurrency() + ".");
                        Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyTakeEvent(sender.getName(), playerName, moneyToTake)));
                    } else {
                        UUID playerUUID = null;
                        try {
                            playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(playerName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerUUID);
                        if ((actualMoney - moneyToTake) < 0) {
                            sender.sendMessage("§c§l[!] §cHráč nemá dostatek peněz. Vlastní: " + Main.getInstance().getFormattedNumber(actualMoney) + Main.getInstance().getCurrency());
                            return;
                        }
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, actualMoney - moneyToTake);
                        sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName + " §7- §6" + Main.getInstance().getFormattedNumber(moneyToTake) + Main.getInstance().getCurrency() + ".");
                        Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyTakeEvent(sender.getName(), playerName, moneyToTake)));
                    }
                    break;
                case "set":
                    long moneyToSet = Long.valueOf((Integer) args[2]);
                    if (moneyToSet >= 0) {
                        sender.sendMessage("§c§l[!] §cNelze nastavovat nulovou nebo zápornou hodnotu!");
                        return;
                    }
                    if (player != null) {
                        long oldBalance = manager.getCraftPlayer(player).getMoney();
                        manager.getCraftPlayer(player).setMoney(moneyToSet);
                        sender.sendMessage("§e§l[*] §eNastavil jsi hráči " + playerName + " počet peněz na §7- §b" + Main.getInstance().getFormattedNumber(moneyToSet) + Main.getInstance().getCurrency());
                        player.sendMessage("§e§l[*] §eTvoje peníze byly nastaveny na §7- §f" + Main.getInstance().getFormattedNumber(moneyToSet) + Main.getInstance().getCurrency());
                        Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneySetEvent(sender.getName(), playerName, oldBalance, moneyToSet)));
                    } else {
                        UUID playerUUID = null;
                        try {
                            playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(playerName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        long oldBalance = Main.getInstance().getMySQL().getVaultEcoBalance(playerUUID);
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, moneyToSet);
                        sender.sendMessage("§e§l[*] §eNastavil jsi hráči " + playerName + " počet peněz na §7- §b" + Main.getInstance().getFormattedNumber(moneyToSet) + Main.getInstance().getCurrency());
                        Main.getAsync().runAsync(() -> Bukkit.getPluginManager().callEvent(new CraftEconomyMoneySetEvent(sender.getName(), playerName, oldBalance, moneyToSet)));
                    }
                    break;
            }
        });
    }*/
}
