package ar.com.wolox.woloxtrainingmolina.entities;

import java.util.Date;

public class User {
    //Los nombres de las variables son de esta forma para que matcheen con el servidor y no tener que
    //definir una namePoliciy específica para usar la notación húngara (mVariable de instancia, sVariable de clase)
    //Se puede crear una name policy, pero agrega complejidad inneccesaria al codigo.
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
    public String code;
    public String error;
}
