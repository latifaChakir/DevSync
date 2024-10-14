package com.example.devSync;
import com.example.devSync.bean.Task;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.bean.enums.Status;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.dao.UtilisateurDao;
import com.example.devSync.dao.impl.TaskDaoImpl;
import com.example.devSync.dao.impl.UtilisateurDaoImpl;
import com.example.devSync.service.TaskService;
import com.example.devSync.service.UserTokenService;
import com.example.devSync.service.UtilisateurService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
//        TaskDao taskDao = new TaskDaoImpl();
//
//        LocalDateTime startDate = LocalDateTime.parse("2024-09-01T00:00:00");
//        LocalDateTime endDate = LocalDateTime.parse("2024-12-31T23:59:59");
//        String tag = "sport";
//        long created=12;
//
//        List<Task> tasks = taskDao.findByTagsAndDateRangeAndCreator(tag, startDate, endDate,created);
//        tasks.forEach(task -> System.out.println(task));
//        UserTokenService userTokenService = new UserTokenService();
//        long userId=13;
//        Utilisateur utilisateur=new Utilisateur();
//        UtilisateurService userService = new UtilisateurService();
//        utilisateur = userService.getUtilisateur(userId).orElse(null);
//        int countTokenSuppress = userTokenService.findByUserAndTokenType(utilisateur,"Remplacement").getTokenCount();
//        System.out.println("Nombre de tokens suppression : " + countTokenSuppress);

        long taskId = 21;
        TaskService taskService = new TaskService();
        Task task = taskService.getTaskById(taskId);
        taskService.deleteTask(21L);

    }
}