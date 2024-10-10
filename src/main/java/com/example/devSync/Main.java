package com.example.devSync;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.dao.UtilisateurDao;
import com.example.devSync.dao.impl.TaskDaoImpl;
import com.example.devSync.dao.impl.UtilisateurDaoImpl;
import com.example.devSync.service.*;

public class Main {
    public static void main(String[] args) {
//        TaskHistoryService taskHistoryService = new TaskHistoryService();
//        Long taskId = 9L;
//        TaskService taskService = new TaskService();
//        Task task = taskService.getTaskById(taskId);
//        Long userId = 13L;
//        UtilisateurService utilisateurService = new UtilisateurService();
//        Utilisateur utilisateur = utilisateurService.getUtilisateur(userId).orElse(null);
//        taskHistoryService.AskToRemplace(task,utilisateur);
        System.out.println("ok");
        new CronJobService();


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
