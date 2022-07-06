package quanphung.hust.nctnbackend.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import quanphung.hust.nctnbackend.config.jwt.JwtTokenProvider;
import quanphung.hust.nctnbackend.domain.UserInfo;
import quanphung.hust.nctnbackend.dto.request.LoginRequest;
import quanphung.hust.nctnbackend.dto.request.SignUpRequest;
import quanphung.hust.nctnbackend.dto.response.AuthResponse;
import quanphung.hust.nctnbackend.repository.UserInfoRepository;
import quanphung.hust.nctnbackend.security.CustomUserDetails;

@Service
@Slf4j
public class UserServiceImpl implements UserService
{

  @Value("${jwt.expirationDateInMs}")
  private Long expiresToken;

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public AuthResponse login(LoginRequest request)
  {
    AuthResponse response;
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

    String token = jwtTokenProvider.generateToken(userDetails);
    response = AuthResponse.builder()
      .accessToken(token)
      .expiresIn(expiresToken)
      .build();
    return response;
  }

  @Override
  @Transactional
  public AuthResponse signup(SignUpRequest request)

  {
    AuthResponse response = null;
    String username = request.getUsername();
    boolean checkUserExist = checkUserExisted(username);
    UserInfo userInfo = UserInfo
      .builder()
      .email(request.getEmail())
      .username(request.getUsername())
      .firstName(request.getFirstName())
      .password(passwordEncoder.encode(request.getPassword()))
      .lastName(request.getLastName())
      .phone(request.getPhone())
      .build();

    if (!checkUserExist)
    {
      userInfoRepository.save(userInfo);
    }
    else
    {
      response = AuthResponse.builder().error("User existed!").build();
    }

    return response;

  }

  private boolean checkUserExisted(String username)
  {
    Optional<UserInfo> userInfo = userInfoRepository.findUserInfoByUsername(username);
    return userInfo.isPresent();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
  {
    Optional<UserInfo> userInfo = userInfoRepository.findUserInfoByUsername(username);
    if (userInfo.isEmpty())
    {
      throw new UsernameNotFoundException("Username not available!");
    }
    return new CustomUserDetails(userInfo.get());
  }
}
