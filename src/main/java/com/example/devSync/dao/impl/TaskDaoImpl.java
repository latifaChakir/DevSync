package com.example.devSync.dao.impl;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.Tag;
import com.example.devSync.dao.TaskDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class TaskDaoImpl implements TaskDao, AutoCloseable {
    private final EntityManagerFactory emf;

    public TaskDaoImpl() {
        this.emf = Persistence.createEntityManagerFactory("devSync");
    }

    @Override
    public Task save(Task task) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                updateTasksTags(task, entityManager);
                entityManager.persist(task);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
            return task;
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            Task task = entityManager.find(Task.class, id);
            return Optional.ofNullable(task);
        }
    }

    @Override
    public List<Task> findAll() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
        }
    }

    @Override
    public Task update(Task task) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                updateTasksTags(task, entityManager);
                Task updatedTask = entityManager.merge(task);
                transaction.commit();
                return updatedTask;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(long id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Task task = entityManager.find(Task.class, id);
                if (task != null) {
                    entityManager.remove(task);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Task> findByName(String name) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("SELECT t FROM Task t WHERE t.title = :name", Task.class)
                    .setParameter("name", name)
                    .getResultList();
        }
    }

    private void updateTasksTags(Task task, EntityManager entityManager) {
        if (task.getTags() != null) {
            for (int i = 0; i < task.getTags().size(); i++) {
                Tag tag = task.getTags().get(i);
                if (tag.getId() != null) {
                    task.getTags().set(i, entityManager.merge(tag));
                } else {
                    entityManager.persist(tag);
                }
            }
        }
    }

    @Override
    public void close() throws Exception {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
