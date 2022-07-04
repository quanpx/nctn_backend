package quanphung.hust.nctnbackend.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

/**
 * @author Son Nguyen
 */
@Component
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>>
{
  private static final String ROLES = "roles";

  private static final String RESOURCE_ACCESS = "realm_access";

  private final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

  @SuppressWarnings("unchecked")
  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt)
  {
    if (jwt != null)
    {
      Map<String, Object> keycloakClientAuthorities = jwt.getClaimAsMap(RESOURCE_ACCESS);
      if (keycloakClientAuthorities != null)
      {
        List<String> clientRoles = (List<String>)keycloakClientAuthorities.get(ROLES);
        if (clientRoles != null)
        {
          Collection<GrantedAuthority> clientAuthorities = clientRoles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

          clientAuthorities.addAll(jwtGrantedAuthoritiesConverter.convert(jwt));

          return clientAuthorities;
        }
      }

    }

    return jwtGrantedAuthoritiesConverter.convert(jwt);
  }
}
