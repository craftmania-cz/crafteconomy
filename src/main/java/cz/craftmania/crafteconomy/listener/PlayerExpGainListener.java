package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.events.PlayerExpGainEvent;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.LevelUtils;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerExpGainListener implements Listener {

    @EventHandler
    public void onExpGain(PlayerExpGainEvent e) {
        final CraftPlayer p = e.getPlayer();
        long amount = e.getAmount();

        //TODO: Volitelny pres Ccomunity
        p.getPlayer().sendMessage("§6+ " + amount + "XP");
        if (p.getExperience() >= LevelUtils.getExpFromLevelToNext(p.getLevel())) {
            LevelAPI.addLevel(p.getPlayer(), 1);
        }
    }
}
