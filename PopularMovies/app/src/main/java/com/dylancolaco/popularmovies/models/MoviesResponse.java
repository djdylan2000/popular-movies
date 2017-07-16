package com.dylancolaco.popularmovies.models;

import java.util.List;

/**
 * Created by Dylan on 16/07/17.
 */

public class MoviesResponse {
    Integer page;
    Integer total_results;
    Integer total_pages;
    List<Movie> results;

    public MoviesResponse() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
