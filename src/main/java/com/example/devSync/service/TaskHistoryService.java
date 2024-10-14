package com.example.devSync.service;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.TaskHistory;
import com.example.devSync.bean.UserToken;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.TaskHistoryDao;
import com.example.devSync.dao.impl.TaskHistoryDaoImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskHistoryService {
    private TaskHistoryDao taskHistoryDao = new TaskHistoryDaoImpl();
    private TaskService taskService = new TaskService();
    private UserTokenService userTokenService = new UserTokenService();

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
        String tokenType = "Remplacement";
        UserToken existingUserToken = userTokenService.findByUserAndTokenType(user, tokenType);

        if (existingUserToken != null) {
            int currentCount = existingUserToken.getTokenCount();
            if (currentCount > 0) {
                existingUserToken.setTokenCount(currentCount - 1);
                existingUserToken.setLastReset(LocalDate.now());
                userTokenService.update(existingUserToken);
            } else {
                System.out.println("Le compteur de jetons est déjà à zéro pour cet utilisateur.");
            }
        } else {
            System.out.println("Aucun jeton trouvé pour cet utilisateur avec le type de jeton spécifié.");
        }
    }

    public void AskToRemove(Task task, Utilisateur user) {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(task);
        taskHistory.setUser(user);
        taskHistory.setIsApproved(false);
        taskHistory.setTypeModification("Suppression");
        taskHistory.setModificationDate(LocalDate.now().atStartOfDay());
        taskHistoryDao.save(taskHistory);
        String tokenType = "Suppression";
        UserToken existingUserToken = userTokenService.findByUserAndTokenType(user, tokenType);
        if (existingUserToken != null) {
            int currentCount = existingUserToken.getTokenCount();
            if (currentCount > 0) {
                existingUserToken.setTokenCount(currentCount - 1);
                existingUserToken.setLastReset(LocalDate.now());
                userTokenService.update(existingUserToken);
            } else {
                System.out.println("Le compteur de jetons est déjà à zéro pour cet utilisateur.");
            }
        } else {
            System.out.println("Aucun jeton trouvé pour cet utilisateur avec le type de jeton spécifié.");
        }
    }


    public void approveRemplace(TaskHistory taskHistory) {
        taskHistory.setIsApproved(true);
        taskHistoryDao.update(taskHistory);
    }
    public void desapproveRemplace(TaskHistory taskHistory) {
        taskHistory.setIsApproved(false);
        taskHistoryDao.update(taskHistory);

    }
    public void updateTaskHistory(TaskHistory taskHistory) {
        taskHistoryDao.update(taskHistory);
    }

    public TaskHistory getTaskHistoryById(long id) {
        return taskHistoryDao.findById(id).orElse(null);
    }

    public List<TaskHistory> getAllTaskHistory() {
        return taskHistoryDao.getAllTaskHistory();
    }

    public List<TaskHistory> getMyRequestToApproved(Utilisateur user) {
        List<TaskHistory> allTaskHistory = taskHistoryDao.findAll();
        List<TaskHistory> tasksWrittenByUser = allTaskHistory.stream()
                .filter(taskHistory -> taskHistory.getTask().getCreatedBy().getId().equals(user.getId()) &&
                        !taskHistory.getIsApproved())
                .collect(Collectors.toList());

        return tasksWrittenByUser;
    }

}
