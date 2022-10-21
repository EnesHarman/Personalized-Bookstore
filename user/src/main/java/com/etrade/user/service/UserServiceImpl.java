package com.etrade.user.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService{

    private final WebClient.Builder webClientBuilder;

    public UserServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public String test() {
       return webClientBuilder.build().get()
                .uri("http://product-service/product/test",
                        uriBuilder -> uriBuilder.build())
                .retrieve()
                .bodyToMono(String.class)
        .block();
    }
}
