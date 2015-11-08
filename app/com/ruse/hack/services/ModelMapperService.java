package com.ruse.hack.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruse.hack.entity.Movie;

import java.util.List;

/**
 * Created by nslavov on 11/7/15.
 */
public interface ModelMapperService {

    /**
     *
     * @param jsonNodeList
     * @return
     */
    List<Movie> map(List<JsonNode> jsonNodeList);
}
