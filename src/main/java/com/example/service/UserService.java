package com.example.service;

import com.example.exception.CustomException;
import com.example.model.User;
import com.example.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserService {

  private final UserRepository userRepo;

  @Inject
  public UserService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  public List<User> getAllUsers() {
    return userRepo.listAll();
  }

  public User getUserById(long userId) {
    User user = userRepo.findById(userId);

    if (user == null) throw new CustomException(
      "User not found for given userId: " + userId,
      404
    );

    return user;
  }

  @Transactional
  public User createUser(User user) {
    if (findUserByEmail(user.getEmail())) throw new CustomException(
      "User is present with email " + user.getEmail(),
      409
    );

    if (findUserByMobileNo(user.getMobileNo())) throw new CustomException(
      "User is present with mobile no " + user.getMobileNo(),
      409
    );

    userRepo.persistAndFlush(user);
    return user;
  }

  Boolean findUserByEmail(String email) {
    User findUserEmail = userRepo.findUserByEmail(email);

    if (findUserEmail == null) return false;

    return true;
  }

  Boolean findUserByMobileNo(String mobileNo) {
    User findUserMobileNo = userRepo.findByMobileNo(mobileNo);

    if (findUserMobileNo == null) return false;

    return true;
  }

  @Transactional
  public User updateUser(long userId, User user) {
    User userRecord = getUserById(userId);

    if (user.getFirstName() != null) {
      userRecord.setFirstName(user.getFirstName());
    }
    if (user.getLastName() != null) {
      userRecord.setLastName(user.getLastName());
    }
    if (user.getAge() != null) {
      userRecord.setAge(user.getAge());
    }
    if (user.getEmail() != null) {
      if (findUserByEmail(user.getEmail())) throw new CustomException(
        "User is present with email " + user.getEmail(),
        409
      );
      userRecord.setEmail(user.getEmail());
    }
    if (user.getMobileNo() != null) {
      if (findUserByMobileNo(user.getMobileNo())) throw new CustomException(
        "User is present with mobile no " + user.getMobileNo(),
        409
      );
      userRecord.setMobileNo(user.getMobileNo());
    }

    userRepo.persist(userRecord);

    return userRecord;
  }

  @Transactional
  public void deleteUserByUserId(long userId) {
    userRepo.delete(getUserById(userId));
  }
}
