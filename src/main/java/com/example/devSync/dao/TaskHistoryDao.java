package com.example.devSync.dao;

import com.example.devSync.bean.TaskHistory;

import java.util.List;
import java.util.Optional;

public interface TaskHistoryDao {
    public TaskHistory save(TaskHistory taskHistory);
    public Optional<TaskHistory> findById(Long id);
    public List<TaskHistory> findAll();
    public TaskHistory update(TaskHistory taskHistory);
}
