package me.dongqinglin.flina.rest.middleware.jwt;

import me.dongqinglin.flina.rest.middleware.tomcat.ServerConfigContrant;

public class JwtConfig {
    public static final String STAR = "*" ;

    public static final String ANY_URL_CONFIG = JwtConfig.ANY_URL_PREFIX + STAR + STAR;
    public static final String AUTH_URL_CONFIG = JwtConfig.AUTH_URL_PREFIX + STAR + STAR;

    public static final String ANY_URL_PREFIX =  ServerConfigContrant.FLINA_1_0 + "any/";
    public static final String AUTH_URL_PREFIX =  ServerConfigContrant.FLINA_1_0 + "auth/";
}

