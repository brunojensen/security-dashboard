package com.yurikilian.securitydashboard.controller;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yurikilian.securitydashboard.core.mapper.DTORequestBody;
import com.yurikilian.securitydashboard.core.mapper.DTORequestParam;
import com.yurikilian.securitydashboard.domain.User;
import com.yurikilian.securitydashboard.model.CreateUserDto;
import com.yurikilian.securitydashboard.model.SearchUserDto;
import com.yurikilian.securitydashboard.model.UpdateUserDto;
import com.yurikilian.securitydashboard.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
  private UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<User> create(@DTORequestBody(CreateUserDto.class) User user)
      throws URISyntaxException {
    User created = service.create(user);
    return ResponseEntity.created(new URI("user" + created.getId())).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> read(@PathVariable("id") Long id) {
    return ResponseEntity.ok(service.read(id));
  }

  @GetMapping
  public ResponseEntity<Page<User>> list(@DTORequestParam(SearchUserDto.class) User user,
      Pageable pageable) {
    return ResponseEntity.ok(service.findAll(user, pageable));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable("id") Long id,
      @DTORequestBody(UpdateUserDto.class) User user) {
    service.update(id, user);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.delete(id);
    return ResponseEntity.ok().build();
  }


}
