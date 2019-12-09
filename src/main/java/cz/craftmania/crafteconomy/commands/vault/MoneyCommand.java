package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.craftcore.core.builders.ArrayBuilder;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.utils.VaultUtils;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class MoneyCommand {

    private static BasicManager manager = new BasicManager();

    public static void register() {

        // Default: /money
        CommandAPI.getInstance().register("money", new String[]{"eco"}, null, (sender, args) -> {
            Player p = (Player) sender;
            long money = (long) Main.getVaultEconomy().getBalance(p);
            p.sendMessage("§e§l[*] §eAktualne mas " + money + Main.getInstance().getCurrency());
        });

        // Default: /money balance [nick]
        LinkedHashMap<String, Argument> moneyArguments = new LinkedHashMap<>();
        moneyArguments.put("prikaz", new LiteralArgument("balance").withPermission(CommandPermission.fromString("crafteconomy.command.money.balance")));
        moneyArguments.put("hrac", new DynamicSuggestedStringArgument(() -> Bukkit.getOnlinePlayers().stream().map(p1 -> ((Player) p1).getName()).toArray(String[]::new)));
        CommandAPI.getInstance().register("money", new String[] {"eco"}, moneyArguments, (sender, args) -> {
            String searchPlayer = (String)args[0];
            long money = (long) Main.getVaultEconomy().getBalance(searchPlayer);
            sender.sendMessage("§e§l[WB] §eHráč " + searchPlayer + " má na účtě: §f" + money + Main.getInstance().getCurrency());
        });

        // Admin prikaz: /money give|take [player] [value]
        LinkedHashMap<String, Argument> moneyAdminArgumenets = new LinkedHashMap<>();
        moneyAdminArgumenets.put("prikaz", new StringArgument().overrideSuggestions("give", "take").withPermission(CommandPermission.fromString("crafteconomy.command.money.edit")));
        moneyAdminArgumenets.put("hrac", new DynamicSuggestedStringArgument(() -> Bukkit.getOnlinePlayers().stream().map(p1 -> ((Player) p1).getName()).toArray(String[]::new)));
        moneyAdminArgumenets.put("hodnota", new IntegerArgument());

        CommandAPI.getInstance().register("money", new String[] {"eco"}, moneyAdminArgumenets, (sender, args) -> {

            String subCommand = (String)args[0];
            String playerName = (String)args[1];
            Player player = Bukkit.getPlayer(playerName);

            switch (subCommand.toLowerCase()) {
                case "give":
                    long moneyToGive = Long.valueOf((Integer)args[2]);
                    // Checks
                    if (moneyToGive <= 0) {
                        sender.sendMessage("§c§l[!] §cNelze přidávat nulovou nebo zápornou hodnotu!");
                        return;
                    }
                    // Give
                    if (player != null) {
                        Main.getVaultEconomy().depositPlayer(player, moneyToGive);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + moneyToGive + Main.getInstance().getCurrency() + ".");
                    } else {
                        long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerName);
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, actualMoney + moneyToGive);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + moneyToGive + Main.getInstance().getCurrency() + ".");
                    }
                    break;
                case "take":
                    long moneyToTake = Long.valueOf((Integer)args[2]);
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
                    } else {
                        long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerName);
                        if ((actualMoney - moneyToTake) < 0) {
                            sender.sendMessage("§c§l[!] §cHráč nemá dostatek peněz. Vlastní: " + actualMoney + Main.getInstance().getCurrency());
                            return;
                        }
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, actualMoney - moneyToTake);
                        sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName + " §7- §6" + moneyToTake + Main.getInstance().getCurrency() + ".");
                    }
                    break;
            }
        });

        // Default: /pay [castka] [nick]
        LinkedHashMap<String, Argument> payArguments = new LinkedHashMap<>();
        payArguments.put("hrac", new DynamicSuggestedStringArgument(() -> Bukkit.getOnlinePlayers().stream().map(p1 -> ((Player) p1).getName()).toArray(String[]::new)));
        payArguments.put("money", new IntegerArgument());
        CommandAPI.getInstance().register("pay", new String[] {}, payArguments, (sender, args) -> {
            String reciever = (String)args[0];
            long moneyToSend = (long) Integer.parseInt(args[1].toString());
            if (moneyToSend <= 0) {
                sender.sendMessage("§c§l[!] §cNelze odesílat nulovou nebo zápornou hodnotu!");
                return;
            }
            if (sender.getName().equals(reciever)) {
                sender.sendMessage("§c§l[!] §cSám sobě nelze zasílat částky, bankovní podvody nevedeme!");
                return;
            }
            if (moneyToSend > Main.getVaultEconomy().getBalance(sender.getName())) {
                sender.sendMessage("§c§l[!] §cNemáš dostatek peněz k odeslání zadané částky.");
                return;
            }
            Player playerReciever = Bukkit.getPlayer(reciever);
            Player playerSender = Bukkit.getPlayer(String.valueOf(sender.getName()));
            if (playerReciever != null) {
                Main.getVaultEconomy().withdrawPlayer(playerSender, moneyToSend);
                Main.getVaultEconomy().depositPlayer(playerReciever, moneyToSend);
                sender.sendMessage("§e§l[*] §eOdeslal jsi hráči: §f" + moneyToSend + Main.getInstance().getCurrency());
                playerReciever.sendMessage("§e§l[*] §eObdržel jsi peníze od §f" + playerSender.getName() + " §7- §a" + moneyToSend + Main.getInstance().getCurrency());
            } else {
                sender.sendMessage("§c§l[!] §cHráč není online, nelze mu zaslat peníze!");
            }
        });
    }
}
