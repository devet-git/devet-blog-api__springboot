package com.personal.devetblogapi.controller;

import com.personal.devetblogapi.annotation.SwaggerFormat;
import com.personal.devetblogapi.constant.AppEndpoint;
import com.personal.devetblogapi.constant.CrossOriginUrls;
import com.personal.devetblogapi.constant.MessageConst;
import com.personal.devetblogapi.entity.UserEntity;
import com.personal.devetblogapi.model.EntityResponseDto;
import com.personal.devetblogapi.model.UserDto;
import com.personal.devetblogapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppEndpoint.ROOT_V1)
@Tag(name = "Users")
@CrossOrigin(
    value = {CrossOriginUrls.WEB_DEV, CrossOriginUrls.WEB_PROD},
    maxAge = 3600)
public class UserController {
  @Autowired private UserService userService;
  @Autowired private ModelMapper modelMapper;

  @PreAuthorize("hasRole('ADMIN')")
  @SwaggerFormat(summary = "List all users(only ADMIN)")
  @GetMapping(AppEndpoint.User.listAll)
  public ResponseEntity<?> listAllUsers(
      @RequestParam(defaultValue = "1") int pageNumber,
      @RequestParam(defaultValue = "1") int pageSize,
      @RequestParam(defaultValue = "email") String sortBy) {
    List<UserEntity> users = userService.getAll(pageNumber, pageSize, sortBy);

    return EntityResponseDto.success(HttpStatus.OK, MessageConst.success("List all users"), users);
  }

  @SwaggerFormat(summary = "Get user by id")
  @GetMapping(AppEndpoint.User.GET_BY_ID)
  public ResponseEntity<?> getById(@PathVariable("id") String userId) {

    UserEntity user = userService.getById(userId);
    var res = modelMapper.map(user, UserDto.Response.class);
    return EntityResponseDto.success(
        HttpStatus.OK, MessageConst.success("Get user with id: " + userId), res);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @SwaggerFormat(summary = "Create an user(only ADMIN)")
  @PostMapping(AppEndpoint.User.CREATE)
  public ResponseEntity<?> createOne(@RequestBody UserDto.Create req) {
    userService.createOne(req);

    return EntityResponseDto.success(HttpStatus.OK, MessageConst.success("Create new user"), null);
  }

  @SwaggerFormat(summary = "Update/Create user by id")
  @PutMapping(AppEndpoint.User.UPDATE)
  public <T> ResponseEntity<?> updateById(
      @PathVariable("id") String userId, @RequestBody UserDto.Update req) {
    userService.updateById(userId, req);

    return EntityResponseDto.success(HttpStatus.OK, MessageConst.success("Update user"), null);
  }

  @SwaggerFormat(summary = "Update a part of the user by id")
  @PatchMapping(AppEndpoint.User.UPDATE)
  public <T> ResponseEntity<?> updateById(
      @PathVariable("id") String userId, @RequestBody Map<String, T> req) {
    userService.updateById(userId, req);
    return EntityResponseDto.success(HttpStatus.OK, MessageConst.success("Update user"), null);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @SwaggerFormat(summary = "Delete an user(only ADMIN)")
  @DeleteMapping(AppEndpoint.User.DELETE)
  public ResponseEntity<?> deleteOneById(@PathVariable("id") String userId) {
    userService.deleteOneById(userId);
    return EntityResponseDto.success(
        HttpStatus.OK, MessageConst.success("Delete user with id: " + userId), null);
  }
}
