package com.yurikilian.securitydashboard.configuration;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.yurikilian.securitydashboard.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByCredentialsEmail(String email);
}
