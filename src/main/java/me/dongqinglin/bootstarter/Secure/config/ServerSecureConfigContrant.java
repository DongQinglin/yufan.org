package me.dongqinglin.bootstarter.Secure.config;

import me.dongqinglin.bootstarter.global.config.ServerConfigContrant;

public class ServerSecureConfigContrant {
    public static final String anyUrl = ServerConfigContrant.PREFIX_URL + "/any/**" ;
    public static final String userUrl = ServerConfigContrant.PREFIX_URL + "/user/**" ;
    public static final String adminUrl = ServerConfigContrant.PREFIX_URL + "/admin/**" ;
    public static final String coderUrl = ServerConfigContrant.PREFIX_URL +  "/coder/**" ;

    public static final String CODE = "202153";
}
