package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("seasonpoints|sp")
@Description("Zobrazuje tvůj aktuální počet SeasonPoints")
public class SeasonPointsCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    @Syntax("[stranka]")
    @CommandCompletion("[stranka]")
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lSeasonPoints commands:");
        help.showHelp();
    }

    @Default
    public void showSeasonPoints(CommandSender sender) {
        if (sender instanceof Player)
            sender.sendMessage("§e§l[*] §eAktuálně máš " + EconomyAPI.SEASON_POINTS.get((Player) sender) + " SeasonPoints.");
    }

    @Subcommand("add|give")
    @CommandPermission("crafteconomy.admin")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void adminSeasonPointsTokens(CommandSender sender, String editedPlayer, long tokensToAdd) {
        Player p = Bukkit.getPlayer(editedPlayer);
        if (p != null) {
            EconomyAPI.SEASON_POINTS.give(p, tokensToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §b" + tokensToAdd + " SP.");
        } else {
            EconomyAPI.SEASON_POINTS.give(editedPlayer, tokensToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §b" + tokensToAdd + " SP.");
        }
    }
}
