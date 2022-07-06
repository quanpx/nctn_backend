package quanphung.hust.nctnbackend.config.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.service.UserService;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter
{
  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException
  {
    try
    {
      String token = extractJwtFromRequest(request);
      if (!jwtTokenProvider.validateToken(token))
      {
        filterChain.doFilter(request, response);
        return;
      }
      Optional<String> usernameOpt = jwtTokenProvider.getUsernameFromToken(token);
      if (usernameOpt.isEmpty())
      {
        filterChain.doFilter(request, response);
        return;
      }
      UserDetails userDetails = userService.loadUserByUsername(usernameOpt.get());

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities());

      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);
    }
    catch (Exception e)
    {
      //TODO: handle exceptioncredentials
      log.warn(e.getMessage());
      filterChain.doFilter(request, response);
    }

  }

  private String extractJwtFromRequest(HttpServletRequest request)
  {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
    {
      return bearerToken.substring(7);
    }
    return null;
  }
}
