package com.greenblat.twitter.controller;

import com.greenblat.twitter.model.Role;
import com.greenblat.twitter.model.User;
import com.greenblat.twitter.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
     public String addUser(User user, Map<String , Object> model) {
        User userFromDatabase = userRepo.findByUsername(user.getUsername());

        if (userFromDatabase != null) {
            model.put("exists", "User exists");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepo.save(user);

        return "redirect:/login";
     }
}
