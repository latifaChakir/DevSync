package com.example.devSync.dao.impl;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UtilisateurDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class UtilisateurDaoImpl implements UtilisateurDao, AutoCloseable {
    private final EntityManagerFactory emf;

    public UtilisateurDaoImpl() {
        this.emf = Persistence.createEntityManagerFactory("devSync");
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(utilisateur);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
            return utilisateur;
        }
    }

    @Override
    public Optional<Utilisateur> findById(Long id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
            return Optional.ofNullable(utilisateur);
        }
    }

    @Override
    public List<Utilisateur> findAll() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
        }
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Utilisateur updatedUtilisateur = entityManager.merge(utilisateur);
                transaction.commit();
                return updatedUtilisateur;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

    @Override
    public void delete(long id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
                if (utilisateur != null) {
                    entityManager.remove(utilisateur);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

    @Override
    public List<Utilisateur> findByName(String name) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("SELECT u FROM Utilisateur u WHERE u.nom = :name", Utilisateur.class)
                    .setParameter("name", name)
                    .getResultList();
        }
    }

    @Override
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}