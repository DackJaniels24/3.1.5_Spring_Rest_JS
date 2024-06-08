package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;
import ru.kata.spring.boot_security.demo.userDAO.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Service
public class UserServiceImpl implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleServiceImpl roleService;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RoleServiceImpl roleService, RoleRepository roleRepository,
                           UserDao userDao, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) { // аннотация Lazy позвоялет избежать цикличности
       this.usersRepository = usersRepository;
       this.roleService = roleService;
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
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : user.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        }
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = usersRepository.save(user);// шифрует пароль
//        Set<Role> roles = new HashSet<>();
//        for (Long roleId : role_id) {
//            Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Роль не найдена"));
//            roles.add(role);
//        }
        Role userRole = roleRepository.findByName("ROLE_USER");
        savedUser.getRoles().add(userRole);//присваивает роль по умолчанию новому пользователю в дочернуюю таблицу
//        savedUser.setRoles(roles);
        usersRepository.save(savedUser);
    }
//        public Collection<Role> getAllRoles() {
//            return entityManager.createQuery("select u from Role u", Role.class)
//                    .getResultList();

}
