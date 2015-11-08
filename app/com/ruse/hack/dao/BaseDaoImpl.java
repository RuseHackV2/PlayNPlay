package com.ruse.hack.dao;

import com.ruse.hack.entity.BaseEntity;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created by nslavov on 11/6/15.
 */
public class BaseDaoImpl <T extends BaseEntity> implements BaseDao<T> {

    private Class<T> type;

    public BaseDaoImpl(Class<T> type){
        this.type = type;
    }

    @Override
    public EntityManager getEntityManager() {
        return JPA.em();
    }

    @Override
    public T save(T type) {
        EntityManager entityManager = getEntityManager();
        entityManager.merge(type);
        return type;
    }

    @Override
    public T update(T type) {
        EntityManager entityManager = getEntityManager();
        entityManager.merge(type);
        return type;
    }

    @Override
    public void persist(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.persist(entity);

    }

    @Override
    public void persistAll(Collection<T> entities) {
        EntityManager entityManager = getEntityManager();
        for(T entry : entities){
            entityManager.persist(entry);
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager entityManager = getEntityManager();
        return entityManager.createQuery("select t from " + type.getSimpleName() + " t").getResultList();
    }

    @Override
    public void saveAll(Collection<T> collection) {
        EntityManager entityManager = getEntityManager();
        for(T type : collection){
            entityManager.merge(type);
        }

    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.remove(findById(id));

    }

    @Override
    public T findById(Long id) {
        EntityManager entityManager = getEntityManager();
        T found = entityManager.find(type,id);
        return found;
    }
}
