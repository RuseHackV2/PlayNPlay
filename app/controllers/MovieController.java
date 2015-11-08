package controllers;

import com.google.inject.Inject;
import com.ruse.hack.dao.MovieDao;
import com.ruse.hack.dao.MovieDaoImpl;
import com.ruse.hack.entity.Movie;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nslavov on 11/7/15.
 */
public class MovieController extends Controller {

    private final MovieDao movieDao;

    @Inject
    public MovieController(MovieDaoImpl movieDao) {
        this.movieDao = movieDao;
    }

    @Transactional(readOnly = true)
    public Result getMovie(String category ,int offset,int limit){
        List<Movie> movieList = new ArrayList<>();

         switch (category){
             case "popular" : movieList = movieDao.getPopularRange(limit,offset);
                 break;
             case "upcoming" : movieList = movieDao.getUpcomingRange(limit, offset);
                 break;
             case "top-rated" : movieList = movieDao.getTopRatedRange(limit, offset);
                 break;
             case "now-playing" : movieList = movieDao.getNowPlayingRange(limit, offset);
                 break;
        }
        System.out.println(movieList.size());
        return ok(Json.toJson(movieList));
    }

    /**
     *
     * @return
     */
    @Transactional
    public Result getCount(){

        Long count = movieDao.getCount();

        return ok(Json.toJson(count));
    }

    /**
     * Get Movie count by category
     * @param category
     * @return
     */
    @Transactional
    public Result getCountByCategory(String category){

        Long count = null;
        switch (category){
            case "popular" : count = movieDao.getCountByCategory("popular");
                break;
            case "upcoming" : count = movieDao.getCountByCategory("upcoming");
                break;
            case "top-rated" : count = movieDao.getCountByCategory("top_rated");
                break;
            case "now-playing" : count = movieDao.getCountByCategory("now_playing");
                break;
        }
        return  ok(Json.toJson(count));
    }
}
