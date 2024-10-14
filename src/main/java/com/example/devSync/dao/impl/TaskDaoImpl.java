package com.example.devSync.dao.impl;

import com.example.devSync.bean.Task;
import com.example.devSync.bean.Tag;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.bean.enums.Status;
import com.example.devSync.dao.TaskDao;
import com.example.devSync.dao.UtilisateurDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDaoImpl implements TaskDao, AutoCloseable {
    private final EntityManagerFactory emf;
    private UtilisateurDao utilisateurDao=new UtilisateurDaoImpl();

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
        EntityManager entityManager = emf.createEntityManager();
        Task task = null;

        try {
            entityManager.getTransaction().begin();
            task = entityManager.find(Task.class, id);
            if (task != null) {
                Hibernate.initialize(task.getTags());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return Optional.ofNullable(task);
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
    public void delete(long taskId) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Task task = entityManager.find(Task.class, taskId);
            if (task != null) {
                task.getTags().forEach(tag -> tag.getTasks().remove(task));
                task.getTags().clear();

                entityManager.remove(task);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
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

    @Override
    public List<Task> getTasksByAssigneeId(Long userId) {
        List<Task> tasks = new ArrayList<>();
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            try {
                Utilisateur assignedUser = utilisateurDao.findById(userId).orElse(null);

                if (assignedUser != null) {
                    tasks = entityManager.createQuery("SELECT t FROM Task t WHERE t.assignedTo = :assignedTo", Task.class)
                            .setParameter("assignedTo", assignedUser)
                            .getResultList();

                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }


    @Override
    public List<Task> getTasksByCreatorId(Long userId) {
        List<Task> tasks = new ArrayList<>();
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            try {
                Utilisateur cretatedUser = utilisateurDao.findById(userId).orElse(null);

                if (cretatedUser != null) {
                    tasks = entityManager.createQuery("SELECT t FROM Task t WHERE t.createdBy = :createBy", Task.class)
                            .setParameter("createBy", cretatedUser)
                            .getResultList();

                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public List<Task> findOverdueTasks(LocalDate date) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("SELECT t FROM Task t WHERE t.deadLine <= :date AND t.status!= 'TERMINEE'", Task.class)
                   .setParameter("date", date)
                   .getResultList();
        }
    }
    @Override
    public List<Task> findByTagsAndDateRangeAndCreator(String tag, LocalDateTime startDate, LocalDateTime endDate, Long creatorId) {
        List<Task> tasks = new ArrayList<>();
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            try {
                tasks = entityManager.createQuery(
                                "SELECT t FROM Task t JOIN t.tags tg " +
                                        "WHERE tg.name = :tag " +
                                        "AND t.createdBy.id = :creatorId " +
                                        "AND t.deadLine BETWEEN :startDate AND :endDate", Task.class)
                        .setParameter("tag", tag)
                        .setParameter("creatorId", creatorId)
                        .setParameter("startDate", startDate)
                        .setParameter("endDate", endDate)
                        .getResultList();

                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
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

    public void changeStatus(long taskId, Status status) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Task task = entityManager.find(Task.class, taskId);
                if (task != null) {
                    task.setStatus(status);
                    entityManager.merge(task);
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
    public void close() throws Exception {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
