package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model, Principal principal, User user) {
        model.addAttribute("users", userService.index());
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
   //     model.addAttribute("role", userService.getAllRoles());
        return "admin";
    }

//    @GetMapping("/admin")
//    public String showAllUsers(@ModelAttribute("user") User user, Principal principal, Model model) {
//        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        model.addAttribute("allUsers", userService.getAllUsers());
//        model.addAttribute("activeTable", "usersTable");
//        return "admin-page";
//    }

    @GetMapping("/id")
    public String show(@RequestParam(value = "id", required = false, defaultValue = "1") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "user_info_for_admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }


    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }
//
    @GetMapping("/remove")
    public String removeUser(@RequestParam("id") Long id) {
        userService.remove(id);
        return "redirect:/admin";
    }
    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id", required = false, defaultValue = "1") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") Long id) {
        userService.update(id, user);
        return "redirect:/admin";
    }
}
