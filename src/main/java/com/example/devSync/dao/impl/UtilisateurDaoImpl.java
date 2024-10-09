package com.example.devSync.dao.impl;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UtilisateurDao;
import jakarta.persistence.*;

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

    public Utilisateur findByUsername(String nomUtilisateur) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.nomUtilisateur = :nomUtilisateur", Utilisateur.class);
        query.setParameter("nomUtilisateur", nomUtilisateur);
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            em.close();
        }
    }

    @Override
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}