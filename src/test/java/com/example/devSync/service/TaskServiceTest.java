package com.example.devSync.service;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.bean.enums.Status;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.bean.UserToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskDao taskDaoMock;

    @Mock
    private UserTokenService userTokenServiceMock;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        Utilisateur assignedUser = new Utilisateur();
        assignedUser.setId(1L);
        task.setAssignedTo(assignedUser);
        when(userTokenServiceMock.findByUserAndTokenType(assignedUser, "Remplacement")).thenReturn(null);
        when(userTokenServiceMock.findByUserAndTokenType(assignedUser, "Suppression")).thenReturn(null);

        taskService.createTask(task);

        verify(taskDaoMock, times(1)).save(task);
        verify(userTokenServiceMock, times(2)).save(any(UserToken.class));
    }

    @Test
    void testUpdateTask() {
        Task task = new Task();
        task.setId(1L);
        taskService.updateTask(task);
        verify(taskDaoMock, times(1)).update(task);
    }

    @Test
    void testDeleteTask() {
        taskService.deleteTask(1L);

        verify(taskDaoMock, times(1)).delete(1L);
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        when(taskDaoMock.findById(1L)).thenReturn(Optional.of(task));
        Task result = taskService.getTaskById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(taskDaoMock, times(1)).findById(1L);
    }

    @Test
    void testGetAllTasks() {
        when(taskDaoMock.findAll()).thenReturn(Arrays.asList(new Task(), new Task()));
        List<Task> tasks = taskService.getAllTasks();
        assertEquals(2, tasks.size());
        verify(taskDaoMock, times(1)).findAll();
    }

    @Test
    void testGetTasksByCreator() {
        when(taskDaoMock.getTasksByCreatorId(1L)).thenReturn(Arrays.asList(new Task(), new Task()));
        List<Task> tasks = taskService.getTasksByCreator(1L);
        assertEquals(2, tasks.size());
        verify(taskDaoMock, times(1)).getTasksByCreatorId(1L);
    }

    @Test
    void testChangeStatus() {
        Task task = new Task();
        task.setId(1L);
        taskService.changeStatus(1L, Status.EN_COURS);
        verify(taskDaoMock, times(1)).changeStatus(1L, Status.EN_COURS);
    }

    @Test
    void testFindOverdueTasks() {
        LocalDateTime date = LocalDateTime.now();
        Task overdueTask = new Task();
        overdueTask.setId(1L);
        when(taskDaoMock.findOverdueTasks(date)).thenReturn(Arrays.asList(overdueTask));
        List<Task> overdueTasks = taskService.findOverdueTasks(date);
        assertEquals(1, overdueTasks.size());
        verify(taskDaoMock, times(1)).findOverdueTasks(date);
        verify(taskDaoMock, times(1)).changeStatus(1L, Status.NON_EFFECTUER);
    }

    @Test
    void testCalculateStatisticsByCreatorId() {
        Task task1 = new Task();
        task1.setStatus(Status.TERMINEE);
        Task task2 = new Task();
        task2.setStatus(Status.EN_COURS);
        Task task3 = new Task();
        task3.setStatus(Status.A_FAIRE);
        Task task4 = new Task();
        task4.setStatus(Status.NON_EFFECTUER);
        when(taskDaoMock.getTasksByCreatorId(1L)).thenReturn(Arrays.asList(task1, task2, task3, task4));
        var statistics = taskService.calculateStatisticsByCreatorId(1L);
        assertEquals(4, statistics.get("totalCreatedTasks"));
        assertEquals(1, statistics.get("completedCreatedTasks"));
        assertEquals(1, statistics.get("inProgressCreatedTasks"));
        assertEquals(1, statistics.get("notStartedCreatedTasks"));
        assertEquals(1, statistics.get("notEffectuedCreatedTasks"));

        verify(taskDaoMock, times(1)).getTasksByCreatorId(1L);
    }
}
