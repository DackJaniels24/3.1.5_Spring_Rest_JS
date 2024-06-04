package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "role_id")
        private Long id;

        @Column(name = "role_name")
        private String name;

        public Role() {  }
        @ManyToMany(mappedBy = "roles")
        private Set<User> users;

//        public Set<User> getUser() {
//        return users;
//    }
//
//        public void setUser(Set<User> users) {
//        this.users = users;
//    }
//        public Role(Long id) {
//            this.id = id;
//        }

        public Role(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getAuthority() {
            return getName();
        }
    }

