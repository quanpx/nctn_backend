package quanphung.hust.nctnbackend.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Component
public class WebMvcConfig implements WebMvcConfigurer
{

  private static final String[] ALLOWED_ORIGINS = { "http://localhost:3000" };

  private static final String[] ALLOWED_METHODS = { "OPTIONS", "GET", "POST", "PUT", "DELETE", "PATCH", "HEAD" };

  private static final String[] ALLOWED_HEADERS = { HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION };

  @Override
  public void addCorsMappings(CorsRegistry registry)
  {
    registry.addMapping("/**")
      .allowCredentials(true)
      .allowedOrigins(ALLOWED_ORIGINS)
      .allowedHeaders(ALLOWED_HEADERS)
      .allowedMethods(ALLOWED_METHODS);
  }

  @Bean(name = "localeResolver")
  public LocaleResolver getLocaleResolver()  {
    CookieLocaleResolver resolver= new CookieLocaleResolver();
    resolver.setCookieDomain("myAppLocaleCookie");
    resolver.setDefaultLocale(Locale.ENGLISH);
    // 60 minutes
    resolver.setCookieMaxAge(60*60);
    return resolver;
  }

  @Bean(name = "messageSource")
  public MessageSource getMessageResource()  {
    ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();

    // Đọc vào file i18n/messages_xxx.properties
    // Ví dụ: i18n/messages_en.properties
    messageResource.setBasename("classpath:i18n/messages");
    messageResource.setDefaultEncoding("UTF-8");
    return messageResource;
  }
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
    localeInterceptor.setParamName("language");
    registry.addInterceptor(localeInterceptor).addPathPatterns("/*");
  }


}
