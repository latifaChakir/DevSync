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
    public UtilisateurDaoImpl(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("devSync");
        this.entityManager = emf.createEntityManager();
    }
    @Override
    public  Utilisateur save(Utilisateur utilisateur) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
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
    public Optional<Utilisateur> findById(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
            transaction.commit();
            return Optional.ofNullable(utilisateur);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Utilisateur> findAll() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            List<Utilisateur> utilisateurs = entityManager.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
            transaction.commit();
            return utilisateurs;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            Utilisateur updatedUtilisateur = entityManager.merge(utilisateur);
            transaction.commit();
            return updatedUtilisateur;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }    }

    @Override
    public void delete(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
            if (utilisateur != null) {
                entityManager.remove(utilisateur);
                transaction.commit();
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
        return List.of();
    }
}
