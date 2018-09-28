package cz.craftmania.crafteconomy.utils;

import cz.craftmania.crafteconomy.Main;
import org.bukkit.Bukkit;

public class AsyncUtils {

    private final Main plugin;

    public AsyncUtils(Main plugin) {
        this.plugin = plugin;
    }

    public void runAsync(Runnable rn) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, rn);
    }

    public void runAsync(Runnable rn, Long timer) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, rn, 0, timer);
    }

    public void runSync(Runnable rn) {
        Bukkit.getScheduler().runTask(plugin, rn);
    }


}
