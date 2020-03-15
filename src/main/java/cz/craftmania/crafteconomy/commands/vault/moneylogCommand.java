package cz.craftmania.crafteconomy.commands.vault;

import cz.craftmania.craftcore.core.mojang.MojangAPI;
import cz.craftmania.craftcore.core.utils.Group;
import cz.craftmania.crafteconomy.Main;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.*;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class moneylogCommand {

    static int maxTableSize = 10;

    public static void register() {
        LinkedHashMap<String, Argument> moneylogArguments = new LinkedHashMap<>();
        moneylogArguments.put("prikaz", new LiteralArgument("log"));
        //moneylogArguments.put("prikaz", new LiteralArgument("log").withPermission(CommandPermission.fromString("crafteconomy.command.money.log")));
        CommandAPI.getInstance().register("money", new String[]{"eco", "bal", "balance"}, moneylogArguments, (sender, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.RED + "Správný syntax příkazu je /money log <nick>!");
            }
        });


        moneylogArguments.put("hrac", new DynamicSuggestedStringArgument(() -> Bukkit.getOnlinePlayers().stream().map(p1 -> p1.getName()).toArray(String[]::new)));
        CommandAPI.getInstance().register("money", new String[]{"eco", "bal", "balance"}, moneylogArguments, (sender, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String requestedPlayer = (String) args[0];
                Group<String, UUID> UUIDdata = null;
                Map<Integer, List> listMap = new HashMap<Integer, List>();

                try {
                    UUIDdata = MojangAPI.getUUID(requestedPlayer);
                    listMap = Main.getInstance().getMySQL().getVaultAllLogsByUUID(UUIDdata.getB().toString());
                } catch (Exception e) {
                    listMap = Main.getInstance().getMySQL().getVaultAllLogsByNickname(requestedPlayer);
                }

                printTableForPlayer(player, listMap, 1);
            }
        });

        moneylogArguments.put("strana", new IntegerArgument());
        CommandAPI.getInstance().register("money", new String[]{"eco", "bal", "balance"}, moneylogArguments, (sender, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String requestedPlayer = (String) args[0];
                Group<String, UUID> UUIDdata = null;
                Map<Integer, List> listMap = new HashMap<Integer, List>();

                try {
                    UUIDdata = MojangAPI.getUUID(requestedPlayer);
                    listMap = Main.getInstance().getMySQL().getVaultAllLogsByUUID(UUIDdata.getB().toString());
                } catch (Exception e) {
                    listMap = Main.getInstance().getMySQL().getVaultAllLogsByNickname(requestedPlayer);
                }

                printTableForPlayer(player, listMap, (int) args[1]);
            }
        });

    }



    private static void printTableForPlayer(Player player, Map<Integer, List> listMap, int page) {
        List<String> recieverNick = listMap.get(1);
        //List<String> recieverUUID = listMap.get(2);
        //List<String> senderNick = listMap.get(3);
        //List<String> senderUUID = listMap.get(4);
        List<String> action = listMap.get(5);
        List<Long> amount = listMap.get(6);
        List<Long> time = listMap.get(7);

        if (recieverNick.size() == 0) {
            player.sendMessage(ChatColor.RED + "Takový hráč/ka neexistuje nebo neprovedl/a žádnou platbu nebo zde ještě nehrál/a!");
            return;
        }

        if ((int)(Math.round((double)recieverNick.size()/10)) < 1) {
            if (page > 1) {
                player.sendMessage(ChatColor.RED + "Taková strana neexistuje!");
                return;
            }
        } else if (page > (int)(Math.round((double)recieverNick.size()/10))) {
            player.sendMessage(ChatColor.RED + "Taková strana neexistuje!");
            return;
        }

        player.sendMessage("");
        player.sendMessage("§e---- §aMoneyLog §e-- §7Strana §c" + page + "§8/§c" + (int)(Math.round((double)recieverNick.size()/10)) + " §e-- §7Nick: §c" + recieverNick.get(0));
        try {
            for (int x=page * 10 - maxTableSize; x<page * 10; x++) {
                String akceTranslated;
                switch (action.get(x)) {
                    case "MONEY_WITHDRAW": {
                        /* Alternativní zbarvování zprav
                        if (x % 2 == 0) {
                            akceTranslated = "§2Výběr";
                        } else {*/
                            akceTranslated = "§aVýběr";
                        //}
                        break;
                    }
                    case "MONEY_DEPOSIT": {
                        /* Alternativní zbarvování zprav
                        if (x % 2 == 0) {
                            akceTranslated = "§4Vklad";
                        } else {*/
                            akceTranslated = "§cVklad";
                        //}
                        break;
                    }
                    default: akceTranslated = action.get(x) + "(unknown)";
                }
                /* Alternativní zbarvování zprav
                if (x % 2 == 0) {
                    player.sendMessage("§a" + (x+1) + "§7. §8Akce: " + akceTranslated + "§7; §8Částka: §6" + amount.get(x) + "§e" + Main.getInstance().getCurrency() + "§7; §8Datum: §6" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(time.get(x)));
                } else {*/
                    player.sendMessage("§a" + (x+1) + "§7. §7Akce: " + akceTranslated + "§8; §7Částka: §e" + amount.get(x) + "§6" + Main.getInstance().getCurrency() + "§8; §7Datum: §e" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(time.get(x)));
                //}
            }
        } catch (Exception ignored) {}
        player.sendMessage("§e--------");
        //player.sendMessage("§7Tvoje pozice: §a" + (nicks.indexOf(player.getName())+1) + "§7. - §e" + balances.get(nicks.indexOf(player.getName())) + Main.getInstance().getCurrency());
        //player.sendMessage("§e--------");
        //player.sendMessage("§b<- §7Předchozí strana §8| §7 Další Strana §b->");
        player.sendMessage("");
    }
}
