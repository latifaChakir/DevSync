package com.example.devSync;
import com.example.devSync.bean.Task;
import com.example.devSync.bean.enums.Status;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.dao.UtilisateurDao;
import com.example.devSync.dao.impl.TaskDaoImpl;
import com.example.devSync.dao.impl.UtilisateurDaoImpl;
import com.example.devSync.service.TaskService;

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
        TaskService taskService=new TaskService();
        long taskid=19L;
        Task task = taskService.getTaskById(taskid);
        System.out.println("Task : "+task.getTags());


    }
}