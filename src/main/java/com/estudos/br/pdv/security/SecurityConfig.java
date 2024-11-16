package com.estudos.br.pdv.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService service;

    private final JWTService jwtService;

    // http://localhost:8080/swagger-ui/index.html
    private static final String[] AUTH = { "/api/pessoas/**",
            "/login", "/sign-up", "/v3/api-docs/**",
            "/swagger-ui/**", "/swagger-ui.html","/swagger-resources/**", "/webjars/**" };

    @Bean
    public static PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/users").permitAll()
                        .requestMatchers(AUTH).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults()) // Use a configuração padrão para autenticação básica
                .sessionManagement((session)-> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable); // Desabilita CSRF

        return http.build();
    }

    public OncePerRequestFilter jwtFilter() {
        return new JWTAuthFilter(jwtService, service);
    }

}
