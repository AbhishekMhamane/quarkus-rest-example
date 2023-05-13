package com.example.repository;

import com.example.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

  public User findUserByEmail(String email) {
    return find("email", email).firstResult();
  }

  public User findByMobileNo(String mobileNo) {
    return find("mobileNo", mobileNo).firstResult();
  }
}
