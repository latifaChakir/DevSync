package com.example.devSync.service;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.TaskHistory;
import com.example.devSync.bean.UserToken;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.TaskHistoryDao;
import com.example.devSync.dao.impl.TaskHistoryDaoImpl;

import java.time.LocalDate;
import java.util.List;

public class TaskHistoryService {
    private TaskHistoryDao taskHistoryDao=new TaskHistoryDaoImpl();
    private UserTokenService userTokenService=new UserTokenService();
//    public void addTaskHistory(TaskHistory taskHistory){
//        taskHistoryDao.save(taskHistory);
//    }
    public void AskToRemplace(Task task, Utilisateur user) {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(task);
        taskHistory.setUser(user);
        taskHistory.setIsApproved(false);
        taskHistory.setTypeModification("Remplacement");
        taskHistory.setModificationDate(LocalDate.now().atStartOfDay());
        taskHistoryDao.save(taskHistory);
        UserToken userToken = new UserToken();
        userToken.setUser(user);
        userToken.setTokenType("Remplacement");
        userToken.setTokenCount(2);
        userToken.setLastReset(LocalDate.now());
        userTokenService.save(userToken);
    }
    public void updateTaskHistory(TaskHistory taskHistory){
        taskHistoryDao.update(taskHistory);
    }
    public TaskHistory getTaskHistoryById(long id){
        return taskHistoryDao.findById(id).orElse(null);
    }
   public List<TaskHistory> getAllTaskHistory() {
        return taskHistoryDao.findAll();
   }

}
