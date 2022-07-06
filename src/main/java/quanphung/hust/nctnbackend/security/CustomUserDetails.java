package quanphung.hust.nctnbackend.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import quanphung.hust.nctnbackend.domain.UserInfo;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails
{
  UserInfo userInfo;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword()
  {
    return userInfo.getPassword();
  }

  @Override
  public String getUsername()
  {
    return userInfo.getUsername();
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return true;
  }
}
