package com.example.devSync.dao.impl;

import com.example.devSync.bean.Tag;
import com.example.devSync.dao.TagDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class TagDaoImpl implements TagDao, AutoCloseable{
    private final EntityManagerFactory emf;

    public TagDaoImpl() {
        this.emf = Persistence.createEntityManagerFactory("devSync");
    }

    @Override
    public Tag save(Tag tag) {
        try (EntityManager entityManager=emf.createEntityManager()){
            EntityTransaction transaction=entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(tag);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()){
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
            return tag;
        }
    }

    @Override
    public Optional<Tag> findById(Long tagId) {
        try(EntityManager entityManager= emf.createEntityManager()) {
            Tag tag=entityManager.find(Tag.class,tagId);
            return Optional.ofNullable(tag);
        }
    }

    @Override
    public List<Tag> findAll() {
        try(EntityManager entityManager= emf.createEntityManager()) {
            return entityManager.createQuery("select t from Tag t", Tag.class).getResultList();
        }
    }

    @Override
    public Tag update(Tag tag) {
        try(EntityManager entityManager= emf.createEntityManager()) {
            EntityTransaction transaction=entityManager.getTransaction();
            try{
                transaction.begin();
                Tag updatedTag=entityManager.merge(tag);
                transaction.commit();
                return updatedTag;
        } catch (Exception e) {
                if(transaction.isActive()){
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(long id) {
        try (EntityManager entityManager= emf.createEntityManager()){
            EntityTransaction transaction=entityManager.getTransaction();
            try {
                transaction.begin();
                Tag tag=entityManager.find(Tag.class, id);
                if(tag != null){
                    entityManager.remove(tag);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()){
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public List<Tag> findByName(String name) {
        return List.of();
    }

    @Override
    public void close() throws Exception {
    }
}
