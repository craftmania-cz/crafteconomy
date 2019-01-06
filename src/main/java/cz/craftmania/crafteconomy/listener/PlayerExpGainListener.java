package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.events.PlayerExpGainEvent;
import cz.craftmania.crafteconomy.events.PlayerLevelGainEvent;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.LevelUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerExpGainListener implements Listener {

    @EventHandler
    public void onExpGain(PlayerExpGainEvent e) {
        CraftPlayer p = e.getPlayer();
        long amount = e.getAmount();

        p.getPlayer().sendMessage("§a+ §7" + amount + "XP");
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F, 1.0F);
        if (p.getExperience() >= LevelUtils.getExpFromLevelToNext(p.getLevel())) {
            LevelAPI.addLevel(p.getPlayer(), 1);
        }
    }
}
