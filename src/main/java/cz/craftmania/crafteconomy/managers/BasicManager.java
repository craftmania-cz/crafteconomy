package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BasicManager {

    public static HashMap<Player, CraftPlayer> players = new HashMap<>();

    public static void loadPlayerData(final Player player) {

        CraftPlayer cp = null;

        if (!Main.getInstance().getMySQL().hasData(player)) {
            // Register
        } else {
            cp = Main.getInstance().getMySQL().getCraftPlayerFromSQL(player);
        }

        // Prevence proti NPE z SQL
        if (cp == null) {
            cp = new CraftPlayer(player);
        }

        players.put(player, cp);
    }

    public static HashMap<Player, CraftPlayer> getCraftPlayersCache() {
        return players;
    }

    public CraftPlayer getCraftPlayer(Player p) {
        for (CraftPlayer cp : players.values()) {
            if (cp.getPlayer() == p) {
                return cp;
            }
        }
        return null;
    }


}
