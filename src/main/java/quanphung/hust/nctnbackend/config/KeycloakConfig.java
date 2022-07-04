package quanphung.hust.nctnbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;

@Data
@Configuration
public class KeycloakConfig
{
  public static final String PASSWORD_GRANT_TYPE = "password";
  public static final String ISSUER_URI ="http://localhost:8080/realms/nctn";


//  @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
//  private String clientId;
//
//  @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
//  private String issuerUri;
//
//  @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
//  private String tokenUri;

  @Bean
  public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }
}
