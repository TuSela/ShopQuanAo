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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINTS = {"/auth/login","users","users/{userId}","products"

            ,"products/type","home","products/{maSp}","home/{maSp}","/types","/colors","/sizes"
            ,"files","files/images","/orders","products/search","types/nav-menu","products/categories","products/product-list","variants/{maSp}"
            ,"products/type","home","products/{maSp}","home/{maSp}","/types/**","/colors/**","/sizes/**"
            ,"files","files/images","/orders"
    };

    private final String[] PUBLIC_Img = {"/avatar/**","/image/**","/public/**"
    };
    @Value("${jwt.signerKey}")
    private String jwtSignerKey;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource())) // BẮT BUỘC
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll().
                        requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS).permitAll().
                        requestMatchers(HttpMethod.DELETE,PUBLIC_ENDPOINTS).permitAll().
                        requestMatchers(HttpMethod.PUT,PUBLIC_ENDPOINTS).permitAll().
                        requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(PUBLIC_Img).permitAll()
              .anyRequest().authenticated());
        httpSecurity.oauth2ResourceServer(ouath2-> ouath2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec= new SecretKeySpec(jwtSignerKey.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}

