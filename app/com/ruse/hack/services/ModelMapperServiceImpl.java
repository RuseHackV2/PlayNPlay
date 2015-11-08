package com.ruse.hack.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruse.hack.entity.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nslavov on 11/7/15.
 */
public class ModelMapperServiceImpl implements  ModelMapperService {


    @Override
    public List<Movie> map(List<JsonNode> jsonNodeList) {

        List<Movie> movieList = new ArrayList<>();
        for(JsonNode node : jsonNodeList){
            Movie movie = new Movie();
            movie.setTmdb_id(node.get("id").asInt());
            movie.setOverview(node.get("overview").asText());
            movie.setPoster_path(node.get("poster_path").asText());
            movie.setTitle(node.get("original_title").asText());
            movie.setRelease_date(node.get("release_date").asText());
            movie.setVote_average(node.get("vote_average").asText());
            if(node.get("key") != null){
                movie.setKey(node.get("key").asText());
            }
            switch (node.get("category").asText()){
                case "top_rated": movie.setTop_rated(true);
                    break;
                case "now-playing": movie.setNow_playing(true);
                    break;
                case "popular": movie.setPopular(true);
                    break;
                case "upcoming": movie.setUpcoming(true);
                    break;
            }
            movieList.add(movie);
        }
        return movieList;
    }
}
