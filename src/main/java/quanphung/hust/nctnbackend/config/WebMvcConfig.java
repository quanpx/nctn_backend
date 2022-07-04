package quanphung.hust.nctnbackend.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer
{

  private static final String[] ALLOWED_ORIGINS = { "*" };

  private static final String[] ALLOWED_METHODS = { "OPTIONS", "GET", "POST", "PUT", "DELETE", "PATCH", "HEAD" };

  private static final String[] ALLOWED_HEADERS = { HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION };

  @Override
  public void addCorsMappings(CorsRegistry registry)
  {
    registry.addMapping("/**")
      .allowedOrigins(ALLOWED_ORIGINS)
      .allowedHeaders(ALLOWED_HEADERS)
      .allowedMethods(ALLOWED_METHODS);
  }

}
