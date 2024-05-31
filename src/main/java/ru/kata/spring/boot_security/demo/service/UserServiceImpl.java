package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;


import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;
import ru.kata.spring.boot_security.demo.userDAO.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;


@Service
public class UserServiceImpl implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RoleRepository roleRepository,
                           UserDao userDao, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) { // аннотация Lazy позвоялет избежать цикличности
       this.usersRepository = usersRepository;
       this.roleRepository = roleRepository;
       this.userDao = userDao;
       this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Transactional
    public List<User> index(){
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Transactional
    public User show(Long id) {
        return userDao.show(id);
    }
    @Transactional
    public void remove(Long id) {
        userDao.remove(id);
    }
    @Transactional
    public void update(Long id, User user) {
        userDao.update(id, user);
    }
    @Transactional
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // шифрует пароль
        user.setRoles(new HashSet<>(roleRepository.findByName("ROLE_USER"))); //присваивает роль по умолчанию новому пользователю в дочернуюю таблицу
        userDao.save(user);
    }

}