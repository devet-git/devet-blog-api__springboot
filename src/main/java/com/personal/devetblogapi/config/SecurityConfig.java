package com.personal.devetblogapi.config;

import com.personal.devetblogapi.constant.AppEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  @Autowired private final LogoutHandler logoutHandler;
  @Autowired private final AuthenticationProvider authenticationProvider;
  @Autowired private final JwtFilter jwtAuthFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        (reqs) -> {
          reqs.requestMatchers(AppEndpoint.WHITE_LIST).permitAll(); // .hasAuthority()
          reqs.anyRequest().permitAll();
        });
    http.authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    http.logout(
        (logout) -> {
          logout.logoutUrl(AppEndpoint.Auth.LOGOUT).addLogoutHandler(logoutHandler);
          logout.logoutSuccessHandler(
              (req, res, authentication) -> SecurityContextHolder.clearContext());
        });
    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.csrf(AbstractHttpConfigurer::disable);
    http.httpBasic(Customizer.withDefaults());
    return http.build();
  }
}
