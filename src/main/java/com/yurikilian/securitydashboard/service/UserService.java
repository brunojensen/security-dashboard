package com.yurikilian.securitydashboard.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.yurikilian.securitydashboard.configuration.UserRepository;
import com.yurikilian.securitydashboard.domain.User;

@Service
public class UserService {

  private UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public Page<User> findAll(User user, Pageable pageable) {
    return repository.findAll(Example.of(user), pageable);
  }

  public User find(User example) {
    return repository.findOne(Example.of(example));
  }

  public User create(User user) {
    return repository.save(user);
  }

}
