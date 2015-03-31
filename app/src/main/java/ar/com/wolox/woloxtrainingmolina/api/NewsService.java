package ar.com.wolox.woloxtrainingmolina.api;

import ar.com.wolox.woloxtrainingmolina.Config;
import retrofit.http.GET;
import retrofit.http.Query;

public interface NewsService {
    @GET(Config.PARSE_NEWS)
    void getNews(@Query("skip") int from,
               @Query("limit") int to,
               retrofit.Callback<NewsRequestAdapter> newsRequestAdapterCallback);
}
