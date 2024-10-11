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
import java.util.List;

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
    public List <Task> getTasksByCreator (Long taskId) {
        return taskDao.getTasksByCreatorId(taskId);
    }
    public List <Task> getTasksByAssigned (Long taskId) {

        return taskDao.getTasksByAssigneeId(taskId);
    }

    public void  changeStatus(long taskId, Status status) {
        Task task = getTaskById(taskId);
        if (task!= null) {
            task.setStatus(status);
            updateTask(task);
        }
    }

}
