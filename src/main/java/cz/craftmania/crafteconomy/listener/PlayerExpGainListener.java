package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.events.PlayerExpGainEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.LevelUtils;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerExpGainListener implements Listener {

    private BasicManager bm = new BasicManager();

    @EventHandler
    public void onExpGain(final PlayerExpGainEvent e) {
        final CraftPlayer p = e.getPlayer();
        long amount = e.getAmount();

        //TODO: Volitelny pres Ccomunity
        Main.getAsync().runAsync(() -> {
            p.getPlayer().sendMessage("§6+ " + amount + "XP");
            if (p.getExperienceByType(bm.getExperienceByServer())
                    >= LevelUtils.getExpFromLevelToNext(p.getLevelByType(bm.getLevelByServer()))) {
                LevelAPI.addLevel(p.getPlayer(), bm.getLevelByServer(), 1);
                p.setExperienceByType(bm.getExperienceByServer(), 0);
            }
        });
    }
}
