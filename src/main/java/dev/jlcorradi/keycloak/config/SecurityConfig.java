package dev.jlcorradi.keycloak.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.ALWAYS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final GrantedAuthoritiesWithPrefixJwtAuthenticationConverter jwtAuthenticationConverter;

  @Bean
  public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(cfg -> cfg.sessionCreationPolicy(ALWAYS))
        .authorizeHttpRequests(cfg ->
            cfg
                .requestMatchers("/api/v1/public").permitAll()
                .requestMatchers("/unauthenticated", "/oauth2/**", "/login/**").permitAll()
                .anyRequest()
                .fullyAuthenticated()
        );

    httpSecurity
        .oauth2Client(withDefaults())
        .oauth2Login(withDefaults())
        .logout((logout) -> logout.logoutSuccessUrl("http://localhost:5173"));

    httpSecurity
        .oauth2ResourceServer(oauth ->
            oauth.jwt(jwtConfigurer ->
                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter))
        );

    return httpSecurity.build();
  }

}
