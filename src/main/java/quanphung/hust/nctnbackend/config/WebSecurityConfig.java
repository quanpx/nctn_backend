package quanphung.hust.nctnbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import quanphung.hust.nctnbackend.security.KeycloakJwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  private KeycloakJwtAuthenticationConverter authenticationConverter;

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http
      .cors()
      .and()
      .csrf().disable()
      .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
      .authorizeRequests()
      .antMatchers("/", "/home").permitAll()
      .anyRequest().authenticated()
      .and()
      .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
        jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
  }

    @Bean
    JwtDecoder jwtDecoder(KeycloakConfig keycloakConfig)
    {
      NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(KeycloakConfig.ISSUER_URI);
      OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(KeycloakConfig.ISSUER_URI);
      jwtDecoder.setJwtValidator(withIssuer);
      return jwtDecoder;
    }

  private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter()
  {
    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    jwtConverter.setJwtGrantedAuthoritiesConverter(authenticationConverter);

    return jwtConverter;
  }
}
