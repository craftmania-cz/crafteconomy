package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.LevelType;
import org.bukkit.Bukkit;

public class PlayerUpdateGlobalLevelTask implements Runnable {

    private BasicManager bm = new BasicManager();

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            try {
                long actualLevel = bm.getCraftPlayer(p).getLevelByType(LevelType.GLOBAL_LEVEL);
                Main.getInstance().getMySQL().setEconomy(LevelType.GLOBAL_LEVEL, p, actualLevel);
            } catch (Exception exception) {
                Main.getInstance().sendSentryException(exception);
            }
        });
    }
}
