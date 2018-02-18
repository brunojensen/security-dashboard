package com.yurikilian.securitydashboard.configuration;

import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.yurikilian.securitydashboard.domain.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository repository;

  public UserDetailsServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = repository.findByCredentialsEmail(email);

    if (user.isPresent()) {
      return new UserDetailsImpl(user.get());
    }

    throw new UsernameNotFoundException("user not authorized");
  }

}
