package com.example.devSync.service;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.enums.Status;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskVerificationJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        TaskService taskService = (TaskService) jobExecutionContext.getMergedJobDataMap().get("taskService");

        System.out.println("Starting task verification job");
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDateTime startOfDay = currentDate.atStartOfDay();  // Ensure this is LocalDateTime

            try {
                taskService.findOverdueTasks(startOfDay);  // Pass LocalDateTime
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
