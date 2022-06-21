package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.craftcore.quartz.Job;
import cz.craftmania.craftcore.quartz.JobExecutionContext;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.objects.LevelType;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.Bukkit;

public class PlayerUpdateGlobalLevelTask implements Job {

    private BasicManager basicManager = new BasicManager();

    @Override
    public void execute(JobExecutionContext context) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            try {
                long actualLevel = basicManager.getCraftPlayer(p).getLevelByType(LevelType.GLOBAL_LEVEL);
                Main.getInstance().getMySQL().setEconomy(LevelType.GLOBAL_LEVEL, p, actualLevel);
                Logger.debug("Update Global Level: " + p.getName() + " hodnota: " + actualLevel);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
