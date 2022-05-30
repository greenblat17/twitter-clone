package com.greenblat.twitter.controller;

import com.greenblat.twitter.model.Message;
import com.greenblat.twitter.model.User;
import com.greenblat.twitter.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model, @RequestParam(required = false, defaultValue = "") String filter) {
        List<Message> messages;
        if (filter == null || filter.isEmpty()) {
            messages = messageRepo.findAll();
        } else {
            messages = messageRepo.findByTag(filter);
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user, @RequestParam String text, @RequestParam String tag){
        Message newMessage = new Message(text, tag, user);
        messageRepo.save(newMessage);

        return "redirect:/main";
    }



}
