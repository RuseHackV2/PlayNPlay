package com.ruse.hack.dao;

import com.ruse.hack.entity.BaseEntity;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

/**
 * Created by nslavov on 11/6/15.
 */
public interface BaseDao<T extends BaseEntity> {

    EntityManager getEntityManager();

    T save(T type);

    T update(T type);

    void persist(T entity);

    void persistAll(Collection<T> entities);

    List<T> findAll();

    void saveAll(Collection<T> collection);

    void deleteById(Long id);

    T findById(Long id);



}
