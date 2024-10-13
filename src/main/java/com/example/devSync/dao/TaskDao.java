package com.example.devSync.dao;

import com.example.devSync.bean.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskDao {
    public Task save(Task task);
    public Optional<Task> findById(Long id);
    public List<Task> findAll();
    public Task update(Task task);
    public void delete(long id);
    public List<Task> findByName(String name);
    public List<Task> getTasksByAssigneeId(Long id);
    public List<Task> getTasksByCreatorId(Long id);

    List<Task> findOverdueTasks(LocalDate date);
    List<Task> findByTagsAndDateRangeAndCreator(String tag, LocalDateTime startDate, LocalDateTime endDate, Long creatorId);
}
