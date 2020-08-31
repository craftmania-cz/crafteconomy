package cz.craftmania.crafteconomy.commands.vault;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.Main;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@CommandAlias("baltop|moneytop")
@Description("Vypíše ti hráče s nejvíce penězmi na daném serveru")
public class BaltopCommand extends BaseCommand {

    private static int maxTableSize = 10;

    private static void printTableForPlayer(Player player, Map<String, Long> balanceMap, int page) {
        balanceMap = balanceMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        List<String> nicks = new ArrayList<>(balanceMap.keySet());
        List<Long> balances = new ArrayList<>(balanceMap.values());

        if (page > (int) (Math.round((double) nicks.size() / 10))) {
            player.sendMessage(ChatColor.RED + "Taková strana neexistuje!");
            return;
        }

        player.sendMessage("");
        player.sendMessage("§e---- §aBalanceTop §e-- §7Strana §c" + page + "§8/§c" + (int) (Math.round((double) nicks.size() / 10)) + " §e-- ");
        try {
            for (int x = page * 10 - maxTableSize; x < page * 10; x++) {
                player.sendMessage("§a" + (x + 1) + "§7. §b" + nicks.get(x) + "§8 - §e" + Main.getInstance().getFormattedNumber(balances.get(x)) + "§6" + Main.getInstance().getCurrency());
            }
        } catch (Exception ignored) {
        }
        player.sendMessage("§e--------");
        try {
            player.sendMessage("§7Tvoje pozice: §a" + (nicks.indexOf(player.getName()) + 1) + "§7. - §e" + Main.getInstance().getFormattedNumber(balances.get(nicks.indexOf(player.getName()))) + "§6" + Main.getInstance().getCurrency());
        } catch (Exception ignored) {
            player.sendMessage(" §7Tvoje pozice: §a#§7. - §e0§6" + Main.getInstance().getCurrency());
        }
        player.sendMessage("§e--------");
        sendClickablePages(player, page, (int) (Math.round((double) nicks.size() / 10)));
        player.sendMessage("");
    }

    private static void sendClickablePages(Player player, int page, int maxPage) {
        String prevPageColor, nextPageColor;
        TextComponent message = new TextComponent();
        TextComponent prevPageMessage = new TextComponent();
        TextComponent nextPageMessage = new TextComponent();

        if (page == 1)
            prevPageColor = "§7";
        else {
            prevPageColor = "§e";
            prevPageMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/baltop " + (page - 1)));
        }
        if (page >= maxPage)
            nextPageColor = "§7";
        else {
            nextPageColor = "§e";
            nextPageMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/baltop " + (page + 1)));
        }

        prevPageMessage.setText("§b<- " + prevPageColor + "Předchozí strana §8|");
        nextPageMessage.setText(" §7 " + nextPageColor + "Další Strana §b->");

        message.addExtra(prevPageMessage);
        message.addExtra(nextPageMessage);

        player.spigot().sendMessage(message);
    }

    private static BaseComponent[] createComponent(List<String> lines) {
        StringBuilder str = new StringBuilder();
        for (String line : lines) {
            if (lines.get(lines.size() - 1).equals(line))
                str.append(line);
            else
                str.append(line).append("\n");
        }
        return new ComponentBuilder(str.toString()).create();
    }

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lBaltop commands:");
        help.showHelp();
    }

    @Default
    public void showBaltopDefault(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            printTableForPlayer(player, Main.getInstance().getVaultEconomyManager().getBaltopCache(), 1);
        }
    }

    @Default
    @CommandCompletion("[cislo]")
    @Syntax("[cislo]")
    public void showBaltopByPage(CommandSender sender, int page) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (page < 1) {
                player.sendMessage(ChatColor.RED + "Číslo nesmí být menší než 1!");
                return;
            }
            printTableForPlayer(player, Main.getInstance().getVaultEconomyManager().getBaltopCache(), page);
        }
    }
}
