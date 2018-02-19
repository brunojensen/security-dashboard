package com.yurikilian.securitydashboard.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yurikilian.securitydashboard.domain.User;
import com.yurikilian.securitydashboard.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
  private UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Page<User>> get(User user, Pageable pageable) {
    return new ResponseEntity<>(service.findAll(user, pageable), HttpStatus.OK);
  }

}
