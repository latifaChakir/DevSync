package com.example.devSync.service;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.TaskHistory;
import com.example.devSync.bean.UserToken;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.TaskHistoryDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskHistoryServiceTest {

    @Mock
    private TaskHistoryDao taskHistoryDaoMock;

    @Mock
    private TaskService taskServiceMock;

    @Mock
    private UserTokenService userTokenServiceMock;

    @InjectMocks
    private TaskHistoryService taskHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAskToRemplace() {
        Task task = new Task();
        Utilisateur user = new Utilisateur();
        user.setId(1L);

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(task);
        taskHistory.setUser(user);
        taskHistory.setIsApproved(false);
        taskHistory.setTypeModification("Remplacement");
        taskHistory.setModificationDate(LocalDate.now().atStartOfDay());

        when(taskHistoryDaoMock.save(any(TaskHistory.class))).thenAnswer(invocation -> {
            TaskHistory savedTaskHistory = invocation.getArgument(0);
            savedTaskHistory.setId(1L);
            return savedTaskHistory;
        });

        UserToken userToken = new UserToken();
        userToken.setUser(user);
        userToken.setTokenType("Remplacement");
        userToken.setTokenCount(1);

        when(userTokenServiceMock.findByUserAndTokenType(user, "Remplacement")).thenReturn(userToken);

        taskHistoryService.AskToRemplace(task, user);

        verify(taskHistoryDaoMock, times(1)).save(any(TaskHistory.class));
        assertEquals(0, userToken.getTokenCount(), "Le compteur de jetons doit être décrémenté de 1.");
    }

    @Test
    void testAskToRemove() {
        Task task = new Task();
        Utilisateur user = new Utilisateur();
        user.setId(1L);

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(task);
        taskHistory.setUser(user);
        taskHistory.setIsApproved(false);
        taskHistory.setTypeModification("Suppression");
        taskHistory.setModificationDate(LocalDate.now().atStartOfDay());

        when(taskHistoryDaoMock.save(any(TaskHistory.class))).thenAnswer(invocation -> {
            TaskHistory savedTaskHistory = invocation.getArgument(0);
            savedTaskHistory.setId(1L);
            return savedTaskHistory;
        });

        UserToken userToken = new UserToken();
        userToken.setUser(user);
        userToken.setTokenType("Suppression");
        userToken.setTokenCount(1);

        when(userTokenServiceMock.findByUserAndTokenType(user, "Suppression")).thenReturn(userToken);

        taskHistoryService.AskToRemove(task, user);

        verify(taskHistoryDaoMock, times(1)).save(any(TaskHistory.class));
        assertEquals(0, userToken.getTokenCount(), "Le compteur de jetons doit être décrémenté de 1.");
    }

    @Test
    void testApproveRemplace() {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setId(1L);
        taskHistory.setIsApproved(false);

        taskHistoryService.approveRemplace(taskHistory);

        verify(taskHistoryDaoMock, times(1)).update(taskHistory);
        assertTrue(taskHistory.getIsApproved(), "La tâche doit être approuvée.");
    }

    @Test
    void testDesapproveRemplace() {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setId(1L);
        taskHistory.setIsApproved(true);

        taskHistoryService.desapproveRemplace(taskHistory);

        verify(taskHistoryDaoMock, times(1)).update(taskHistory);
        assertFalse(taskHistory.getIsApproved(), "La tâche doit être désapprouvée.");
    }

    @Test
    void testGetTaskHistoryById() {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setId(1L);

        when(taskHistoryDaoMock.findById(1L)).thenReturn(java.util.Optional.of(taskHistory));
        TaskHistory result = taskHistoryService.getTaskHistoryById(1L);

        assertEquals(taskHistory, result, "Le taskHistory retourné doit être le même que celui attendu.");
        verify(taskHistoryDaoMock, times(1)).findById(1L);
    }

    @Test
    void testGetAllTaskHistory() {
        List<TaskHistory> taskHistories = List.of(new TaskHistory(), new TaskHistory());
        when(taskHistoryDaoMock.getAllTaskHistory()).thenReturn(taskHistories);

        List<TaskHistory> result = taskHistoryService.getAllTaskHistory();

        assertEquals(2, result.size(), "Le nombre d'historique de tâches retourné doit être 2.");
        verify(taskHistoryDaoMock, times(1)).getAllTaskHistory();
    }

    @Test
    void testGetMyRequestToApproved() {
        Utilisateur user = new Utilisateur();
        user.setId(1L);

        Task task = new Task();
        task.setCreatedBy(user);
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(task);
        taskHistory.setUser(user);
        taskHistory.setIsApproved(false);

        when(taskHistoryDaoMock.findAll()).thenReturn(List.of(taskHistory));

        List<TaskHistory> result = taskHistoryService.getMyRequestToApproved(user);

        assertEquals(1, result.size(), "Le nombre d'historique de tâches retourné doit être 1.");
        verify(taskHistoryDaoMock, times(1)).findAll();
    }
}
