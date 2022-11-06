package com.etrade.user.service;

import com.etrade.user.core.config.keycloak.Credentials;
import com.etrade.user.core.config.keycloak.KeycloakConfig;
import com.etrade.user.core.constants.UserRoles;
import com.etrade.user.core.result.*;
import com.etrade.user.dto.LoginRequest;
import com.etrade.user.dto.LoginResponse;

import com.etrade.user.dto.RegisterRequest;

import com.etrade.user.model.User;
import com.etrade.user.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.*;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Value("${app.keycloak.login.url}")
    private String loginUrl;
    @Value("${app.keycloak.client-secret}")
    private String clientSecret;
    @Value("${app.keycloak.grant-type}")
    private String grantType;
    @Value("${app.keycloak.client-id}")
    private String clientId;
    private final WebClient.Builder webClientBuilder;
    private final UserRepository userRepository;

    public UserServiceImpl(WebClient.Builder webClientBuilder, UserRepository userRepository) {
        this.webClientBuilder = webClientBuilder;
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) { //TODO WEBCLIENT
        LoginResponse response = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build()
                .post()
                .uri(loginUrl,
                        uriBuilder -> uriBuilder.build())
                .body(BodyInserters.fromFormData("username", loginRequest.getUsername())
                        .with("password", loginRequest.getPassword())
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("grant_type", grantType))
                .retrieve()
                .bodyToMono(LoginResponse.class)
                .block();
        response.setRole(getUserRoleFromRequest(response.getAccess_token()));
        response.setName(getUserNameFromToken(response.getAccess_token()));
        return response;
    }

    @Override
    public Result register(RegisterRequest registerRequest) {
        boolean mongoResult = addUserToMongo(registerRequest);
        if(!mongoResult){
            return new ErrorResult("There is already a user with this e-mail. Please change your information.");
        }
        boolean keycloakResult = addUserToKeycloak(registerRequest);
        if(!keycloakResult){
            return new ErrorResult("There is a problem with your information. Please check your input.");
        }
        return new SuccessResult("You have registered successfully.");
    }

    private boolean addUserToMongo(RegisterRequest registerRequest){
        User user = User.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getFirstname())
                .lastName(registerRequest.getLastName())
                .birthDate(registerRequest.getBirthDate())
                .gender(registerRequest.getGender())
                .address(registerRequest.getAddress())
                .prefers(registerRequest.getPrefers())
                .build();
        try {
            this.userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean addUserToKeycloak(RegisterRequest registerRequest) {
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(registerRequest.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerRequest.getUserName());
        user.setFirstName(registerRequest.getFirstname());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        UsersResource instance = getInstance();
        Response response =  instance.create(user);
        if(response.getStatus() != 201){
            return false;
        }

        List<UserRepresentation> userList = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users().search(registerRequest.getUserName()).stream()
                .filter(userRep -> userRep.getUsername().equals(registerRequest.getUserName())).collect(Collectors.toList());
        user = userList.get(0);
        this.assignRoleToUser(user.getId(), "customer");
        return true;
    }

    private void assignRoleToUser(String userId, String role) {
        UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
        UserResource userResource = usersResource.get(userId);
        ClientRepresentation clientRepresentation = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).clients().findAll().stream().filter(client -> client.getClientId().equals(clientId)).collect(Collectors.toList()).get(0);
        ClientResource clientResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).clients().get(clientRepresentation.getId());
        RoleRepresentation roleRepresentation = clientResource.roles().list().stream().filter(element -> element.getName().equals(role)).collect(Collectors.toList()).get(0);
        userResource.roles().clientLevel(clientRepresentation.getId()).add(Collections.singletonList(roleRepresentation));
    }

    private UsersResource getInstance(){
        return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
    }

    private String getUserRoleFromRequest(String token){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(token.split("\\.")[1]));
        JSONObject obj = new JSONObject(payload);
        JSONArray roles = obj.getJSONObject("resource_access").getJSONObject("bp-etrade-gateway").getJSONArray("roles");
        for (int i=0; i< roles.length(); i++){
            if(roles.getString(i).equals(UserRoles.ADMIN)){
                return UserRoles.ADMIN;
            }
        }
        return UserRoles.CUSTOMER;
    }

    private String getUserNameFromToken(String token){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(token.split("\\.")[1]));
        JSONObject obj = new JSONObject(payload);

        return obj.getString("name");
    }


}
