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

            // Pokud je v SQL spatny UUID, je nutny ho opravit a nacist
            if (Main.getInstance().getMySQL().hasDataByName(player.getName())) {
                Main.getInstance().getMySQL().updateCcominutyForceUUID(player);
                System.out.println("[CraftEconomy] Update UUID v SQL pro: " + player.getName());
                return Main.getInstance().getMySQL().getCraftPlayerFromSQL(player);
            }

            // Pokud hrac neni vubec v SQL, tak se provede register
            if (Main.getInstance().isRegisterEnabled()) {

                // Vytvoreni
                Main.getInstance().getMySQL().createCcominutyProfile(player);

                // Event
                final PlayerCreateCcomunityProfileEvent event = new PlayerCreateCcomunityProfileEvent(player);
                Bukkit.getPluginManager().callEvent(event);
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
