package com.Nhom19.shopQuanAo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINTS = {"/auth/login","users","users/{userId}","products","brands","products/type"
    };
    @Value("${jwt.signerKey}")
    private String jwtSignerKey;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll().
                        requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS).permitAll().
                        requestMatchers(HttpMethod.DELETE,PUBLIC_ENDPOINTS).permitAll().
                        requestMatchers(HttpMethod.PUT,PUBLIC_ENDPOINTS).permitAll()
//                      requestMatchers(HttpMethod.GET,"/users").hasAuthority("SCOPE_ADMIN")
              .anyRequest().authenticated());
        httpSecurity.oauth2ResourceServer(ouath2-> ouath2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);


        return httpSecurity.build();
    }
    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec= new SecretKeySpec(jwtSignerKey.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}

