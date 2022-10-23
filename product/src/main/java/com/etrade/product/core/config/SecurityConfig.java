package com.etrade.product.core.config;

import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

@Override
protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.csrf().disable().cors().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET,"/api/product/test2").hasRole("customer")
            .antMatchers(HttpMethod.POST,"/api/product/add/**").hasRole("admin")
            .antMatchers(HttpMethod.DELETE,"/api/product/delete/**").hasRole("admin")
            .antMatchers(HttpMethod.PUT,"/api/product/update/**").hasRole("admin")
            .antMatchers(HttpMethod.GET,"/api/product/list/**").permitAll()
            .antMatchers(HttpMethod.PUT,"/api/product/add-link/**").hasRole("admin")
            .antMatchers(HttpMethod.PUT,"/api/product//add-stock/**").hasRole("admin")
            .antMatchers(HttpMethod.PUT,"/api/product/discount/**").hasRole("admin")
            .anyRequest()
            .authenticated();

}

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    @Override
    protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
        KeycloakAuthenticationProcessingFilter filter = new KeycloakAuthenticationProcessingFilter(this.authenticationManagerBean());
        filter.setSessionAuthenticationStrategy(this.sessionAuthenticationStrategy());
        return filter;
    }

}
