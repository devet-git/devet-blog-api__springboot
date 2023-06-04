package com.personal.devetblogapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final String[] AUTH_WHITE_LIST = {
    "/",
    "/api/v1/auth/**",
    "/api/v1/files/images/**",
    "/api/v1/files/pdf/**",
    "/api/v1/files/data/**",
    "/swagger-ui/index.html/**",
    "/v3/api-docs/**"
  };
  //  @Autowired private final LogoutHandler logoutHandler;
  //  @Autowired private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        (reqs) -> {
          //          reqs.requestMatchers(AUTH_WHITE_LIST).permitAll(); // .hasAuthority()
          reqs.anyRequest().permitAll();
        });
    //    http.authenticationProvider(authenticationProvider); // .addFilterBefore();
    http.logout(
        (logout) -> {
          logout.logoutUrl("/auth/logout"); // .addLogoutHandler(logoutHandler);
          logout.logoutSuccessHandler(
              (req, res, authentication) -> SecurityContextHolder.clearContext());
        });
    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.csrf(AbstractHttpConfigurer::disable);
    //    http.httpBasic(Customizer.withDefaults());
    return http.build();
  }
}
