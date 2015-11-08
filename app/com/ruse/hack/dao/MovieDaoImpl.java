package com.ruse.hack.dao;

import com.ruse.hack.entity.Movie;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


/**
 * Created by nslavov on 11/6/15.
 */
public class MovieDaoImpl extends BaseDaoImpl<Movie> implements MovieDao {
    public MovieDaoImpl(Class<Movie> type) {
        super(type);
    }

    public MovieDaoImpl(){
        this(Movie.class);
    }

    @Override
    public List<Movie> getPopularRange(int limit,int offset) {
        EntityManager entityManager = getEntityManager();
        String find = "select m from Movie m where m.popular = true";
        Query query = entityManager.createQuery(find);
        List<Movie> movieList = query.setFirstResult(offset).setMaxResults(limit).getResultList();
        return movieList;
    }

    @Override
    public List<Movie> getUpcomingRange(int limit,int offset) {
        EntityManager entityManager = getEntityManager();
        String find = "select m from Movie m where m.upcoming = true";
        Query query = entityManager.createQuery(find);
        List<Movie> movieList = query.setFirstResult(offset).setMaxResults(limit).getResultList();
        return movieList;
    }

    @Override
    public List<Movie> getTopRatedRange(int limit,int offset) {
        EntityManager entityManager = getEntityManager();
        String find = "select m from Movie m where m.top_rated = true";
        Query query = entityManager.createQuery(find);
        List<Movie> movieList = query.setFirstResult(offset).setMaxResults(limit).getResultList();
        return movieList;
    }

    @Override
    public List<Movie> getNowPlayingRange(int limit,int offset) {
        EntityManager entityManager = getEntityManager();
        String find = "select m from Movie m where m.now_playing = true";
        Query query = entityManager.createQuery(find);
        List<Movie> movieList = query.setFirstResult(offset).setMaxResults(limit).getResultList();
        return movieList;
    }

    @Override
    public Long getCount() {
        EntityManager entityManager = getEntityManager();
        String find = "SELECT count(m) from Movie m";
        Long coun = (Long) entityManager.createQuery(find).getSingleResult();
        return coun;
    }

    @Override
    public Long getCountByCategory(String category) {
        EntityManager entityManager = getEntityManager();
        String find = "SELECT count(m) from Movie m where m." + category + " =true";
        Long coun = (Long) entityManager.createQuery(find).getSingleResult();
        return coun;
    }
}
