package ar.com.wolox.woloxtrainingmolina;

import ar.com.wolox.woloxtrainingmolina.annotations.GET;
import ar.com.wolox.woloxtrainingmolina.entitites.Usuario;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;

public interface LogInService {
    @FormUrlEncoded
    @GET(Config.PARSE_LOGIN)
    Usuario logIn(@Field("username") String username, @Field("password") String password);
}
