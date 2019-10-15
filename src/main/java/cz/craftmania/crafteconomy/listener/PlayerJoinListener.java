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
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 4 && !player.hasPermission("rc.bypass.confiscate.items.material.COD_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 4);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 5 && !player.hasPermission("rc.bypass.confiscate.items.material.COW_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 5);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 7 && !player.hasPermission("rc.bypass.confiscate.items.material.PIG_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 7);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 8 && !player.hasPermission("rc.bypass.confiscate.items.material.TRADER_LLAMA_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 8);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 9 && !player.hasPermission("rc.bypass.confiscate.items.material.FOX_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 9);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 11 && !player.hasPermission("rc.bypass.confiscate.items.material.PANDA_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 11);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 12 && !player.hasPermission("rc.bypass.confiscate.items.material.POLAR_BEAR_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 12);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 13 && !player.hasPermission("rc.bypass.confiscate.items.material.WOLF_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 13);
            }
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) >= 14 && !player.hasPermission("rc.bypass.confiscate.items.material.PARROT_SPAWN_EGG")) {
                bm.givePlayerLevelReward(player, 14);
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
