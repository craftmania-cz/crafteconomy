package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.CleanUpManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class VaultCleanTask implements Job {

    private final CleanUpManager cleanManager = new CleanUpManager();

    @Override
    public void execute(JobExecutionContext context) {
        int cleanUpDays = Main.getInstance().getConfig().getInt("vault-economy.cleanup.days", 150);
        cleanManager.cleanUpDatabase(cleanUpDays);
    }
}
