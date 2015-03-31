package ar.com.wolox.woloxtrainingmolina.api;

import ar.com.wolox.woloxtrainingmolina.entities.News;

public class NewsRequestAdapter {

    private News[] results;

    public News[] getResults() {
        return results;
    }

    public void setResults(News[] results) {
        this.results = results;
    }
}
