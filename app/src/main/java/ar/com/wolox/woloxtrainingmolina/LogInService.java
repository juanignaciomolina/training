package ar.com.wolox.woloxtrainingmolina;


import ar.com.wolox.woloxtrainingmolina.annotations.GET;
import ar.com.wolox.woloxtrainingmolina.annotations.Path;
import ar.com.wolox.woloxtrainingmolina.entitites.Usuario;

public interface LogInService {
    @GET("/login/{user}/repos")
    Usuario getUser(@Path("user") String user);
}
