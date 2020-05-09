package cz.craftmania.crafteconomy.commands;

import cz.craftmania.craftcore.spigot.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.menu.ProfileGUI;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.DynamicSuggestedStringArgument;
import org.bukkit.entity.Player;

import java.util.*;

public class ProfileCommand {

    public static void register() {

        // Default: /profil
        CommandAPI.getInstance().register("profil", new String[]{"profile"}, null, (sender, args) -> {
            sender.sendMessage("§c§l[!] §c/profile příkaz bude dostupný v pozdějších updatech! Zatím použij /profile settings pro nastavení.");
        });

        // Defualt: /profile settings
        LinkedHashMap<String, Argument> profileArgs = new LinkedHashMap<>();
        profileArgs.put("settings", new DynamicSuggestedStringArgument(() -> {
            return new String[]{"settings", "nastaveni"};
        }));
        CommandAPI.getInstance().register("profil", new String[]{"profile"}, profileArgs, (sender, args) -> {
            if (sender instanceof Player) {
                SmartInventory.builder().size(5, 9).title("Profile settings").provider(new ProfileGUI()).build().open((Player) sender);
            }
        });
    }
}
