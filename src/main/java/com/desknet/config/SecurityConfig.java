package com.desknet.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // deshabilitar CSRF (solo para pruebas o APIs REST)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/category/**").permitAll()
                        .requestMatchers("/api/products/**").permitAll()
                        .anyRequest().permitAll()                    // el resto requiere autenticación
                );

        return http.build();
    }
}
