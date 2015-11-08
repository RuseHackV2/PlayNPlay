package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.ruse.hack.dao.MovieDao;
import com.ruse.hack.dao.MovieDaoImpl;
import com.ruse.hack.entity.Movie;
import com.ruse.hack.services.ModelMapperService;
import com.ruse.hack.services.ModelMapperServiceImpl;
import com.ruse.hack.services.TmdbService;
import com.ruse.hack.services.TmdbServiceImpl;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nslavov on 11/7/15.
 */
public class InitController extends Controller {

    private final TmdbService tmdbService;
    private final MovieDao movieDao;
    private final ModelMapperService mapper;

    @Inject
    public InitController(TmdbServiceImpl tmdbService, MovieDaoImpl movieDao, ModelMapperServiceImpl mapper) {
        this.tmdbService = tmdbService;
        this.movieDao = movieDao;
        this.mapper = mapper;
    }

    /**
     * Init
     * @return ok
     */
    @Transactional
    public Result initData(){

        List<JsonNode> jsonNodeList = new ArrayList<>();
        List<Movie> movieList = new ArrayList<>();

        try {
            jsonNodeList = tmdbService.loadMovie();
            movieList = mapper.map(jsonNodeList);
            movieDao.saveAll(movieList);
        } catch (IOException e) {
            e.printStackTrace();
        }
 //       movieList = movieDao.getNowPlayingRange(10,10);
//        System.out.println(Json.toJson(movieList.get(0)));
        return ok();
    }
}
