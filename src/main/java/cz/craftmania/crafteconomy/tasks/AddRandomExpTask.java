package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.api.LevelAPI;
import cz.craftmania.crafteconomy.managers.BasicManager;
import org.bukkit.Bukkit;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class AddRandomExpTask implements Job {

    private final BasicManager basicManager = new BasicManager();

    @Override
    public void execute(JobExecutionContext context) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (basicManager.getCraftPlayer(player) != null) {
                if (!basicManager.getCraftPlayer(player).isAfk()) {
                    if (!Main.getInstance().getDisabledExperienceInWorlds().contains(basicManager.getCraftPlayer(player).getPlayer().getWorld().getName())) {
                        LevelAPI.addExp(player, basicManager.getExperienceByServer(), randomRangeInt(Main.getInstance().getMinExp(), Main.getInstance().getMaxExp()));
                    }
                }
            }
        });
    }

    private static int randomRangeInt(int min, int max) {
        return (int) (Math.random() < 0.5 ? ((1 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
    }
}