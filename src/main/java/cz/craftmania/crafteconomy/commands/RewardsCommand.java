package cz.craftmania.crafteconomy.commands;

import cz.craftmania.craftcore.spigot.inventory.builder.SmartInventory;
import cz.craftmania.crafteconomy.rewards.RewardsGUI;
import io.github.jorelali.commandapi.api.CommandAPI;
import org.bukkit.entity.Player;

public class RewardsCommand {

    public static void register() {

        // Default: /rewards
        CommandAPI.getInstance().register("rewards", new String[] {}, null, (sender, args) -> {
            Player player = (Player) sender;
            SmartInventory.builder().size(6, 9).title("Level rewards").provider(new RewardsGUI()).build().open(player);
        });
    }
}
