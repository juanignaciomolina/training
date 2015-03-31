package ar.com.wolox.woloxtrainingmolina.api;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.entities.News;
import retrofit.http.GET;
import retrofit.http.Query;

public interface NewsService {
    @GET(Config.PARSE_NEWS)
    void getNews(@Query("skip") String from,
               @Query("limit") String to,
               retrofit.Callback<News> newsCallback);
}
