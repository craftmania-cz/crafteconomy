package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyMoneyGiveEvent;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyMoneySetEvent;
import cz.craftmania.crafteconomy.events.vault.CraftEconomyMoneyTakeEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.UUID;

public class MoneyCommand {

    private static BasicManager manager = new BasicManager();

    public static void register() {

        // Default: /money
        CommandAPI.getInstance().register("money", new String[]{"eco", "bal", "balance"}, null, (sender, args) -> {
            Player p = (Player) sender;
            long money = (long) Main.getVaultEconomy().getBalance(p);
            p.sendMessage("§e§l[*] §eAktuálně máš " + money + Main.getInstance().getCurrency());
        });

        // Default: /money balance [nick]
        LinkedHashMap<String, Argument> moneyArguments = new LinkedHashMap<>();
        moneyArguments.put("prikaz", new LiteralArgument("balance").withPermission(CommandPermission.fromString("crafteconomy.command.money.balance")));
        moneyArguments.put("hrac", new DynamicSuggestedStringArgument(() -> Bukkit.getOnlinePlayers().stream().map(p1 -> p1.getName()).toArray(String[]::new)));
        CommandAPI.getInstance().register("money", new String[]{"eco"}, moneyArguments, (sender, args) -> {
            String searchPlayer = (String) args[0];
            long money = (long) Main.getVaultEconomy().getBalance(searchPlayer);
            sender.sendMessage("§e§l[B] §eHráč " + searchPlayer + " má na účtě: §f" + money + Main.getInstance().getCurrency());
        });

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
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + moneyToGive + Main.getInstance().getCurrency() + ".");
                        Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyGiveEvent(sender.getName(), playerName, moneyToGive));
                    } else {
                        UUID playerUUID = null;
                        try {
                            playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(playerName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerUUID);
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, actualMoney + moneyToGive);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + moneyToGive + Main.getInstance().getCurrency() + ".");
                        Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyGiveEvent(sender.getName(), playerName, moneyToGive));
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
                            sender.sendMessage("§c§l[!] §cHráč nemá dostatek peněz. Vlastní: " + money + Main.getInstance().getCurrency());
                            return;
                        }
                        Main.getVaultEconomy().withdrawPlayer(player, moneyToTake);
                        sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName + " §7- §6" + moneyToTake + Main.getInstance().getCurrency() + ".");
                        Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyTakeEvent(sender.getName(), playerName, moneyToTake));
                    } else {
                        UUID playerUUID = null;
                        try {
                            playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(playerName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerUUID);
                        if ((actualMoney - moneyToTake) < 0) {
                            sender.sendMessage("§c§l[!] §cHráč nemá dostatek peněz. Vlastní: " + actualMoney + Main.getInstance().getCurrency());
                            return;
                        }
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, actualMoney - moneyToTake);
                        sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName + " §7- §6" + moneyToTake + Main.getInstance().getCurrency() + ".");
                        Bukkit.getPluginManager().callEvent(new CraftEconomyMoneyTakeEvent(sender.getName(), playerName, moneyToTake));
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
                        sender.sendMessage("§e§l[*] §eNastavil jsi hráči " + playerName + " počet peněz na §7- §b" + moneyToSet + Main.getInstance().getCurrency());
                        player.sendMessage("§e§l[*] §eTvoje peníze byly nastaveny na §7- §f" + moneyToSet + Main.getInstance().getCurrency());
                        Bukkit.getPluginManager().callEvent(new CraftEconomyMoneySetEvent(sender.getName(), playerName, oldBalance, moneyToSet));
                    } else {
                        UUID playerUUID = null;
                        try {
                            playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(playerName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        long oldBalance = Main.getInstance().getMySQL().getVaultEcoBalance(playerUUID);
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, moneyToSet);
                        sender.sendMessage("§e§l[*] §eNastavil jsi hráči " + playerName + " počet peněz na §7- §b" + moneyToSet + Main.getInstance().getCurrency());
                        Bukkit.getPluginManager().callEvent(new CraftEconomyMoneySetEvent(sender.getName(), playerName, oldBalance, moneyToSet));
                    }
                    break;
            }
        });
    }
}
