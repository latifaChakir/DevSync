package com.example.devSync.service;

import com.example.devSync.bean.UserToken;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class ResetTokensJob implements Job {
    private final UserTokenService userTokenService;

    public ResetTokensJob(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            List<UserToken> tokens = userTokenService.findByTokenType("Remplacement");
            for (UserToken token : tokens) {
                token.setTokenCount(2);
                token.setLastReset(LocalDate.now());
                userTokenService.save(token);
            }
            System.out.println("Les jetons de remplacement ont été réinitialisés.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la réinitialisation des jetons de remplacement");
            throw new JobExecutionException(e);
        }
    }
}