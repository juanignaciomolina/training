package ar.com.wolox.woloxtrainingmolina;

import ar.com.wolox.woloxtrainingmolina.entities.Usuario;
import retrofit.http.GET;
import retrofit.http.Query;

public interface LogInService {
    @GET(Config.PARSE_LOGIN)
    void logIn(@Query("username") String email, @Query("password") String password, retrofit.Callback<Usuario> usuarioCallback); //En esta app el username es el mail
}
