package com.ruse.hack.dao;

import com.ruse.hack.entity.Movie;

import java.util.List;

/**
 * Created by nslavov on 11/6/15.
 */
public interface MovieDao extends BaseDao<Movie> {

    List<Movie> getPopularRange(int limit,int offset);

    List<Movie> getUpcomingRange(int limit,int offset);

    List<Movie> getTopRatedRange(int limit,int offset);

    List<Movie> getNowPlayingRange(int limit,int offset);

    Long getCount();

    Long getCountByCategory(String category);

}
