package com.example.devSync.service;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CronJobService {
    private Scheduler scheduler;

    public CronJobService(UserTokenService userTokenService) {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduleResetTokensJob(userTokenService);
            scheduleResetDeleteTokensJob(userTokenService);
            scheduleTaskVerificationJob();
            scheduleRemplaceTask();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    public Scheduler getScheduler() {
        return scheduler;
    }

    private void scheduleResetTokensJob(UserTokenService userTokenService) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("userTokenService", userTokenService);

        JobDetail jobDetail = JobBuilder.newJob(ResetTokensJob.class)
                .withIdentity("resetTokensJob", "group1")
                .setJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("resetTokensTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("Le job de réinitialisation des jetons a été planifié pour chaque jour.");
    }
    private void scheduleResetDeleteTokensJob(UserTokenService userTokenService) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("userTokenService", userTokenService);

        JobDetail jobDetail = JobBuilder.newJob(ResetDeleteTokensJob.class)
                .withIdentity("resetDeleteTokensJob", "group1")
                .setJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("resetDeleteTokensTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("Le job de réinitialisation des jetons de suppression a été planifié pour chaque jour.");
    }


    private void scheduleTaskVerificationJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(TaskVerificationJob.class)
                .withIdentity("taskVerificationJob", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("taskVerificationTrigger", "group1")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(23, 59))  // Every day at 23:59
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("Task verification job scheduled.");
    }
    private  void scheduleRemplaceTask() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ChangeTaskJob.class)
                .withIdentity("remplaceTaskJob", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("remplaceTaskTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 * * * ?"))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("Remplace task job scheduled.");
    }
}
