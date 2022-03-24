package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.craftcore.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.menu.ProfileSettingsGUI;
import cz.craftmania.craftlibs.utils.ChatInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("profil|profile|profilesettings")
@Description("Otevře ti tvůj profil")
public class ProfileCommand extends BaseCommand {

    @HelpCommand // Automatický generovaný subpříkaz /cc help [subcommand]
    @Syntax("[stranka]")
    @CommandCompletion("[stranka]")
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lProfile commands:"); // Nastavení textu v headu helpu
        help.showHelp(); // Zobrazí basic nápovědu
    }

    @Default
    public void showProfile(CommandSender sender) {
        if (sender instanceof Player) {
            //SmartInventory.builder().size(5, 9).title("Profile").provider(new ProfileGUI()).build().open((Player) sender);
            ChatInfo.DANGER.send((Player)sender, "Profil je dočasně deaktivovaný, pokud chceš změnit nastavení použij §f/profile settings");
        }
    }

    @Subcommand("settings|nastaveni")
    @CommandCompletion("settings|nastaveni")
    @Syntax("[settings]")
    public void showProfileSettings(CommandSender sender) {
        if (sender instanceof Player) {
            SmartInventory.builder().size(5, 9).title("Profile settings").provider(new ProfileSettingsGUI()).build().open((Player) sender);
        }
    }
}
