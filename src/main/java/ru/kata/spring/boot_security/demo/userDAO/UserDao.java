package ru.kata.spring.boot_security.demo.userDAO;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
  List<User> getAllUsers();

  User show(Long id);

   void remove(Long id);

    void update(Long id, User user);

    void save(User user);
}
