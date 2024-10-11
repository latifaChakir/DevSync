package com.example.devSync;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.UserToken;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.dao.UtilisateurDao;
import com.example.devSync.dao.impl.TaskDaoImpl;
import com.example.devSync.dao.impl.UtilisateurDaoImpl;
import com.example.devSync.service.*;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final Logger logger = Logger.getLogger(Main.class);

//        TaskHistoryService taskHistoryService = new TaskHistoryService();
//        Long taskId = 9L;
//        TaskService taskService = new TaskService();
//        Task task = taskService.getTaskById(taskId);
//        Long userId = 13L;
//        UtilisateurService utilisateurService = new UtilisateurService();
//        Utilisateur utilisateur = utilisateurService.getUtilisateur(userId).orElse(null);
//        taskHistoryService.AskToRemplace(task,utilisateur);
        PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
        ConsoleAppender consoleAppender = new ConsoleAppender(layout);
        Logger.getRootLogger().addAppender(consoleAppender);

        logger.warn("Ceci est un message de log de niveau WARN");
        logger.error("Ceci est un message de log de niveau ERROR");
        UserTokenService userTokenService = new UserTokenService();
        CronJobService cronJobService=new CronJobService(userTokenService);


//        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl();
//        UtilisateurService utilisateurService = new UtilisateurService();
//
//        String username = "latifaChakir";
//        String password = "1234";
//
//        Utilisateur utilisateur = utilisateurService.login(username, password);
//        System.out.println(utilisateur);
//
//        if (utilisateur != null) {
//            System.out.println("Login réussi pour l'utilisateur : " + utilisateur.getNomUtilisateur());
//        } else {
//            System.out.println("Échec de la connexion. Nom d'utilisateur ou mot de passe incorrect.");
//        }

    }
}
