package cz.craftmania.crafteconomy.commands.vault;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.craftcore.core.mojang.MojangAPI;
import cz.craftmania.craftcore.core.utils.Group;
import cz.craftmania.crafteconomy.Main;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.DynamicSuggestedStringArgument;
import io.github.jorelali.commandapi.api.arguments.IntegerArgument;
import io.github.jorelali.commandapi.api.arguments.LiteralArgument;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

@CommandAlias("moneylog")
@Description("Zobrazí log transakcí pro určitého hráče")
public class MoneylogCommand extends BaseCommand {

    static int maxTableSize = 10;

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lMoneylog commands:");
        help.showHelp();
    }

    @Default
    @CommandCompletion("@players")
    @CommandPermission("crafteconomy.admin")
    public void showLogByName(CommandSender sender, String requestedPlayer) {
        Group<String, UUID> UUIDdata = null;
        Map<Integer, List> listMap = new HashMap<>();
        try {
            UUIDdata = MojangAPI.getUUID(requestedPlayer);
            listMap = Main.getInstance().getMySQL().getVaultAllLogsByUUID(UUIDdata.getB().toString());
        } catch (Exception e) {
            listMap = Main.getInstance().getMySQL().getVaultAllLogsByNickname(requestedPlayer);
        }
        printTableForPlayer(sender, listMap, 1);
    }

    @Default
    @CommandCompletion("@players [cislo]")
    @CommandPermission("crafteconomy.admin")
    public void showLogByNameAndPage(CommandSender sender, String requestedPlayer, int page) {
        Group<String, UUID> UUIDdata = null;
        Map<Integer, List> listMap = new HashMap<>();
        try {
            UUIDdata = MojangAPI.getUUID(requestedPlayer);
            listMap = Main.getInstance().getMySQL().getVaultAllLogsByUUID(UUIDdata.getB().toString());
        } catch (Exception e) {
            listMap = Main.getInstance().getMySQL().getVaultAllLogsByNickname(requestedPlayer);
        }
        printTableForPlayer(sender, listMap, page);
    }

    private static void printTableForPlayer(CommandSender player, Map<Integer, List> listMap, int page) {
        List<String> recieverNick = listMap.get(1);
        List<String> action = listMap.get(5);
        List<Long> amount = listMap.get(6);
        List<Long> time = listMap.get(7);

        if (recieverNick.size() == 0) {
            player.sendMessage(ChatColor.RED + "Takový hráč/ka neexistuje nebo neprovedl/a žádnou platbu nebo zde ještě nehrál/a!");
            return;
        }

        if ((int) (Math.round((double) recieverNick.size() / 10)) < 1) {
            if (page > 1) {
                player.sendMessage(ChatColor.RED + "Taková strana neexistuje!");
                return;
            }
        } else if (page > (int) (Math.round((double) recieverNick.size() / 10))) {
            player.sendMessage(ChatColor.RED + "Taková strana neexistuje!");
            return;
        }

        player.sendMessage("");
        player.sendMessage("§e---- §aMoneyLog §e-- §7Strana §c" + page + "§8/§c" + (int) (Math.round((double) recieverNick.size() / 10)) + " §e-- §7Nick: §c" + recieverNick.get(0));
        try {
            for (int x = page * 10 - maxTableSize; x < page * 10; x++) {
                String akceTranslated;
                switch (action.get(x)) {
                    case "MONEY_WITHDRAW": {
                        akceTranslated = "§aVýběr";
                        break;
                    }
                    case "MONEY_DEPOSIT": {
                        akceTranslated = "§cVklad";
                        break;
                    }
                    default:
                        akceTranslated = action.get(x) + "(unknown)";
                }
                player.sendMessage("§a" + (x + 1) + "§7. §7Akce: " + akceTranslated + "§8; §7Částka: §e" + Main.getInstance().getFormattedNumber(amount.get(x)) + "§6" + Main.getInstance().getCurrency() + "§8; §7Datum: §e" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(time.get(x)));
            }
        } catch (Exception ignored) {
        }
        player.sendMessage("§e--------");
        //player.sendMessage("§b<- §7Předchozí strana §8| §7 Další Strana §b->");
        player.sendMessage("");
    }
}
