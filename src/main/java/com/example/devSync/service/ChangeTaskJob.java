package com.example.devSync.service;

import com.example.devSync.bean.TaskHistory;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.bean.UserToken;
import com.example.devSync.dao.TaskHistoryDao;
import com.example.devSync.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
public class ChangeTaskJob implements Job {

    private TaskHistoryDao taskHistoryDao;
    private UserTokenService userTokenService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            List<TaskHistory> taskHistories = taskHistoryDao.findAll();
            for (TaskHistory taskHistory : taskHistories) {
                LocalDateTime modificationDate = taskHistory.getModificationDate();
                LocalDateTime now = LocalDateTime.now();

                if (ChronoUnit.HOURS.between(modificationDate, now) > 12 && !taskHistory.getIsApproved()) {
                    Utilisateur user = taskHistory.getUser();
                    UserToken existingUserToken = userTokenService.findByUserAndTokenType(user,"Remplacement");

                    if (existingUserToken != null) {
                        existingUserToken.setTokenCount(existingUserToken.getTokenCount() + 2);
                        existingUserToken.setLastReset(LocalDate.now().plusDays(1));
                        userTokenService.update(existingUserToken);

                        log.info("Doubled tokens for user: {}", user.getNomUtilisateur());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error executing ChangeTaskJob", e);
            throw new JobExecutionException(e);
        }
    }
}