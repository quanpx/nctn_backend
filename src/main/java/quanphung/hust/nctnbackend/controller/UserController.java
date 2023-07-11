package quanphung.hust.nctnbackend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.apache.catalina.security.SecurityUtil;
import quanphung.hust.nctnbackend.domain.LikedItem;
import quanphung.hust.nctnbackend.domain.Role;
import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.request.LoginRequest;
import quanphung.hust.nctnbackend.dto.request.SearchBidRequest;
import quanphung.hust.nctnbackend.dto.request.SignUpRequest;
import quanphung.hust.nctnbackend.dto.response.AuthResponse;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.GetLotResponse;
import quanphung.hust.nctnbackend.dto.response.RoleResponse;
import quanphung.hust.nctnbackend.mapping.LotMapping;
import quanphung.hust.nctnbackend.repository.LikedItemRepository;
import quanphung.hust.nctnbackend.repository.RoleRepository;
import quanphung.hust.nctnbackend.service.BidService;
import quanphung.hust.nctnbackend.service.UserService;
import quanphung.hust.nctnbackend.type.UserRole;
import quanphung.hust.nctnbackend.utils.SecurityUtils;

@RestController
public class UserController implements UserOperations
{

  @Autowired
  private UserService userService;

  @Autowired
  private BidService bidService;

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
  public ResponseEntity<BidResponse> getAllItems()
  {
    String userName = SecurityUtils.getCurrentUsername().orElse(null);
    SearchBidRequest request = SearchBidRequest.builder()
      .status("win")
      .username(userName)
      .build();
    return ResponseEntity.ok(bidService.getAllBid(request));
  }

  @Override
  public ResponseEntity<GetLotResponse> getFavorites()
  {
    return ResponseEntity.ok(userService.getFavorites());
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
