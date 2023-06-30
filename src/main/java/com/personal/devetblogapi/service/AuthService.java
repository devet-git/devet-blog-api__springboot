package com.personal.devetblogapi.service;

import com.personal.devetblogapi.constant.MessageConst;
import com.personal.devetblogapi.constant.UserRole;
import com.personal.devetblogapi.entity.TokenEntity;
import com.personal.devetblogapi.entity.UserEntity;
import com.personal.devetblogapi.exception.CustomException;
import com.personal.devetblogapi.model.AuthDto;
import com.personal.devetblogapi.model.UserDto;
import com.personal.devetblogapi.repo.TokenRepo;
import com.personal.devetblogapi.repo.UserRepo;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** The type Authentication service. */
@Service
@RequiredArgsConstructor
public class AuthService {
  @Autowired private final UserRepo userRepo;
  @Autowired private final TokenRepo tokenRepo;
  @Autowired private final JwtService jwtService;
  @Autowired private ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  /**
   * Register service
   *
   * @param req the register request body
   * @return the jwt token
   * @throws CustomException the custom exception
   */
  public AuthDto.Response register(AuthDto.Register req) throws CustomException {

    String reqEmail = req.getEmail();
    String reqUsername = req.getUsername();
    String reqPw = req.getPassword();

    UserEntity existedUserByEmail = userRepo.findByEmail(reqEmail).orElse(null);

    if (existedUserByEmail != null) {
      throw new CustomException(
          String.format("%s - %s", reqEmail, MessageConst.existed("User")),
          HttpStatus.BAD_REQUEST,
          null);
    }

    UserEntity user =
        UserEntity.builder()
            .username(reqUsername)
            .email(reqEmail)
            .password(passwordEncoder.encode(reqPw))
            .role(UserRole.USER)
            .createDate(new Date())
            .build();

    UserEntity savedUser = userRepo.save(user);
    String jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);

    return AuthDto.Response.builder()
        .token(jwtToken)
        .user(modelMapper.map(savedUser, UserDto.Response.class))
        .build();
  }

  /**
   * Authentication service
   *
   * @param req the login request body
   * @return the jwt token
   * @throws CustomException the custom exception
   */
  public AuthDto.Response login(AuthDto.Login req) throws CustomException {

    String reqEmail = req.getEmail();
    String reqPw = req.getPassword();
    UserEntity user = userRepo.findByEmail(reqEmail).orElse(null);

    if (user == null || !isPasswordMatches(reqPw, user.getPassword())) {
      throw new CustomException(MessageConst.notExist("User"), HttpStatus.BAD_REQUEST, null);
    }

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqEmail, reqPw));
    String jwtToken = jwtService.generateToken(user);

    revokedAllUserToken(user);
    saveUserToken(user, jwtToken);

    return AuthDto.Response.builder()
        .token(jwtToken)
        .user(modelMapper.map(user, UserDto.Response.class))
        .build();
  }

  private void saveUserToken(UserEntity user, String jwtToken) {
    TokenEntity token =
        TokenEntity.builder()
            .userId(user.getId())
            .token(jwtToken)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepo.save(token);
  }

  private void revokedAllUserToken(UserEntity user) {
    var validUserToken = tokenRepo.findAllValidTokenByUserId(user.getId());
    if (validUserToken.isEmpty()) {
      return;
    }
    validUserToken.forEach(
        token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });

    tokenRepo.saveAll(validUserToken);
  }

  private boolean isPasswordMatches(String rawPw, String encodedPw) {
    return passwordEncoder.matches(rawPw, encodedPw);
  }

  public UserEntity getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return userRepo
        .findByEmail(auth.getName())
        .orElseThrow(
            () -> new CustomException(MessageConst.notExist("User"), HttpStatus.BAD_REQUEST, null));
  }
}
