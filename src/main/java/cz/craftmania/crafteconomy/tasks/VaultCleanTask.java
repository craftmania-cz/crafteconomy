package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.craftcore.quartz.Job;
import cz.craftmania.craftcore.quartz.JobExecutionContext;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.CleanUpManager;

public class VaultCleanTask implements Job {

    private final CleanUpManager cleanManager = new CleanUpManager();

    @Override
    public void execute(JobExecutionContext context) {
        int cleanUpDays = Main.getInstance().getConfig().getInt("vault-economy.cleanup.days", 150);
        cleanManager.cleanUpDatabase(cleanUpDays);
    }
}
