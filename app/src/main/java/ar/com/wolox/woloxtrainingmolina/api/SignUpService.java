package ar.com.wolox.woloxtrainingmolina.api;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.entities.User;
import retrofit.http.Body;
import retrofit.http.POST;

public interface SignUpService {
    @POST(Config.PARSE_USERS)
    void signUp(@Body User user, retrofit.Callback<User> userCallback);
}
