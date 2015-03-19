package ar.com.wolox.woloxtrainingmolina.entities;

import java.util.Date;

public class Usuario {
    //Los nombres de las variables tienen que ser si o si así para que matcheen con el servidor
    // y que Retrofit aproveche GSON para crear la instancia de usuario
    public String objectId;
    public String username;
    public String password;
    public String sessionToken;
    public String email;
    public String emailVerified;
    public String cover;
    public String description;
    public String location;
    public String name;
    public String phone;
    public String picture;
    public String createdAt;
    public String updatedAt;
}
