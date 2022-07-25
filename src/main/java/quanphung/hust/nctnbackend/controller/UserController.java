package quanphung.hust.nctnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import quanphung.hust.nctnbackend.domain.Role;
import quanphung.hust.nctnbackend.dto.request.LoginRequest;
import quanphung.hust.nctnbackend.dto.request.SignUpRequest;
import quanphung.hust.nctnbackend.dto.response.AuthResponse;
import quanphung.hust.nctnbackend.dto.response.RoleResponse;
import quanphung.hust.nctnbackend.repository.RoleRepository;
import quanphung.hust.nctnbackend.service.UserService;
import quanphung.hust.nctnbackend.type.UserRole;

@RestController
public class UserController implements UserOperations
{

  @Autowired
  private UserService userService;

  @Autowired
  private RoleRepository roleRepository;


  @Override
  public ResponseEntity<AuthResponse> login(LoginRequest request)
  {

    return ResponseEntity.ok(userService.login(request));
  }

  @Override
  public ResponseEntity<AuthResponse> signup(SignUpRequest request)
  {
    return ResponseEntity.ok(userService.signup(request));
  }

  @Override
  public ResponseEntity<RoleResponse> getRoles()
  {
    return ResponseEntity.ok(userService.getRoles());
  }

  @Override
  public void createRole() {
    for (UserRole role : UserRole.values())
    {
      Role r = Role.builder()
              .name(role.getValue())
              .build();

      r = roleRepository.save(r);
    }
  }
}
