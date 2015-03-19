package ar.com.wolox.woloxtrainingmolina;

import javax.security.auth.callback.Callback;

import ar.com.wolox.woloxtrainingmolina.entitites.Usuario;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

public interface LogInService {
    @GET(Config.PARSE_LOGIN)
    void logIn(@Query("username") String email, @Query("password") String password, retrofit.Callback<HTTPResponse> httpResponseCallback); //En esta app el username es el mail
}
