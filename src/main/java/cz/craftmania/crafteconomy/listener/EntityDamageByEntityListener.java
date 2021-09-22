package cz.craftmania.crafteconomy.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class EntityDamageByEntityListener implements Listener {

    // Blokace, aby firework při levelupu nedával damage
    @EventHandler
    public void onLevelUpFirework(EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata("nodamage")) {
            e.setCancelled(true);
        }
    }
}
