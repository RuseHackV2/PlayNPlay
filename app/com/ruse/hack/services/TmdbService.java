package com.ruse.hack.services;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;

/**
 * Created by nslavov on 11/7/15.
 */
public interface TmdbService {

    /**
     *
     * @return
     * @throws IOException
     */
    List<JsonNode> loadMovie() throws IOException;
}
