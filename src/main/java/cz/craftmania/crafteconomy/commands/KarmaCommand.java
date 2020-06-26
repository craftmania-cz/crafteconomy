package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.api.KarmaAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("karma")
@Description("Zobrazuje tvůj aktuální počet Karmy")
public class KarmaCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lKarma commands:");
        help.showHelp();
    }

    @Default
    public void showCraftKarma(CommandSender sender) {
        if (sender instanceof Player)
            sender.sendMessage("§e§l[*] §eAktuálně máš " + KarmaAPI.getKarma((Player) sender) + " Karma/y.");
    }

    @Subcommand("add|give")
    @CommandPermission("crafteconomy.admin")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void adminGiveVoteKarma(CommandSender sender, String editedPlayer, long karmaToAdd) {
        Player p = Bukkit.getPlayer(editedPlayer);
        if (p != null) {
            KarmaAPI.giveKarma(p, karmaToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §d" + karmaToAdd + " Karmy.");
        } else {
            KarmaAPI.giveOfflineKarma(editedPlayer, karmaToAdd);
            sender.sendMessage("§e§l[*] §ePřidal jsi hráči §f" + editedPlayer + " §7- §d" + karmaToAdd + " Karmy.");
        }
    }

    @Subcommand("remove|take")
    @CommandPermission("crafteconomy.admin")
    @CommandCompletion("@players [pocet]")
    @Syntax("[nick] [pocet]")
    public void adminTakeVoteKarma(CommandSender sender, String editedPlayer, long karmaToTake) {
        Player player2 = Bukkit.getPlayer(editedPlayer);
        if (player2 == null) { //TODO: Chybi offline kontrola, lze jit do minusu
            KarmaAPI.takeOfflineKarma(editedPlayer, karmaToTake);
            sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + editedPlayer + " §7- §d" + karmaToTake + " Karmy.");
            return;
        }
        if ((manager.getCraftPlayer(player2).getKarma() - karmaToTake) < 0) {
            sender.sendMessage("§c§l[!] §cHráč nemá dostatek Karmy! Má k dispozici: " + manager.getCraftPlayer(player2).getKarma());
            return;
        }
        KarmaAPI.takeKarma(player2, karmaToTake);
        sender.sendMessage("§e§l[*] §eOdebral jsi hráči §f" + editedPlayer + " §7- §d" + karmaToTake + " Karmy.");
    }
}
