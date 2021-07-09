package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.menu.RewardsGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("rewards|odmeny")
@Description("Zobrazí tvoje odměny na tvém momentálním serveru")
public class RewardsCommand extends BaseCommand {

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lRewards commands:");
        help.showHelp();
    }

    @Default
    public void showRewards(CommandSender sender) {
        if (sender instanceof Player)
            SmartInventory.builder().size(6, 9).title("Level rewards").provider(new RewardsGUI()).build().open((Player) sender);
    }
}
