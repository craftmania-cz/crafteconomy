package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.menu.KalendarGUI;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.ServerType;
import cz.craftmania.craftlibs.utils.ChatInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("kalendar|calendar")
@Description("Vánoční speciální kalendář")
public class KalendarCommand extends BaseCommand {

    final BasicManager basicManager = new BasicManager();

    @HelpCommand
    @Syntax("[stranka]")
    @CommandCompletion("[stranka]")
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lKalendar příkazy:");
        help.showHelp();
    }

    @Default
    public void showLevel(CommandSender sender) {
        if (sender instanceof Player player) {
            if (basicManager.getCraftPlayer(player).getLevelByType(LevelType.GLOBAL_LEVEL) < 3) {
                ChatInfo.DANGER.send(player, "Na otevření kalendáře potřebuješ mít Globalní level 3 a více §f/level §c.");
                return;
            }
            if (Main.getServerType() == ServerType.CREATIVE) {
                ChatInfo.DANGER.send(player, "Upozorňujeme, že nábytek používaný v Creative módu se může bugovat. Používejte jej obezřetně!");
            }
            SmartInventory.builder().size(5, 9).title(":offset_-18::calendar_menu:").provider(new KalendarGUI()).build().open(player);
        }
    }

}
