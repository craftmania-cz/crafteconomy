package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.BasicManager;
import cz.craftmania.crafteconomy.utils.Logger;
import org.bukkit.Bukkit;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EconomySaveTask implements Job {

    private final BasicManager bm = new BasicManager();

    @Override
    public void execute(JobExecutionContext context) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (bm.getCraftPlayer(player) != null) {
                double balance = bm.getCraftPlayer(player).getMoney();
                Main.getInstance().getMySQL().setVaultEcoBalance(player.getName(), balance);
            }
        });
        Logger.info("Economy update v MySQL dokončen.");
    }
}
