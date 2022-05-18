package com.greenblat.twitter.controller;

import com.greenblat.twitter.model.Role;
import com.greenblat.twitter.model.User;
import com.greenblat.twitter.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());

        return "user_list";
    }

    @GetMapping("/{user}")
    public String editUser(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user_edit";
    }

    @PostMapping
    public String saveUser(@RequestParam String username, @RequestParam Map<String, String> form, @RequestParam("userId") User user) {
        user.setUsername(username);

        user.getRoles().clear();

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        for (String key: form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);

        return "redirect:/user";
    }
}
