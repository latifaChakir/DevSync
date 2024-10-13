package com.example.devSync;
import com.example.devSync.bean.Task;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.dao.impl.TaskDaoImpl;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskDao taskDao = new TaskDaoImpl();

        LocalDateTime startDate = LocalDateTime.parse("2024-09-01T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2024-12-31T23:59:59");
        String tag = "sport";
        long created=12;

        List<Task> tasks = taskDao.findByTagsAndDateRangeAndCreator(tag, startDate, endDate,created);
        tasks.forEach(task -> System.out.println(task));
    }
}