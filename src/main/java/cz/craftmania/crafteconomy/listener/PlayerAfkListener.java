package cz.craftmania.crafteconomy.listener;

import com.Zrips.CMI.events.CMIAfkEnterEvent;
import com.Zrips.CMI.events.CMIAfkKickEvent;
import com.Zrips.CMI.events.CMIAfkLeaveEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerAfkListener implements Listener {

    private BasicManager manager = new BasicManager();

    @EventHandler
    public void onAfkEnter(CMIAfkEnterEvent event) {
        Player player = event.getPlayer();
        manager.getCraftPlayer(player).setAfk(true);
    }

    @EventHandler
    public void onAfkLeave(CMIAfkLeaveEvent event) {
        Player player = event.getPlayer();
        try {
            manager.getCraftPlayer(player).setAfk(false); // Když je CMI rychlejší jak CraftEconomy
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void onAfkKick(CMIAfkKickEvent event) {
        Player player = event.getPlayer();
        try {
            manager.getCraftPlayer(player).setAfk(false);
        } catch (Exception ignored) {}
    }
}
