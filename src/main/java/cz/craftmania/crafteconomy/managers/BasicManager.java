package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BasicManager {

    public static HashMap<Player, CraftPlayer> list = new HashMap<>();

    public void loadPlayerData(Player player) {
        if(!Main.getInstance().getMySQL().hasData(player)){
            // Register
        } else {
            // Load
        }
    }
}
