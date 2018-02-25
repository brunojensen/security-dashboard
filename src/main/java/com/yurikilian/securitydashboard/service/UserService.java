package com.yurikilian.securitydashboard.service;

import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.yurikilian.securitydashboard.configuration.UserRepository;
import com.yurikilian.securitydashboard.core.bean.BeanUtils;
import com.yurikilian.securitydashboard.core.exception.BussinessException;
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

  public User read(Long id) {
    return repository.findOne(id);
  }

  public void delete(Long id) {
    Optional<User> userOptional = Optional.ofNullable(repository.findOne(id));
    repository.delete(userOptional.orElseThrow(() -> new BussinessException("User not found.")));
  }

  public void update(Long id, User user) {
    Optional<User> userOptional = Optional.ofNullable(repository.findOne(id));
    User persistedUser = userOptional.orElseThrow(() -> new BussinessException("User not found."));
    BeanUtils.merge(persistedUser, user);
    repository.save(persistedUser);
  }

}
