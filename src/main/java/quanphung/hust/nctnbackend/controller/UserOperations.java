package quanphung.hust.nctnbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import quanphung.hust.nctnbackend.dto.request.LoginRequest;
import quanphung.hust.nctnbackend.dto.request.SignUpRequest;
import quanphung.hust.nctnbackend.dto.response.AuthResponse;

@RequestMapping(UserOperations.API_RESOURCE)
public interface UserOperations
{
  String API_RESOURCE = "/api";

  String LOGIN = "/login";
  String LOGOUT = "/logout";
  String SIGNUP = "/signup";

  @PostMapping(LOGIN)
  ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request);

  @PostMapping(SIGNUP)
  ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest request);

}
