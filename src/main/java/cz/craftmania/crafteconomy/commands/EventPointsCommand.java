package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("eventpoints|ep")
@Description("Zobrazuje tvůj aktuální počet EventPointů")
public class EventPointsCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    @Syntax("[stranka]")
    @CommandCompletion("[stranka]")
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lEventPoints commands:");
        help.showHelp();
    }

    @Default
    public void showEventPoints(CommandSender sender) {
        if (sender instanceof Player)
            sender.sendMessage("§e§l[*] §eAktuálně máš " + EconomyAPI.EVENT_POINTS.get((Player) sender) + " EventPoints.");
    }
}
