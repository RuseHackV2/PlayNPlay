package com.ruse.hack.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by nslavov on 11/6/15.
 */

@javax.persistence.Entity
@Table(name = "movie")
public class Movie extends  BaseEntity {

    @Column(name = "tmdb_id")
    private int tmdb_id;

    @Column(name = "title",length = 1000)
    private String title;

    @Column(name = "overview",length = 3000)
    private String overview;

    @Column(name = "release_date",length = 20)
    private String release_date;

    @Column(name = "poster_path",length = 300)
    private String poster_path;

    @Column(name = "vote_average")
    private String vote_average;

    @Column(name = "key")
    private String key;

    @Column(name = "popular")
    private boolean popular;

    @Column(name = "upcoming")
    private boolean upcoming;

    @Column(name = "top_rated")
    private boolean top_rated;

    @Column(name = "now_playing")
    private boolean now_playing;

    public int getTmdb_id() {
        return tmdb_id;
    }

    public void setTmdb_id(int tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    public boolean isUpcoming() {
        return upcoming;
    }

    public void setUpcoming(boolean upcoming) {
        this.upcoming = upcoming;
    }

    public boolean isTop_rated() {
        return top_rated;
    }

    public void setTop_rated(boolean top_rated) {
        this.top_rated = top_rated;
    }

    public boolean isNow_playing() {
        return now_playing;
    }

    public void setNow_playing(boolean now_playing) {
        this.now_playing = now_playing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (tmdb_id != movie.tmdb_id) return false;
        if (popular != movie.popular) return false;
        if (upcoming != movie.upcoming) return false;
        if (top_rated != movie.top_rated) return false;
        if (now_playing != movie.now_playing) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null) return false;
        if (release_date != null ? !release_date.equals(movie.release_date) : movie.release_date != null) return false;
        if (poster_path != null ? !poster_path.equals(movie.poster_path) : movie.poster_path != null) return false;
        if (vote_average != null ? !vote_average.equals(movie.vote_average) : movie.vote_average != null) return false;
        return !(key != null ? !key.equals(movie.key) : movie.key != null);

    }

    @Override
    public int hashCode() {
        int result = tmdb_id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        result = 31 * result + (poster_path != null ? poster_path.hashCode() : 0);
        result = 31 * result + (vote_average != null ? vote_average.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (popular ? 1 : 0);
        result = 31 * result + (upcoming ? 1 : 0);
        result = 31 * result + (top_rated ? 1 : 0);
        result = 31 * result + (now_playing ? 1 : 0);
        return result;
    }
}
