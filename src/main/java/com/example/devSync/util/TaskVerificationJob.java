package com.example.devSync.util;

import com.example.devSync.service.TaskService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskVerificationJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        TaskService taskService = (TaskService) jobExecutionContext.getMergedJobDataMap().get("taskService");

        System.out.println("Starting task verification job");
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDateTime startOfDay = currentDate.atStartOfDay();

            try {
                taskService.findOverdueTasks(startOfDay);
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }

            System.out.println("Overdue tasks marked as non effectuées.");
        } catch (Exception e) {
            System.out.println("Error during task verification");
            throw new JobExecutionException(e);
        }
    }
}
