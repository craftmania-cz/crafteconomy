package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.craftcore.core.builders.ArrayBuilder;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.utils.VaultUtils;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.DynamicSuggestedStringArgument;
import io.github.jorelali.commandapi.api.arguments.IntegerArgument;
import io.github.jorelali.commandapi.api.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class MoneyCommand {

    private static BasicManager manager = new BasicManager();

    public static void register() {

        // Default: /money
        CommandAPI.getInstance().register("money", new String[] {"eco"}, null, (sender, args) -> {
            Player p = (Player) sender;
            long money = (long) Main.getVaultEconomy().getBalance(p);
            p.sendMessage("§e§l[*] §eAktualne mas " + money + "$");
            System.out.println(Arrays.toString(Bukkit.getOnlinePlayers().stream().map(p1 -> ((Player) p1).getName()).toArray(String[]::new)));
        });



        // Admin prikaz: /money give|take [player] [value]
        LinkedHashMap<String, Argument> arguments = new LinkedHashMap<>();
        arguments.put("prikaz", new StringArgument().overrideSuggestions("give", "take").withPermission(CommandPermission.fromString("crafteconomy.admin")));
        arguments.put("hrac", new DynamicSuggestedStringArgument(() -> Bukkit.getOnlinePlayers().stream().map(p1 -> ((Player) p1).getName()).toArray(String[]::new)));
        arguments.put("hodnota", new IntegerArgument());

        CommandAPI.getInstance().register("money", new String[] {"eco"}, arguments, (sender, args) -> {

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
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + moneyToGive + "$.");
                    } else {
                        long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerName);
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, actualMoney + moneyToGive);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + moneyToGive + "$.");
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
                            sender.sendMessage("§c§l[!] §cHráč nemá dostatek peněz. Vlastní: " + money + "$");
                            return;
                        }
                        Main.getVaultEconomy().withdrawPlayer(player, moneyToTake);
                        sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName + " §7- §6" + moneyToTake + "$.");
                    } else {
                        long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerName);
                        if ((actualMoney - moneyToTake) < 0) {
                            sender.sendMessage("§c§l[!] §cHráč nemá dostatek peněz. Vlastní: " + actualMoney + "$");
                            return;
                        }
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, actualMoney - moneyToTake);
                        sender.sendMessage("§e§l[*] §eOdebral jsi hraci §f" + playerName + " §7- §6" + moneyToTake + "$.");
                    }
                    break;

            }
        });
    }
}
