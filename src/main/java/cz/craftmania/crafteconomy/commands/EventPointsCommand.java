package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import cz.craftmania.crafteconomy.api.EconomyAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("eventpoints|ep")
@Description("Zobrazuje tvůj aktuální počet EventPointů")
public class EventPointsCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lEventPoints commands:");
        help.showHelp();
    }

    @Default
    public void showEventPoints(CommandSender sender) {
        if (sender instanceof Player)
            sender.sendMessage("§e§l[*] §eAktuálně máš " + EconomyAPI.EVENTPOINTS.get((Player) sender) + " EventPoints.");
    }
}
