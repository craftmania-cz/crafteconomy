package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandAlias("crafttokens|ct")
@Description("Zobrazuje tvůj aktuální počet CraftTokens")
public class CraftTokensCommand extends BaseCommand {

    private static final BasicManager manager = new BasicManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lCraftTokens commands:");
        help.showHelp();
    }

    @Default
    public void showCraftTokens(CommandSender sender) {
        if (sender instanceof Player)
            sender.sendMessage("§e§l[*] §eAktuálně máš " + EconomyAPI.CRAFTTOKENS.get((Player) sender) + " CraftTokens.");
    }

    @Subcommand("add|give")
    @CommandPermission("crafteconomy.admin")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void adminGiveVoteTokens(CommandSender sender, String editedPlayer, long tokensToAdd) {
        Player p = Bukkit.getPlayer(editedPlayer);
        if (p != null) {
            EconomyAPI.CRAFTTOKENS.give(p, tokensToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §d" + tokensToAdd + " CT.");
        } else {
            EconomyAPI.CRAFTTOKENS.giveOffline(editedPlayer, tokensToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §d" + tokensToAdd + " CT.");
        }
    }

    @Subcommand("remove|take")
    @CommandPermission("crafteconomy.admin")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void adminTakeVoteTokens(CommandSender sender, String editedPlayer, long tokensToTake) {
        Player player2 = Bukkit.getPlayer(editedPlayer);
        if (player2 == null) { //TODO: Chybi offline kontrola, lze jit do minusu
            EconomyAPI.CRAFTTOKENS.takeOffline(editedPlayer, tokensToTake);
            sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + editedPlayer + " §7- §d" + tokensToTake + " CT.");
            return;
        }
        if ((manager.getCraftPlayer(player2).getTokens() - tokensToTake) < 0) {
            sender.sendMessage("§c§l[!] §cHráč nemá dostatek CraftTokens! Má k dispozici: " + manager.getCraftPlayer(player2).getTokens());
            return;
        }
        EconomyAPI.CRAFTTOKENS.take(player2, tokensToTake);
        sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + editedPlayer + " §7- §d" + tokensToTake + " CT.");
    }
}
