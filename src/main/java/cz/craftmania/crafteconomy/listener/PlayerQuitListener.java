package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.ServerType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private Main main;
    private final BasicManager bm = new BasicManager();

    public PlayerQuitListener(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (Main.getInstance().isVaultEconomyEnabled()) { // Save peněz po odpojení
            CraftPlayer craftPlayer = bm.getCraftPlayer(player);
            if (craftPlayer != null) {
                Main.getInstance().getMySQL().setVaultEcoBalance(player.getName(), craftPlayer.getMoney());
            }
        }

        BasicManager.getCraftPlayersCache().remove(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKick(PlayerKickEvent event) {
        final Player player = event.getPlayer();

        if (Main.getInstance().isVaultEconomyEnabled()) { // Save peněz po odpojení
            CraftPlayer craftPlayer = bm.getCraftPlayer(player);
            if (craftPlayer != null) {
                Main.getInstance().getMySQL().setVaultEcoBalance(player.getName(), craftPlayer.getMoney());
            }
        }

        BasicManager.getCraftPlayersCache().remove(player);
    }
}
