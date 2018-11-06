package cz.craftmania.crafteconomy.managers;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.ChangeActions;
import cz.craftmania.crafteconomy.events.PlayerCreateCcomunityProfileEvent;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BasicManager {

    public static HashMap<Player, CraftPlayer> players = new HashMap<>();

    public static void loadPlayerData(final Player player) {
        CraftPlayer cp = getOrRegisterPlayer(player);
        players.put(player, cp);
    }

    public static HashMap<Player, CraftPlayer> getCraftPlayersCache() {
        return players;
    }

    public CraftPlayer getCraftPlayer(Player p) {
        return players.values().stream().filter(cp -> cp.getPlayer() == p).findFirst().orElse(null);
    }

    public static CraftPlayer getOrRegisterPlayer(final Player player) {
        CraftPlayer cp = null;
        if (!Main.getInstance().getMySQL().hasData(player)) {
            if (Main.getInstance().isRegisterEnabled()) {

                // Vytvoreni
                Main.getInstance().getMySQL().createCcominutyProfile(player);

                // Event
                final PlayerCreateCcomunityProfileEvent event = new PlayerCreateCcomunityProfileEvent(player);
                Bukkit.getPluginManager().callEvent(event);

                // Player's changelog
                Main.getInstance().getMySQL().insertChangeIntoChangelog(player, "server",
                        ChangeActions.ECONOMY_REGISTER, "0", "0", "lobby");
            }
        } else {
            cp = Main.getInstance().getMySQL().getCraftPlayerFromSQL(player);
        }

        // Prevence proti NPE z SQL
        if (cp == null) {
            cp = new CraftPlayer(player);
        }
        return cp;
    }


}
