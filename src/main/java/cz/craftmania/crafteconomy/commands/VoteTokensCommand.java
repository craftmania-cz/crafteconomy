package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.api.VoteTokensAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("votetokens|vt")
@Description("Zobrazuje tvůj aktuální počet VoteTokens")
public class VoteTokensCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lVoteTokens commands:");
        help.showHelp();
    }

    @Default
    public void showVoteTokens(CommandSender sender) {
        if (sender instanceof Player)
            sender.sendMessage("§e§l[*] §eAktuálně máš " + VoteTokensAPI.getVoteTokens((Player) sender) + " VoteTokens.");
    }

    @Subcommand("add|give")
    @CommandPermission("crafteconomy.admin")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void adminGiveVoteTokens(CommandSender sender, String editedPlayer, long tokensToAdd) {
        Player p = Bukkit.getPlayer(editedPlayer);
        if (p != null) {
            VoteTokensAPI.giveVoteTokens(p, tokensToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §b" + tokensToAdd + " VT.");
        } else {
            VoteTokensAPI.giveOfflineVoteTokens(editedPlayer, tokensToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §b" + tokensToAdd + " VT.");
        }
    }

    @Subcommand("remove|take")
    @CommandPermission("crafteconomy.admin")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void adminTakeVoteTokens(CommandSender sender, String editedPlayer, long tokensToTake) {
        Player player2 = Bukkit.getPlayer(editedPlayer);
        if (player2 == Bukkit.getPlayer(editedPlayer)) {
            VoteTokensAPI.takeOfflineVoteTOkens(editedPlayer, tokensToTake);
            sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + editedPlayer + " §7- §d" + tokensToTake + " VT.");
            return;
        }
        if ((manager.getCraftPlayer(player2).getVoteTokens() - tokensToTake) < 0) {
            sender.sendMessage("§c§l[!] §cHráč nemá dostatek VoteTokens! Má k dispozici: " + manager.getCraftPlayer(player2).getVoteTokens());
            return;
        }
        VoteTokensAPI.takeVoteTokens(player2, tokensToTake);
        sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + editedPlayer + " §7- §d" + tokensToTake + " VT.");
    }
}
