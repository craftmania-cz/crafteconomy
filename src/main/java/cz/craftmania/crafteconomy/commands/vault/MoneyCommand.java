package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.craftcore.core.builders.ArrayBuilder;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
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
        arguments.put("prikaz", new StringArgument().overrideSuggestions("give").withPermission(CommandPermission.fromString("crafteconomy.admin")));
        arguments.put("hrac", new DynamicSuggestedStringArgument(() -> Bukkit.getOnlinePlayers().stream().map(p1 -> ((Player) p1).getName()).toArray(String[]::new)));
        arguments.put("hodnota", new IntegerArgument());

        CommandAPI.getInstance().register("money", new String[] {"eco"}, arguments, (sender, args) -> {
            String subCommand = (String)args[0];
            switch (subCommand.toLowerCase()) {
                case "give":
                    String playerName = (String)args[1];
                    Player player = Bukkit.getPlayer(playerName);
                    long moneyToGive = Long.valueOf((Integer)args[2]);
                    if (player != null) {
                        Main.getVaultEconomy().depositPlayer(player, moneyToGive);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + moneyToGive + "$.");
                    } else {
                        long actualMoney = Main.getInstance().getMySQL().getVaultEcoBalance(playerName);
                        Main.getInstance().getMySQL().setVaultEcoBalance(playerName, moneyToGive + actualMoney);
                        sender.sendMessage("§e§l[*] §ePridal jsi hraci §f" + playerName + " §7- §6" + moneyToGive + "$.");
                    }
                    break;
            }
        });
    }
}
