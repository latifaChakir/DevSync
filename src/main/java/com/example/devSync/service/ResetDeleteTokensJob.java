package com.example.devSync.service;

import com.example.devSync.bean.UserToken;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class ResetDeleteTokensJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(ResetDeleteTokensJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        UserTokenService userTokenService = (UserTokenService) jobDataMap.get("userTokenService");

        System.out.println("Resetting delete tokens");
        try {
            List<UserToken> tokens = userTokenService.findByTokenType("Suppression");
            for (UserToken token : tokens) {
                token.setTokenCount(1);
                token.setLastReset(LocalDate.now());
                userTokenService.update(token);
            }
            System.out.println("Les jetons de suppression ont été réinitialisés.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la réinitialisation des jetons de suppression");
            throw new JobExecutionException(e);
        }
    }
}
