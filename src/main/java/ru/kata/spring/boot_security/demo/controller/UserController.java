package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public String showUserInfo(Model model, Principal principal){
//        User user = userService.findByUsername(principal.getName());
//        model.addAttribute("user", user);
//        return "userInfo";
//    }
    @GetMapping
    public User showUserInfo(Principal principal){
        return userService.findByUsername(principal.getName());
    }
}
