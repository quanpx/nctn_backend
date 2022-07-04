package quanphung.hust.nctnbackend.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import quanphung.hust.nctnbackend.utils.SecurityUtils;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig
{

  public static final String SYSTEM_NAME = "nctn-server";

  @Bean
  public AuditorAware<String> auditorProvider()
  {
    return new AuditorAwareImpl();
  }

  static class AuditorAwareImpl implements AuditorAware<String>
  {

    @Override
    public Optional<String> getCurrentAuditor()
    {
      Optional<String> currentUsername = SecurityUtils.getCurrentUsername();
      if (currentUsername.isPresent())
      {
        return currentUsername;
      }
      else
      {
        return Optional.of(SYSTEM_NAME);
      }
    }

  }

}
