package com.pikecape.springboot.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {
  @Value("${github.client-id}")
  private String githubClientId;

  @Value("${github.client-secret}")
  private String githubClientSecret;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(requests -> requests
        .requestMatchers("/api/duey", "/api/huey").authenticated()
        .requestMatchers("/api/luey").permitAll());

    httpSecurity.formLogin(Customizer.withDefaults());
    httpSecurity.oauth2Login(Customizer.withDefaults());

    return httpSecurity.build();
  }

  @Bean
  ClientRegistrationRepository clientRegistrationRepository() {
    ClientRegistration github = githubClientRegistration();
    return new InMemoryClientRegistrationRepository(github); //, facebook);
  }

  private ClientRegistration githubClientRegistration() {
    return CommonOAuth2Provider.GITHUB.getBuilder("github")
        .clientId(githubClientId)
        .clientSecret(githubClientSecret)
        .build();
  }
}
