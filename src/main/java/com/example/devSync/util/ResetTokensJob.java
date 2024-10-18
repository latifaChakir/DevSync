package com.example.devSync.util;

import com.example.devSync.bean.UserToken;
import com.example.devSync.service.UserTokenService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class ResetTokensJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(ResetTokensJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        UserTokenService userTokenService = (UserTokenService) jobDataMap.get("userTokenService");

        System.out.println("Resetting tokens");
        try {
            List<UserToken> tokens = userTokenService.findByTokenType("Remplacement");
            for (UserToken token : tokens) {
                token.setTokenCount(2);
                token.setLastReset(LocalDate.now());
                userTokenService.update(token);
            }
            System.out.println("Les jetons de remplacement ont été réinitialisés.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la réinitialisation des jetons de remplacement");
            throw new JobExecutionException(e);
        }
    }
}


