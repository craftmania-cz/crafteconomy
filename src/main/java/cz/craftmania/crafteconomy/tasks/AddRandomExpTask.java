package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.LevelAPI;
import org.bukkit.Bukkit;

public class AddRandomExpTask implements Runnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> LevelAPI.addExp(p, randomRangeInt(Main.getInstance().getMinExp(), Main.getInstance().getMaxExp())));
    }

    private static int randomRangeInt(int min, int max) {
        return (int) (Math.random() < 0.5 ? ((1 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
    }
}