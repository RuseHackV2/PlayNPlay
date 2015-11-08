package com.ruse.hack.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import play.libs.Json;

/**
 * Created by nslavov on 11/7/15.
 */


public class TmdbServiceImpl implements TmdbService {

  private final String API_KEY = "?api_key=0994c96d657eedf101516b5fa00c5d5e";
  private final String URL_POPULAR = "http://api.themoviedb.org/3/movie/popular";
  private final String URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated";
  private final String URL_UPCOMING = "http://api.themoviedb.org/3/movie/upcoming";
  private final String URL_NOW_PLAYING = "http://api.themoviedb.org/3/movie/now_playing";
  private final String URL_BY_ID_TRAILER = "http://api.themoviedb.org/3/movie/";
  private final String POPULAR = "popular";
  private final String TOP_RATED = "top_rated";
  private final String UPCOMING = "upcoming";
  private final String NOW_PLAYING = "now-playing";
  private final String PARAM_PAGE = "page=";
  private final String[] FILTER = {"id", "title", "overview", "poster_path", "imdb_id", "key",
      "category", "release_date", "original_title", "vote_average"};
  private final HttpClientBuilder httpClient;

  @Inject
  public TmdbServiceImpl(HttpClientBuilder httpClient) {
    this.httpClient = httpClient;
  }

  private int getPage(String url) throws IOException {
    System.out.println(url);
    int page = 0;
    try {
      if (url != null) {
        JsonNode result = null;
        BasicResponseHandler response = new BasicResponseHandler();
        ObjectMapper mapper = new ObjectMapper();
        HttpGet httpGet = new HttpGet(url);
        String responseBody = httpClient.build().execute(httpGet, response);
        if (responseBody != null) {
          result = mapper.readTree(responseBody);
        }
        page = result.get("total_pages").asInt();
      }
      if (page > 5) {
        page = 5;
      }
    } catch (HttpResponseException e) {

    }


    return page;
  }

  private List<JsonNode> getMovieByCategory(String category, String url) throws IOException {
    String newUrl = null;
    int page = getPage(url + API_KEY);
    System.out.println("newwwww  " + url + API_KEY);

    JsonNode result = null;
    List<JsonNode> jsonNodeList = new ArrayList<JsonNode>();
    BasicResponseHandler response = new BasicResponseHandler();
    ObjectMapper mapper = new ObjectMapper();
    Iterator<JsonNode> iterator = null;
    try {
      for (int i = 1; i < page; i++) {
        newUrl = url + API_KEY + "&" + PARAM_PAGE + i;
        HttpGet httpGet = new HttpGet(newUrl);
        String responseBody = httpClient.build().execute(httpGet, response);
        if (responseBody != null) {
          result = mapper.readTree(responseBody);
        }
        iterator = result.get("results").elements();
        while (iterator.hasNext()) {
          ObjectNode node = (ObjectNode) iterator.next();
          node.retain(FILTER);
          node.put("category", category);
          jsonNodeList.add((JsonNode) node);
          System.out.println(node);
        }
      }
    } catch (HttpResponseException e) {

    }

    System.out.println(Json.toJson(result));
    return jsonNodeList;
  }

  private List<JsonNode> getTrailer(List<JsonNode> jsonNodeList) throws IOException {
    ObjectNode result;
    BasicResponseHandler response = new BasicResponseHandler();
    ObjectMapper mapper = new ObjectMapper();
    String responseBody = null;
    List<JsonNode> resultList = new ArrayList<>();
    try {
      for (int i = 0; i < jsonNodeList.size(); i++) {
        ObjectNode node = (ObjectNode) jsonNodeList.get(i);
        String url = URL_BY_ID_TRAILER + node.get("id") + "/videos" + API_KEY;
        System.out.println(url);
        HttpGet httpGet = new HttpGet(url);
        responseBody = httpClient.build().execute(httpGet, response);
        if (responseBody != null) {
          result = (ObjectNode) mapper.readTree(responseBody);
          if (result.get("results").get(0) != null) {
            System.out.println(result.get("results").get(0).get("key"));
            node.put("key", result.get("results").get(0).get("key").asText());
          }
          resultList.add(node);
        }
      }
    } catch (HttpResponseException e) {

    }

    return resultList;
  }

  @Override
  public List<JsonNode> loadMovie() throws IOException {
    List<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList = getMovieByCategory(POPULAR, URL_POPULAR);
    jsonNodeList.addAll(getMovieByCategory(UPCOMING, URL_UPCOMING));
    jsonNodeList.addAll(getMovieByCategory(NOW_PLAYING, URL_NOW_PLAYING));
    jsonNodeList.addAll(getMovieByCategory(TOP_RATED, URL_TOP_RATED));
    jsonNodeList = getTrailer(jsonNodeList);
    System.out.println(Json.toJson(jsonNodeList.get(0)));
    return jsonNodeList;
  }

}
