package ru.kata.spring.boot_security.demo.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.Role;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


import java.security.Principal;
import java.util.Set;

@Controller
public class AdminController {
    private final UserServiceImpl userService;
//    private final RoleDaoImpl roleService;
  //  private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
 //       this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.index());
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "admin";
    }

    @GetMapping("/id")
    public String show(@RequestParam(value = "id", required = false, defaultValue = "1") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "user_info_for_admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
 //       model.addAttribute("role", roleService.findAll());
        return "new";
    }
    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") User user, Role role) {
        userService.save(user,role);
        return "redirect:/admin";
    }
    @PostMapping("/remove")
    public String removeUser(@RequestParam("id") Long id) {
        userService.remove(id);
        return "redirect:/admin";
    }

//    @PostMapping("/edit")
//    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") Long id) {
//        userService.update(id, user);
//        return "redirect:/admin";
//    }
        @PatchMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") Long id) {
//        user.setRoles(user.getAllUserRoles());
        userService.update(user, id);
        return "redirect:/admin";
    }













//    @GetMapping("/edit")
//    public String edit(@RequestParam(value = "id", required = false, defaultValue = "1") Long id, Model model) {
//        model.addAttribute("user", userService.show(id));
////        model.addAttribute("roles", roleService.getAllRoles());
//        return "edit";
//    }

//        @PostMapping("/edit")
//    public String edit(@RequestParam(value = "id", required = false, defaultValue = "1") Long id, Model model,User user) {
//        model.addAttribute("user", userService.show(id));
//            userService.update(user);
////        model.addAttribute("roles", roleService.getAllRoles());
//        return "redirect:/admin";
//    }


//@PostMapping("/edit")
//public String updateUser(@ModelAttribute("user") User user,
//                         Model model, Principal principal) {
//    model.addAttribute("user", userService.findByUsername(principal.getName()));
//    userService.update(user);
//    return "redirect:/users/admin";
//}
//@PostMapping("/admin")
//public String updateUser(@ModelAttribute("userIter") User user,
//                         Model model, Principal principal) {
//    model.addAttribute("authUser", userService.findByUsername(principal.getName()));
//    userService.update(user);
//    return "redirect:/admin";
//}
}
