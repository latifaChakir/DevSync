package com.example.devSync.service;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CronJobService {
    private Scheduler scheduler;
    public CronJobService() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduleResetTokensJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    private void scheduleResetTokensJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ResetTokensJob.class)
                .withIdentity("resetTokensJob", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("resetTokensTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")) // Exécute toutes les 2 secondes
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

}
