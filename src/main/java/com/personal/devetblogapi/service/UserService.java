package com.personal.devetblogapi.service;

import com.personal.devetblogapi.constant.MessageConst;
import com.personal.devetblogapi.constant.UserRole;
import com.personal.devetblogapi.entity.UserEntity;
import com.personal.devetblogapi.exception.CustomException;
import com.personal.devetblogapi.model.UserDto;
import com.personal.devetblogapi.repo.UserRepo;
import com.personal.devetblogapi.util.EntityUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  @Autowired private final PasswordEncoder passwordEncoder;
  @Autowired private AuthService authService;
  @Autowired private UserRepo userRepo;
  @Autowired private ModelMapper modelMapper;

  @Cacheable(value = "users")
  public List<UserEntity> getAll(int pageNumber, int pageSize, String sortBy) {
    if (pageNumber <= 0)
      throw new CustomException(
          "pageNumber value must be 1 and more", HttpStatus.BAD_REQUEST, null);
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
    Page<UserEntity> pageResult = userRepo.findAll(pageable);
    return pageResult.getContent();
  }

  @Cacheable(value = "user", key = "#id")
  public UserEntity getById(String userId) {
    return userRepo
        .findById(userId)
        .orElseThrow(
            () -> new CustomException(MessageConst.notExist("User"), HttpStatus.BAD_REQUEST, null));
  }

  public void createOne(UserDto.Create req) {
    String reqEmail = req.getEmail();
    String reqUsername = req.getUsername();
    String reqPw = req.getPassword();
    if (userRepo.existsByEmail(reqEmail)) {
      throw new CustomException(
          String.format("%s - %s", reqEmail, MessageConst.existed("User")),
          HttpStatus.BAD_REQUEST,
          null);
    }

    UserEntity user =
        UserEntity.builder()
            .username(reqUsername)
            .fullName(req.getFullName())
            .email(reqEmail)
            .password(passwordEncoder.encode(reqPw))
            .role(UserRole.USER)
            .create_date(new Date())
            .build();

    userRepo.save(user);
  }

  @CachePut(value = "updatedUser", key = "#userId")
  public void updateById(String userId, UserDto.Update req) {
    UserEntity updatedUser = getById(userId);

    updatedUser.setUsername(req.getUsername());
    updatedUser.setFullName(req.getFullName());
    updatedUser.setEmail(req.getEmail());
    updatedUser.setUpdate_date(new Date());
    userRepo.save(updatedUser);
  }

  public <T> void updateById(String userId, Map<String, T> fields) {
    UserEntity updatedUser = getById(userId);
    EntityUtil.partialUpdate(updatedUser, fields);
    updatedUser.setUpdate_date(new Date());
    userRepo.save(updatedUser);
  }

  @CacheEvict(value = "deletedUser", key = "#userId")
  public void deleteOneById(String userId) {
    UserEntity currentUser = authService.getCurrentUser();
    if (Objects.equals(currentUser.getId(), userId)) {
      throw new CustomException("You cannot delete your account", HttpStatus.BAD_REQUEST, null);
    }
    userRepo.deleteById(userId);
  }
}
