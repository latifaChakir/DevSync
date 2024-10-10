package com.example.devSync.dao.impl;

import com.example.devSync.bean.TaskHistory;
import com.example.devSync.dao.TaskHistoryDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class TaskHistoryDaoImpl implements TaskHistoryDao, AutoCloseable {

    private final EntityManagerFactory emf;

    public TaskHistoryDaoImpl() {
        this.emf = Persistence.createEntityManagerFactory("devSync");
    }

    @Override
    public TaskHistory save(TaskHistory taskHistory) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(taskHistory);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
            return taskHistory;
        }
    }

    @Override
    public Optional<TaskHistory> findById(Long id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            TaskHistory taskHistory = entityManager.find(TaskHistory.class, id);
            return Optional.ofNullable(taskHistory);
        }
    }

    @Override
    public List<TaskHistory> findAll() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("select t from TaskHistory t", TaskHistory.class).getResultList();
        }
    }

    @Override
    public TaskHistory update(TaskHistory taskHistory) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                TaskHistory updatedTaskHistory = entityManager.merge(taskHistory);
                transaction.commit();
                return updatedTaskHistory;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void close() throws Exception {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
