package cz.craftmania.crafteconomy.listener;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.events.economy.AsyncPlayerGainExpEvent;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.CraftPlayer;
import cz.craftmania.crafteconomy.utils.LevelUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerExpGainListener implements Listener {

    private BasicManager basicManager = new BasicManager();

    @EventHandler
    public void onExpGain(final AsyncPlayerGainExpEvent event) {
        final Player player = event.getPlayer();
        final CraftPlayer craftPlayer = basicManager.getCraftPlayer(player);
        long amount = event.getAmount();

        if (craftPlayer.isAfk()) { // Pokud je AFK nedostane žádný expy!
            return;
        }

        //TODO: Volitelny pres Ccomunity
        Main.getAsync().runAsync(() -> {
            craftPlayer.getPlayer().sendMessage("§6+ " + amount + "XP");
            if (craftPlayer.getExperienceByType(basicManager.getExperienceByServer())
                    >= LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(basicManager.getLevelByServer()))) {
                double remainingExp = craftPlayer.getExperienceByType(basicManager.getExperienceByServer()) - LevelUtils.getExpFromLevelToNext(craftPlayer.getLevelByType(basicManager.getLevelByServer()));
                LevelAPI.addLevel(craftPlayer.getPlayer(), basicManager.getLevelByServer(), 1);
                if (remainingExp <= 0) {
                    LevelAPI.resetExperienceToZero(craftPlayer.getPlayer());
                }
                else {
                    LevelAPI.resetExperienceToZero(craftPlayer.getPlayer());
                    LevelAPI.addExp(craftPlayer.getPlayer(), basicManager.getExperienceByServer(), ((int) remainingExp));
                }
            }
        });
    }
}
