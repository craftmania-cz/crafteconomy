package cz.craftmania.crafteconomy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import cz.craftmania.craftcore.spigot.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.menu.ProfileSettingsGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("profil|profile|profilesettings")
@Description("Otevře ti tvůj profil")
public class ProfileCommand extends BaseCommand {

    @HelpCommand // Automatický generovaný subpříkaz /cc help [subcommand]
    public void helpCommand(CommandSender sender, CommandHelp help) {
        sender.sendMessage("§e§lProfile commands:"); // Nastavení textu v headu helpu
        help.showHelp(); // Zobrazí basic nápovědu
    }

    @Default
    public void showProfile(CommandSender sender) {
        sender.sendMessage("§c§l[!] §c/profile příkaz bude dostupný v pozdějších updatech! Zatím použij /profile settings pro nastavení.");
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
