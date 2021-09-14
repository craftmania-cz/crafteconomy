package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.menu.VotePassGUI;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("votepass")
@Description("Zobraz si postuv v aktuální VotePassu")
public class VotePassCommand extends BaseCommand {

    private static BasicManager manager = new BasicManager();

    @HelpCommand
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lVotePass commands:");
        help.showHelp();
    }

    @Default
    public void showVotePass(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Main.getServerType() == ServerType.LOBBY) {
                sender.sendMessage("§c§l[!] §cVotePass nelze používat na lobby. Navštiv nějaký server k získání výhod.");
                return;
            }
            SmartInventory.builder().size(3, 9).title("VotePass").provider(new VotePassGUI()).build().open(player);
        } else {
            sender.sendMessage("§c§lNelze použít tento příkaz jako konzole.");
        }
    }
}
