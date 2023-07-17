package quanphung.hust.nctnbackend.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import quanphung.hust.nctnbackend.domain.Role;
import quanphung.hust.nctnbackend.security.CustomUserDetails;
import quanphung.hust.nctnbackend.type.UserRole;

public final class SecurityUtils
{

  private SecurityUtils()
  {
  }

  /**
   * Get username of current logged user.
   *
   * @return current username if user has logged. Otherwise return {@link Optional#empty()}
   */
  public static Optional<String> getCurrentUsername()
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getPrincipal() == null)
    {
      return Optional.empty();
    }

    // Resolve username depend on type of principal.
    Object principal = authentication.getPrincipal();
    if (principal instanceof String)
    {
      return Optional.of(principal.toString());
    }
    else if (principal instanceof CustomUserDetails)
    {
      CustomUserDetails userDetails = (CustomUserDetails)principal;
      return Optional.of(userDetails.getUsername());
    }
    else
    {
      return Optional.empty();
    }
  }
  public static Optional<String> getCurrentEmail()
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getPrincipal() == null)
    {
      return Optional.empty();
    }

    // Resolve username depend on type of principal.
    Object principal = authentication.getPrincipal();
    if (principal instanceof String)
    {
      return Optional.of(principal.toString());
    }
    else if (principal instanceof CustomUserDetails)
    {
      CustomUserDetails userDetails = (CustomUserDetails)principal;
      return Optional.of(userDetails.getUserInfo().getEmail());
    }
    else
    {
      return Optional.empty();
    }
  }
  /**
   * @return
   */
  @SuppressWarnings("unchecked")
  public static Collection<Role> getAuthorities()
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getPrincipal() == null)
    {
      return Collections.emptyList();
    }

    return (Collection<Role>)authentication.getAuthorities();
  }

  public static boolean isVdAdmin()
  {
    // Allow VD Admin can abort the session
    Collection<Role> authorities = getAuthorities();
    for (Role authority : authorities)
    {
      if (UserRole.SYSTEM_ADMIN.getValue().equals(authority.getAuthority()))
      {
        return true;
      }
    }

    return false;
  }

  private static Optional<String> resolveUsernameFromJwt(Jwt jwt)
  {
    Object preferredUsername = jwt.getClaims().get("preferred_username");

    if (preferredUsername != null)
    {
      return Optional.of(preferredUsername.toString());
    }
    else
    {
      return Optional.empty();
    }
  }
}
