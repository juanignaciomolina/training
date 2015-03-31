package ar.com.wolox.woloxtrainingmolina.api;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.entities.User;
import retrofit.http.GET;

public interface LogInSessionService {
    @GET(Config.PARSE_ME)
    void sessionLogIn(retrofit.Callback<User> userCallback);
}
