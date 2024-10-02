package com.example.devSync.dao.impl;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UtilisateurDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class UtilisateurDaoImpl implements UtilisateurDao {
    private EntityManagerFactory emf;
    private EntityManager entityManager;

    public UtilisateurDaoImpl() {
        this.emf = Persistence.createEntityManagerFactory("devSync");
        this.entityManager = emf.createEntityManager();
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
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

    @Override
    public Optional<Utilisateur> findById(Long id) {
        try {
            Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
            return Optional.ofNullable(utilisateur);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Utilisateur> findAll() {
        try {
            return entityManager.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
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

    @Override
    public void delete(int id) {
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

    @Override
    public List<Utilisateur> findByName(String name) {
        try {
            return entityManager.createQuery("SELECT u FROM Utilisateur u WHERE u.nom = :name", Utilisateur.class)
                    .setParameter("name", name)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
