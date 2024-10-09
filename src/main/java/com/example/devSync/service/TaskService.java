package com.example.devSync.service;

import com.example.devSync.bean.Tag;
import com.example.devSync.bean.Task;
import com.example.devSync.dao.TagDao;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.dao.impl.TaskDaoImpl;

import java.util.List;

public class TaskService {
    private TaskDao taskDao = new TaskDaoImpl();

    public void createTask(Task task) {
        taskDao.save(task);
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
}
