package quanphung.hust.nctnbackend.config.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.domain.Role;
import quanphung.hust.nctnbackend.security.CustomUserDetails;

@Component
@Slf4j
public class JwtTokenProvider
{
  @Value("${jwt.secretKey}")
  private String secret;

  @Value("${jwt.expirationDateInMs}")
  private Long expirationDateInMs;

  @Value("${jwt.refreshExpirationDateInMs}")
  private Long refreshExpirationDateInMs;

  public String generateToken(CustomUserDetails userDetails)
  {
    Map<String, Object> claims = new HashMap<>();

    List<Role> roles = new ArrayList<Role>((Collection<? extends Role>)userDetails.getAuthorities());

    if (roles.contains(new Role("admin")))
    {
      claims.put("isAdmin", true);
    }
    if (roles.contains(new Role("user")))
    {
      claims.put("isUser", true);
    }

    return doGenerateToken(claims, userDetails.getUsername());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject)
  {
    try
    {
       return Jwts.builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + expirationDateInMs))
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
      return null;
    }

  }

  public boolean validateToken(String token)
  {

    try
    {
      Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token);
      return true;
    }
    catch (MalformedJwtException | IllegalArgumentException | UnsupportedJwtException | ExpiredJwtException ex)
    {
      log.warn(ex.getMessage());
    }
    return false;

  }

  public Optional<String> getUsernameFromToken(String token)
  {
    try
    {
      Claims claims = Jwts.parser().
        setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();

      String username = claims.getSubject();
      return Optional.of(username);

    }
    catch (Exception e)
    {
      log.error(e.getMessage());
      return Optional.empty();
    }

  }

  public String doGenerateRefreshToken(Map<String, Object> claims, String subject)
  {

    return Jwts.builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact();

  }
  public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    List<SimpleGrantedAuthority> roles = null;

    Boolean isAdmin = claims.get("isAdmin", Boolean.class);
    Boolean isUser = claims.get("isUser", Boolean.class);

    if (isAdmin != null && isAdmin) {
      roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    if (isUser != null && isAdmin) {
      roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    return roles;

  }
}
