package ru.kata.spring.boot_security.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository {
    Set<Role> findByName(String ROLE_USER);
    List<Role> getAllRoles();

    Set<Role> findById(Set<Long> rolesId);

    void saveRole(Role role);

}
