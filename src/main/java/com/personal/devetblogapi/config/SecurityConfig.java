package com.personal.devetblogapi.config;

import com.personal.devetblogapi.constant.AppEndpoint;
import com.personal.devetblogapi.constant.CrossOriginUrls;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
        (requests) ->
            requests
                .requestMatchers(AppEndpoint.PUBLIC_LIST)
                .permitAll()
                .requestMatchers(HttpMethod.GET, AppEndpoint.PUBLIC_LIST_GET)
                .permitAll()
                .anyRequest()
                .authenticated());

    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    http.logout(
        (logout) -> {
          logout.logoutUrl("/api/v1/auth/logout").addLogoutHandler(logoutHandler);
          logout.logoutSuccessHandler(
              (req, res, authentication) -> SecurityContextHolder.clearContext());
          logout.permitAll();
        });
    http.csrf(AbstractHttpConfigurer::disable);
    http.cors(Customizer.withDefaults());
    //    http.httpBasic(Customizer.withDefaults());
    return http.build();
  }

  //  TODO: Config CORS
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin(CrossOriginUrls.WEB_DEV);
    config.addAllowedOrigin(CrossOriginUrls.WEB_PROD);
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.setMaxAge(3600L);
    source.registerCorsConfiguration("/api/**", config);
    return new CorsFilter(source);
  }
}
