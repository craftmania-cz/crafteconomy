package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.migration.CraftCoins2_migration;
import cz.craftmania.crafteconomy.migration.CraftMoney_migrations;
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

        // Zakladni nacteni dat do cache
        BasicManager.loadPlayerData(player);

        // Migrace CraftCoinsV2 & CraftMoney
        if (Main.isMigrations_enabled()) {
            CraftCoins2_migration c2m = new CraftCoins2_migration(player);
            c2m.migrate();

            CraftMoney_migrations c1m = new CraftMoney_migrations(player);
            c1m.migrate();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();

        //TODO: Ukladani
    }
}
