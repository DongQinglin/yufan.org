package me.dongqinglin.bootstarter.Secure.converter;

import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;

public class FlinaUserConverter {
    private static final int DEFAULT_LEVEL = 1;
    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final boolean DEFAULT_ENABLE = true;


    public static FlinaUser createFlinaUser(String username, String pass, String email){
        FlinaUser flinaUser = new FlinaUser();
        flinaUser.setUsername(username).setPassword(pass.hashCode()).setEmail(email).setEnable(DEFAULT_ENABLE).setRoles(DEFAULT_ROLE).setLevel(DEFAULT_LEVEL);
        return flinaUser;
    }
}
