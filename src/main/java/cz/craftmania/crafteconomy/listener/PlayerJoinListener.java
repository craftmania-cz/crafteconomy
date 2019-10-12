package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    private Main main;
    private BasicManager bm = new BasicManager();

    public PlayerJoinListener(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        // Ignorování unknown serverů
        if (Main.getServerType() == ServerType.UNKNOWN) {
            return;
        }

        // Zakladni nacteni dat do cache a vytvoření objektu
        CraftPlayer craftPlayer = BasicManager.loadPlayerData(player);

        if (Main.getServerType() == ServerType.CREATIVE) {
            // Fix pro ty, co mají vyšší lvl jak 4 ale nemají reward za lvl 4
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 4 && !player.hasPermission("rc.bypass.disable.interacting.in-hand.COD_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 4);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 7 && !player.hasPermission("rc.bypass.disable.interacting.in-hand.PIG_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 7);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 9 && !player.hasPermission("rc.bypass.disable.interacting.in-hand.FOX_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 9);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();

        // Ignorování unknown serverů
        if (Main.getServerType() == ServerType.UNKNOWN) {
            return;
        }

        BasicManager.getCraftPlayersCache().remove(player);
    }
}
