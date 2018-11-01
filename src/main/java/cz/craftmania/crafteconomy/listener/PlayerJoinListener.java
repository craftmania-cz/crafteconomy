package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    private Main main;

    public PlayerJoinListener(Main main) {
        this.main = main;
    }

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        // Registrace do Ccominuty prvni
        if (main.isRegisterEnabled()) {
            Main.getInstance().getMySQL().createCcominutyProfile(player);
        }

        // Zakladni nacteni dat do cache
        BasicManager.loadPlayerData(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();

        //TODO: Ukladani
    }
}
