package ru.kata.spring.boot_security.demo.userDAO;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Component
public class UserDaoImpl implements UserDao {
@PersistenceContext
private EntityManager entityManager;

    @Override
    public List<User> getAllUsers(){
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }
    @Override
    public User show(Long id) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u WHERE u.id=:user_id", User.class);
        q.setParameter("user_id", id);
         return q.getResultList().stream().findAny().orElse(null);
    }
    @Override
    public void remove(Long id) {
        entityManager.remove(show(id));
    }

    @Override
    public void update(Long id, User user) {
        entityManager.merge(user);
    }
//
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }


}
