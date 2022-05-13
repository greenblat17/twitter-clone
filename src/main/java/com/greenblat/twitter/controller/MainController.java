package com.greenblat.twitter.controller;

import com.greenblat.twitter.model.Message;
import com.greenblat.twitter.model.User;
import com.greenblat.twitter.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        List<Message> messages = messageRepo.findAll();
        System.out.println(messages);
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user, @RequestParam String text, @RequestParam String tag) {
        System.out.println(5);
        Message newMessage = new Message(text, tag, user);
        System.out.println(10);
        messageRepo.save(newMessage);

        System.out.println(15);


        return "redirect:/main";
    }

    @PostMapping("/filter")
    public String filterMessage(@RequestParam String filter, Map<String, Object> model) {
        List<Message> filterMessages;
        if (filter == null || filter.isEmpty()) {
            filterMessages = messageRepo.findAll();
        } else {
            filterMessages = messageRepo.findByTag(filter);
        }
        model.put("messages", filterMessages);

        return "main";
    }

}
