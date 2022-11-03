package com.etrade.user.core.config.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;


public class KeycloakConfig {
    static Keycloak keycloak = null;
    @Value(value = "${app.keycloak.server-url}")
    public static String serverUrl;
    @Value(value = "${app.keycloak.realm}")
    public static String realm;
    @Value(value = "${app.keycloak.client-id}")
    public static String clientId;
    @Value(value = "${app.keycloak.clientSecret}")
    public static String clientSecret;
    @Value(value = "${app.keycloak.clientSecret}")
    public static String adminUsername;
    @Value(value = "${app.keycloak.clientSecret}")
    public static String adminPassword;


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
