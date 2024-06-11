//package ru.kata.spring.boot_security.demo.userDAO;
//
//
//import org.springframework.stereotype.Repository;
//import ru.kata.spring.boot_security.demo.model.Role;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Repository
//public class RoleDaoImpl {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    public Set<Role> getAllRoles() {
//        List<Role> roleList = entityManager.createQuery("select r from Role r ", Role.class).getResultList();
//        return new HashSet<>(roleList);
//    }
//
//
////    public Role getRoleByName(String role) {
////        return entityManager.createQuery(
////                "SELECT r from Role r where r.name=:role", Role.class
////        ).setParameter("role", role).getSingleResult();
////    }
////
////
//    public Set<Role> getSetOfRoles(String[] roleNames) {
//        Set<Role> roleSet = new HashSet<>();
//        for (String role : roleNames) {
//            roleSet.add(getRoleByName(role));
//        }
//        return roleSet;
//    }
//
//
//    public void add(Role role) {
//        entityManager.persist(role);
//    }
//
//
//    public void edit(Role role) {
//        entityManager.merge(role);
//    }
//
//
//    public Role getById(Long id) {
//
//        return entityManager.find(Role.class, id);
//    }
//
//    public Role getRoleByName(String name) {
//        Role role = null;
//        try {
//            role = entityManager
//                    .createQuery("SELECT r FROM Role r WHERE r.name=:role", Role.class)
//                    .setParameter("role", name)
//                    .getSingleResult();
//        } catch (Exception e) {
//            System.out.println("Роли с таким именем не существует");
//        }
//        return role;
//    }
//}