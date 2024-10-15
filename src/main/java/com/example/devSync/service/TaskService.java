package com.example.devSync.service;

import com.example.devSync.bean.Tag;
import com.example.devSync.bean.Task;
import com.example.devSync.bean.UserToken;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.bean.enums.Status;
import com.example.devSync.dao.TagDao;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.dao.impl.TaskDaoImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService {
    private TaskDao taskDao = new TaskDaoImpl();
    private UserTokenService userTokenService = new UserTokenService();

    public void createTask(Task task) {
        taskDao.save(task);
        Utilisateur assignedUser = task.getAssignedTo();

        UserToken replacementToken = userTokenService.findByUserAndTokenType(assignedUser, "Remplacement");
        if (replacementToken == null) {
            replacementToken = new UserToken();
            replacementToken.setUser(assignedUser);
            replacementToken.setTokenType("Remplacement");
            replacementToken.setTokenCount(2);
            replacementToken.setLastReset(LocalDate.now());
            userTokenService.save(replacementToken);
        }

        UserToken deletionToken = userTokenService.findByUserAndTokenType(assignedUser, "Suppression");
        if (deletionToken == null) {
            deletionToken = new UserToken();
            deletionToken.setUser(assignedUser);
            deletionToken.setTokenType("Suppression");
            deletionToken.setTokenCount(1);
            deletionToken.setLastReset(LocalDate.now());
            userTokenService.save(deletionToken);
        }
    }

    public void updateTask(Task task) {
        taskDao.update(task);
    }

    public void deleteTask(Long taskId) {
        taskDao.delete(taskId);
    }

    public Task getTaskById(Long taskId) {
        return taskDao.findById(taskId).orElse(null);
    }

    public List<Task> getAllTasks() {
        return taskDao.findAll();
    }

    public List<Task> getTasksByCreator(Long taskId) {
        return taskDao.getTasksByCreatorId(taskId);
    }

    public List<Task> getTasksByAssigned(Long taskId) {

        return taskDao.getTasksByAssigneeId(taskId);
    }

    public void changeStatus(long taskId, Status status) {
        taskDao.changeStatus(taskId, status);
    }

    public List<Task> findOverdueTasks(LocalDateTime date) {
        List<Task> overdueTasks = taskDao.findOverdueTasks(date);
        for (Task task : overdueTasks) {
            changeStatus(task.getId(),Status.NON_EFFECTUER);
        }
        return overdueTasks;
    }

    public Map<String, Long> calculateStatisticsByCreatorId(Long creatorId) {
        List<Task> createdTasks = taskDao.getTasksByCreatorId(creatorId);

        long totalCreatedTasks = createdTasks.size();
        long completedCreatedTasks = createdTasks.stream()
                .filter(task -> task.getStatus().equals(Status.TERMINEE))
                .count();
        long inProgressCreatedTasks = createdTasks.stream()
                .filter(task -> task.getStatus().equals(Status.EN_COURS))
                .count();
        long notStartedCreatedTasks = createdTasks.stream()
                .filter(task -> task.getStatus().equals(Status.A_FAIRE))
                .count();
        long notEffectuedCreatedTasks = createdTasks.stream()
                .filter(task -> task.getStatus().equals(Status.NON_EFFECTUER))
                .count();

        Map<String, Long> statistics = new HashMap<>();
        statistics.put("totalCreatedTasks", totalCreatedTasks);
        statistics.put("completedCreatedTasks", completedCreatedTasks);
        statistics.put("inProgressCreatedTasks", inProgressCreatedTasks);
        statistics.put("notStartedCreatedTasks", notStartedCreatedTasks);
        statistics.put("notEffectuedCreatedTasks", notEffectuedCreatedTasks);

        return statistics;
    }
}
