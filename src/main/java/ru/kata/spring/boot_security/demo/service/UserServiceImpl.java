package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;
import ru.kata.spring.boot_security.demo.userDAO.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final RoleServiceImpl roleService;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RoleRepository roleRepository,
                           UserDao userDao, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) { // аннотация Lazy позвоялет избежать цикличности
       this.usersRepository = usersRepository;
       this.userDao = userDao;
       this.bCryptPasswordEncoder = bCryptPasswordEncoder;
       this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
 //   @Override
    @Transactional
    public List<User> index(){
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }
 //   @Override
    @Transactional
    public User show(Long id) {
        return userDao.show(id);
    }
 //   @Override
    @Transactional
    public void remove(Long id) {
        userDao.remove(id);
    }

    @Transactional
    public void save(User user, Role role) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(String.valueOf(role));
        user.addRole(role);
        userDao.save(user);
    }
    @Transactional
    public void update(Long id, User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.update(id, user);
    }
//    @Transactional
//    public void update(Long id, User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        userDao.update(id, user);
//    }
//    @Override


//        @Transactional
//    public void update(User user, Role role) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        Role newRole = roleRepository.findByName(String.valueOf(role));
//        user.getRoles().forEach(roles -> user.removeRole(role));
//        user.addRole(newRole);
////        Role userRole = roleRepository.findByName(String.valueOf(role));
////        user.setRoles(role);
////        Role userRole = roleRepository.findByName(String.valueOf(role));
////        user.addRole(role);
//        userDao.update(user, role);
//    }
//
//@Transactional
//    public void update(User user, String newRoleName) {
//        // Fetch the user from the database
//        User user = usersRepository.findByUsername(username)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        // Fetch the new role from the database
//        Role newRole = roleRepository.findByName(newRoleName)
//                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
//
//        // Remove the current role from the user
//        user.getRoles().forEach(role -> user.removeRole(role));
//
//        // Add the new role to the user
//        user.addRole(newRole);
//
//        // Save the updated user
//        userRepository.save(user);
//    }
//    @Transactional
//    public void save(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        Role userRole = roleRepository.findByName("ROLE_USER");
//        User savedUser = usersRepository.save(user);
//        savedUser.getRoles().add(userRole);//присваивает роль по умолчанию новому пользователю в дочернуюю таблицу
//        usersRepository.save(savedUser);
//    } мой рабочий метод
  //  @Override

//    public User findByUsername(String username){
//        return usersRepository.findByUsername(username);
//    }
 //   @Transactional
  //  @Override
//    public void update(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        Role newRole = roleRepository.findByName(String.valueOf(role));
//        user.addRole(role);
//        usersRepository.save(user);
//    }

//    public void update(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        usersRepository.save(user);
//    }
}
