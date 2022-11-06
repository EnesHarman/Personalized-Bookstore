package com.etrade.apigateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig{
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .csrf().disable()
                .cors().disable()
                .authorizeExchange(exchange ->
                        exchange.pathMatchers("/eureka/**")
                                .permitAll()
                                .pathMatchers("/api/user/login/**")
                                .permitAll()
                                .pathMatchers("/api/user/register/**")
                                .permitAll()
                                .pathMatchers("/api/product/list/**")
                                .permitAll()
                                .pathMatchers("/api/product/filter/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
        return serverHttpSecurity.build();
    }
}

