package cz.craftmania.crafteconomy.commands;

import cz.craftmania.crafteconomy.api.EventPointsAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import io.github.jorelali.commandapi.api.CommandAPI;
import org.bukkit.entity.Player;

public class EventPointsCommand {

    private static BasicManager manager = new BasicManager();

    public static void register() {

        // Default: /eventpoints
        CommandAPI.getInstance().register("eventpoints", new String[] {"ep"}, null, (sender, args) -> {
            Player p = (Player) sender;
            long points = EventPointsAPI.getEventPoints(p);
            p.sendMessage("§e§l[*] §eAktuálně máš " + points + " EventPoints.");
        });
    }
}
