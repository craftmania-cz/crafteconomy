package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private Main main;

    public PlayerQuitListener(Main main) {
        this.main = main;
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();

        BasicManager.getCraftPlayersCache().remove(player);
    }
}
