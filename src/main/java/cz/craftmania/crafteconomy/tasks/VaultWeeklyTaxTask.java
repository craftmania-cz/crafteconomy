package cz.craftmania.crafteconomy.tasks;

import cz.craftmania.craftcore.quartz.Job;
import cz.craftmania.craftcore.quartz.JobExecutionContext;
import cz.craftmania.crafteconomy.Main;
import cz.craftmania.crafteconomy.managers.WeeklyTaxManager;

public class VaultWeeklyTaxTask implements Job {

    private WeeklyTaxManager weeklyTaxManager = new WeeklyTaxManager();

    @Override
    public void execute(JobExecutionContext context) {
        long minBalance = Main.getInstance().getConfig().getLong("vault-economy.tax-payments.min-balance", 150000);
        int percentage = Main.getInstance().getConfig().getInt("vault-economy.tax-payments.percentage", 5);
        weeklyTaxManager.doPlayerPayTax(minBalance, percentage);
    }
}
