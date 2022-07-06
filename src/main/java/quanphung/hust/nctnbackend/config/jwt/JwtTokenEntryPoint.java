package quanphung.hust.nctnbackend.config.jwt;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenEntryPoint implements AuthenticationEntryPoint, Serializable
{
  private static final long serialVersionUID = 6579569679213333441L;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws IOException
  {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized!");
  }
}
