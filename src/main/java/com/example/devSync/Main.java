package com.example.devSync;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.dao.UtilisateurDao;
import com.example.devSync.dao.impl.TaskDaoImpl;
import com.example.devSync.dao.impl.UtilisateurDaoImpl;
import com.example.devSync.service.TaskService;
import com.example.devSync.service.UtilisateurService;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        Long assignedTo= 13L;
        taskService.getTasksByAssigned(13L);

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
