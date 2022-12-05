package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.menu.KalendarGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("kalendar|calendar")
@Description("Vánoční speciální kalendář")
public class KalendarCommand extends BaseCommand {


    @HelpCommand
    @Syntax("[stranka]")
    @CommandCompletion("[stranka]")
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lKalendar příkazy:");
        help.showHelp();
    }

    @Default
    public void showLevel(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SmartInventory.builder().size(5, 9).title(":offset_-18::calendar_menu:").provider(new KalendarGUI()).build().open(player);
        }
    }

}
