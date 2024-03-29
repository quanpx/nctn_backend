package quanphung.hust.nctnbackend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import quanphung.hust.nctnbackend.domain.UserInfo;
import quanphung.hust.nctnbackend.dto.request.LoginRequest;
import quanphung.hust.nctnbackend.dto.request.SignUpRequest;
import quanphung.hust.nctnbackend.dto.response.AuthResponse;
import quanphung.hust.nctnbackend.dto.response.GetLotResponse;
import quanphung.hust.nctnbackend.dto.response.RoleResponse;
import quanphung.hust.nctnbackend.dto.response.YourItemsResponse;

public interface UserService extends UserDetailsService
{
 AuthResponse login(LoginRequest request);

  AuthResponse signup(SignUpRequest request);

  RoleResponse getRoles();

  GetLotResponse getFavorites();

  UserInfo getUserbyUsername(String username);

  YourItemsResponse getYourItems();
}
