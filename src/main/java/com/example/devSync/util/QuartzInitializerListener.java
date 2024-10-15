package com.example.devSync.util;
import com.example.devSync.service.CronJobService;
import com.example.devSync.service.TaskService;
import com.example.devSync.service.UserTokenService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class QuartzInitializerListener implements ServletContextListener {

    private CronJobService cronJobService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing Quartz...");
        UserTokenService userTokenService = new UserTokenService();
        TaskService taskService = new TaskService();
        cronJobService = new CronJobService(userTokenService, taskService);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down Quartz...");
        try {
            if (cronJobService != null && cronJobService.getScheduler() != null) {
                cronJobService.getScheduler().shutdown(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
