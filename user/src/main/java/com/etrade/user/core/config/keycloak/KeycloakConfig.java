package com.etrade.user.core.config.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;


public class KeycloakConfig {
    static Keycloak keycloak = null;
    public static String serverUrl = "http://localhost:8181/";
    public static String realm = "bp-etrade";
    public static String clientId = "bp-etrade-gateway";
    public static String clientSecret = "VCkgjZEIZHZ1l3ILu8R9P6mhwtZV8PhE";
    public static String adminUsername = "enes";
    public static String adminPassword = "1999"; //not a good place to set it lol


    public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){

            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(adminUsername)
                    .password(adminPassword)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilder()
                            .connectionPoolSize(10)
                            .build()
                                   )
                    .build();
        }
        return keycloak;
    }
}
