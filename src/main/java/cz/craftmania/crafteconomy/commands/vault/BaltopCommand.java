package cz.craftmania.crafteconomy.commands.vault;


import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.sql.SQLManager;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.IntegerArgument;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaltopCommand {

    static int maxTableSize = 10;

    public static void register() {
        CommandAPI.getInstance().register("baltop", new String[]{"ecotop", "moneytop"}, null, (sender, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Map<Integer, List> listMap = Main.getInstance().getMySQL().getVaultAllEcosWithNicks();
                printTableForPlayer(player, listMap, 1);
            }
        });

        LinkedHashMap<String, Argument> arguments = new LinkedHashMap<>();
        arguments.put("Strana", new IntegerArgument());
        CommandAPI.getInstance().register("baltop", new String[]{"ecotop", "moneytop"}, arguments, (sender, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if ((int) args[0] < 1) {
                    player.sendMessage(ChatColor.RED + "Číslo nesmí být menší než 1!");
                    return;
                }

                Map<Integer, List> listMap = Main.getInstance().getMySQL().getVaultAllEcosWithNicks();
                printTableForPlayer(player, listMap, (int) args[0]);
            }
        });
    }
    private static void printTableForPlayer(Player player, Map<Integer, List> listMap, int page) {
        List<String> nicks = listMap.get(1);
        List<Long> balances = listMap.get(2);

        if (page > (int)(Math.round((double)nicks.size()/10))) {
            player.sendMessage(ChatColor.RED + "Taková strana neexistuje!");
            return;
        }

        player.sendMessage("");
        player.sendMessage("§e---- §aBalanceTop §e-- §7Strana §c" + page + "§8/§c" + (int)(Math.round((double)nicks.size()/10)) + " §e-- ");
        try {
            for (int x=page * 10 - maxTableSize; x<page * 10; x++) {
                player.sendMessage("§a" + (x+1) + "§7. §b" + nicks.get(x) + "§8 - §e" + balances.get(x) + "§6" + Main.getInstance().getCurrency());
            }
        } catch (Exception ignored) {}
        player.sendMessage("§e--------");
        try {
            player.sendMessage("§7Tvoje pozice: §a" + (nicks.indexOf(player.getName())+1) + "§7. - §e" + balances.get(nicks.indexOf(player.getName())) + "§6" + Main.getInstance().getCurrency());
        } catch (Exception ignored) {
            player.sendMessage(" §7Tvoje pozice: §a#§7. - §e0§6" + Main.getInstance().getCurrency());
        }
        //player.sendMessage("§e--------");
        //player.sendMessage("§b<- §7Předchozí strana §8| §7 Další Strana §b->");
        player.sendMessage("");
    }
}
