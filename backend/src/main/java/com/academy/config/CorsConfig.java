package com.academy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:3001,http://localhost:3002}")
  private String[] allowedOrigins;

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    for (String origin : allowedOrigins) {
      config.addAllowedOrigin(origin);
    }
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.setMaxAge(3600L);
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}

