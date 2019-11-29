package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.AchievementPointsAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.Bukkit;
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

        // Zakladni nacteni dat do cache a vytvoření objektu
        CraftPlayer craftPlayer = BasicManager.loadPlayerData(player);

        // Ignorování unknown serverů
        if (Main.getServerType() == ServerType.UNKNOWN) {
            return;
        }

        if (Main.getServerType() == ServerType.VANILLA) {
            if (craftPlayer.getLevelByType(bm.getLevelByServer()) < 22 && player.hasPermission("lands.lands.3")) {
                bm.removePlayerReward(player, 22);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();

        BasicManager.getCraftPlayersCache().remove(player);
    }
}
