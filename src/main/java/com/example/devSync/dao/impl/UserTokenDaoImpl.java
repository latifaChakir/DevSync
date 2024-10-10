package com.example.devSync.dao.impl;

import com.example.devSync.bean.UserToken;
import com.example.devSync.dao.UserTokenDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class UserTokenDaoImpl implements UserTokenDao, AutoCloseable {
    private final EntityManagerFactory emf;

    public UserTokenDaoImpl() {
        this.emf = Persistence.createEntityManagerFactory("devSync");
    }

    @Override
    public UserToken save(UserToken userToken) {
        System.out.println(userToken);
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(userToken);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
            return userToken;
        }
    }

    @Override
    public Optional<UserToken> findById(Long id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            UserToken userToken = entityManager.find(UserToken.class, id);
            return Optional.ofNullable(userToken); // Retourne un Optional contenant l'utilisateur ou vide
        }
    }

    @Override
    public List<UserToken> findAll() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("SELECT ut FROM UserToken ut", UserToken.class).getResultList(); // Récupère tous les jetons
        }
    }

    @Override
    public UserToken update(UserToken userToken) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                UserToken updatedToken = entityManager.merge(userToken); // Met à jour l'entité
                transaction.commit();
                return updatedToken;
            } catch (Exception e) {
                transaction.rollback();
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
                UserToken userToken = entityManager.find(UserToken.class, id);
                if (userToken != null) {
                    entityManager.remove(userToken); // Supprime l'entité
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<UserToken> findByTokenType(String tokenType) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("SELECT ut FROM UserToken ut WHERE ut.tokenType = :tokenType", UserToken.class).getResultList();
        }
    }

    @Override
    public void close() throws Exception {
        if (emf != null && emf.isOpen()) {
            emf.close(); // Ferme l'EntityManagerFactory
        }
    }
}
