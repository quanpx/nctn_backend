package quanphung.hust.nctnbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import quanphung.hust.nctnbackend.config.jwt.JwtTokenEntryPoint;
import quanphung.hust.nctnbackend.config.jwt.JwtTokenFilter;
import quanphung.hust.nctnbackend.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  private UserService userService;

  @Autowired
  private JwtTokenFilter jwtTokenFilter;

  @Autowired
  private JwtTokenEntryPoint jwtTokenEntryPoint;

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception
  {
    // TODO Auto-generated method stub
    return super.authenticationManager();
  }

  @Bean
  public PasswordEncoder getPasswordEncoder()
  {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http
      .headers()
      .frameOptions()
      .disable()
      .and()
      .cors()
      .and()
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/api/login", "/api/signup", "/", "/api/sse/*","/nctn-ws/","/nctn-ws/**").permitAll()
      .antMatchers(HttpMethod.POST, "/api/lot").hasAuthority("admin")
      .antMatchers(HttpMethod.POST, "/api/auction").hasAuthority("admin")
      .antMatchers(HttpMethod.POST,"/api/auction/requests","/api/auction/requests/**").hasAuthority("admin")
      .antMatchers(HttpMethod.POST, "/api/bid").hasAuthority("user")
      .antMatchers(HttpMethod.GET, "/api/lot", "/api/lot/{id}").permitAll()
      .antMatchers(HttpMethod.GET, "/api/auction").permitAll()
      .antMatchers(HttpMethod.GET, "/api/auction/{id}").permitAll()
      .antMatchers(HttpMethod.GET, "/api/sse/subscribe").permitAll()
      .anyRequest().authenticated()
      .and()
      .logout()
      .logoutUrl("/api/logout")
      .logoutSuccessUrl("/")
      .and()
      .exceptionHandling().authenticationEntryPoint(jwtTokenEntryPoint).and();

    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.userDetailsService(userService)
      .passwordEncoder(getPasswordEncoder());
  }

  @Bean
  public CorsFilter corsFilter()
  {
    UrlBasedCorsConfigurationSource source =
      new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:3000");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
