import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import quanphung.hust.nctnbackend.domain.Role;
import quanphung.hust.nctnbackend.repository.RoleRepository;
import quanphung.hust.nctnbackend.type.UserRole;

@Component
public class InitRole implements CommandLineRunner
{
  @Autowired
  private RoleRepository roleRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception
  {
    System.out.printf("run");
    for (UserRole role : UserRole.values())
    {
      Role r = Role.builder()
        .name(role.getValue())
        .build();

      r = roleRepository.save(r);
    }
  }
}
