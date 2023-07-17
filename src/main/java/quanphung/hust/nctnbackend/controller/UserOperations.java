package quanphung.hust.nctnbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import quanphung.hust.nctnbackend.dto.request.LoginRequest;
import quanphung.hust.nctnbackend.dto.request.SignUpRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionDetailResponse;
import quanphung.hust.nctnbackend.dto.response.AuthResponse;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.GetLotResponse;
import quanphung.hust.nctnbackend.dto.response.RoleResponse;
import quanphung.hust.nctnbackend.dto.response.YourItemsResponse;

@RequestMapping(UserOperations.API_RESOURCE)
public interface UserOperations
{
  String API_RESOURCE = "/api";

  String LOGIN = "/login";

  String LOGOUT = "/logout";
  String SIGNUP = "/signup";
  String ROLES ="/roles";

  String ITEMS = "/items";

  String FAVORITES="/favorites";
  @PostMapping(LOGIN)
  ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request);

  @PostMapping(SIGNUP)
  ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest request);

  @GetMapping(ROLES)
  ResponseEntity<RoleResponse> getRoles();


  ResponseEntity<BidResponse> getAllItems();
  @GetMapping(FAVORITES)
  ResponseEntity<GetLotResponse> getFavorites();

  @GetMapping(ITEMS)
  ResponseEntity<YourItemsResponse> GetYourItems();

  @PostMapping(ROLES)
  void createRole();

}
