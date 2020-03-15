package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;

public class AddRandomExpTask implements Runnable {

    private BasicManager bm = new BasicManager();

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (bm.getCraftPlayer(p) != null) {
                if (!bm.getCraftPlayer(p).isAfk()) {
                    LevelAPI.addExp(p, bm.getExperienceByServer(), randomRangeInt(Main.getInstance().getMinExp(), Main.getInstance().getMaxExp()));
                }
            }
        });
    }

    private static int randomRangeInt(int min, int max) {
        return (int) (Math.random() < 0.5 ? ((1 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
    }
}