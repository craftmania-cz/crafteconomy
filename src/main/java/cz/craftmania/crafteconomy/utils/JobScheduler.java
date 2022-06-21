package cz.craftmania.crafteconomy.utils;

import cz.craftmania.craftcore.quartz.*;

public class JobScheduler {

    private final Scheduler scheduler;

    public JobScheduler(Scheduler scheduler) throws SchedulerException {
        this.scheduler = scheduler;
    }

    public void scheduleWithBuilder(Class<? extends Job> javaClass, String jobKey, CronScheduleBuilder cron) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(javaClass)
                .withIdentity(jobKey)
                .build();
        CronTrigger jobTrigger = TriggerBuilder.newTrigger()
                .forJob(jobKey)
                .withSchedule(cron)
                .build();
        scheduler.scheduleJob(job, jobTrigger);
    }

    public void scheduleWithBuilder(Class<? extends Job> javaClass, String jobKey, SimpleScheduleBuilder scheduleBuilder) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(javaClass)
                .withIdentity(jobKey)
                .build();
        SimpleTrigger jobTrigger = TriggerBuilder.newTrigger()
                .forJob(jobKey).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(job, jobTrigger);
    }

    public void scheduleWithBuilder(Class<? extends Job> javaClass, String jobKey, DailyTimeIntervalScheduleBuilder scheduleBuilder) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(javaClass)
                .withIdentity(jobKey)
                .build();
        DailyTimeIntervalTrigger jobTrigger = TriggerBuilder.newTrigger()
                .forJob(jobKey).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(job, jobTrigger);
    }
}
