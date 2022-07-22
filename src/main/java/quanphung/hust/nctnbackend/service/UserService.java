package quanphung.hust.nctnbackend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import quanphung.hust.nctnbackend.dto.request.LoginRequest;
import quanphung.hust.nctnbackend.dto.request.SignUpRequest;
import quanphung.hust.nctnbackend.dto.response.AuthResponse;
import quanphung.hust.nctnbackend.dto.response.RoleResponse;

public interface UserService extends UserDetailsService
{
 AuthResponse login(LoginRequest request);

  AuthResponse signup(SignUpRequest request);

  RoleResponse getRoles();
}
