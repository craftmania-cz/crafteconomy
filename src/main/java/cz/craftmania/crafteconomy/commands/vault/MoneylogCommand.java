package cz.craftmania.crafteconomy.commands.vault;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.objects.EconomyLog;
import cz.craftmania.crafteconomy.objects.Pagination;
import cz.craftmania.crafteconomy.utils.Logger;
import cz.craftmania.craftlibs.utils.ChatInfo;
import cz.craftmania.craftlibs.utils.TextComponentBuilder;
import jline.internal.Log;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@CommandAlias("moneylog")
@Description("Zobrazí log transakcí pro určitého hráče")
public class MoneylogCommand extends BaseCommand {

    private HashMap<CommandSender, String> requests = new HashMap<>();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lMoneylog commands:");
        help.showHelp();
    }

    @Default
    @CommandCompletion("@players")
    @CommandPermission("crafteconomy.command.moneylog")
    public void showLogByName(CommandSender sender, String requestedPlayer) {
        Long started = System.currentTimeMillis();

        if (requests.getOrDefault(sender, null) != null) return;
        requests.put(sender, requestedPlayer);

        sender.sendMessage("§7Načítaní záznamů (může to chvíli trvat)...");

        CompletableFuture<List<EconomyLog>> completableFuture;
        try {
            UUID playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(requestedPlayer);
            completableFuture = Main.getInstance().getMySQL().getVaultAllLogsByUUID(playerUUID);
        } catch (Exception e) {
            completableFuture = Main.getInstance().getMySQL().getVaultAllLogsByNickname(requestedPlayer);
        }

        completableFuture.thenAcceptAsync(list -> {
            printTableForPlayer(sender, list, 1, started);
        });
    }

    @Default
    @CommandCompletion("@players [cislo]")
    @CommandPermission("crafteconomy.command.moneylog")
    public void showLogByNameAndPage(CommandSender sender, String requestedPlayer, int page) {
        Long started = System.currentTimeMillis();

        if (requests.getOrDefault(sender, null) != null) return;
        requests.put(sender, requestedPlayer);

        CompletableFuture<List<EconomyLog>> completableFuture;
        try {
            UUID playerUUID = Main.getInstance().getMySQL().fetchUUIDbyName(requestedPlayer);
            completableFuture = Main.getInstance().getMySQL().getVaultAllLogsByUUID(playerUUID);
        } catch (Exception e) {
            completableFuture = Main.getInstance().getMySQL().getVaultAllLogsByNickname(requestedPlayer);
        }

        completableFuture.thenAcceptAsync(list -> {
            printTableForPlayer(sender, list, page, started);
        });
    }

    private void printTableForPlayer(CommandSender player, List<EconomyLog> list, int page, Long started) {
        if (list.isEmpty()) {
            ChatInfo.DANGER.send(player, "Takový hráč neexistuje nebo neprovedl žádnou platbu nebo zde ještě nehrál!");
            return;
        }

        final Pagination<EconomyLog> pagination = new Pagination<>(list, 10);

        final String playerName = pagination.getItems().get(0).getReceiver();

        if (page <= 0 || page > pagination.getPageCount()) {
            ChatInfo.DANGER.send(player, "Taková strana neexistuje!");
            return;
        }

        player.sendMessage("");
        player.sendMessage("§e---- §aMoney Log §e-- §7Strana §c" + page + "§8/§c" + pagination.getPageCount() + " §e-- §7Nick: §c" + playerName);
        try {
            int x = 1;
            pagination.setPage(page - 1);
            for (EconomyLog log : pagination.getItems()) {
                if (log.getAction() == EconomyLog.EconomyAction.PAY_COMMAND) {
                    if (log.getReceiver().equals(playerName)) {
                        // Got money
                        player.sendMessage("§a" + (x + (page - 1) * 10) + ". §7Akce: §a" + log.getAction().getTranslated() + "§7, Částka: §e" + Main.getInstance().getFormattedNumber(log.getAmount()) + "§6" + Main.getInstance().getCurrency() + "§7, Od: §e" + log.getSender() + "§7, Datum: §e" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(log.getTime()));
                    } else {
                        // Sent money
                        player.sendMessage("§a" + (x + (page - 1) * 10) + ". §7Akce: §c" + log.getAction().getTranslated() + "§7, Částka: §e" + Main.getInstance().getFormattedNumber(log.getAmount()) + "§6" + Main.getInstance().getCurrency() + "§7, Pro: §e" + log.getReceiver() + "§7, Datum: §e" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(log.getTime()));
                    }
                }
                else
                    player.sendMessage("§a" + (x + (page - 1) * 10) + ". §7Akce: " + log.getAction().getTranslated() + "§7, Částka: §e" + Main.getInstance().getFormattedNumber(log.getAmount()) + "§6" + Main.getInstance().getCurrency() + "§7, Datum: §e" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(log.getTime()));
                x++;
            }
        } catch (Exception e) {
            Log.error("Error occured while displaying money log (page: " + page + ", target: " + playerName + ").");
            e.printStackTrace();
        }

        TextComponent message = new TextComponent();
        TextComponent prevPageMessage = new TextComponent();
        TextComponent nextPageMessage = new TextComponent();

        if (!pagination.isFirst()) {
            prevPageMessage.setText("§b<- §ePředchozí strana §8| ");
            prevPageMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/moneylog " + playerName + " " + (page - 1)));
        } else {
            prevPageMessage.setText("§7<- Předchozí strana §8| ");
        }
        if (!pagination.isLast()) {
            nextPageMessage.setText("§eDalší strana §b->");
            nextPageMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/moneylog " + playerName + " " + (page + 1)));
        } else {
            nextPageMessage.setText("§7Další strana ->");
        }

        if (pagination.isNecessary()) {
            message.addExtra(prevPageMessage);
            message.addExtra(nextPageMessage);
            player.spigot().sendMessage(message);
        }

        player.sendMessage("");

        requests.remove(player, playerName);

        Logger.info("Money log (" + playerName + ", page: " + page + ") took " + (System.currentTimeMillis() - started) + "ms.");
    }
}
