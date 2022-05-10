package com.greenblat.twitter.controller;

import com.greenblat.twitter.model.Message;
import com.greenblat.twitter.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class  GreetingController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        List<Message> messages = messageRepo.findAll();
        System.out.println(messages);
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/")
    public String add(@RequestParam String text, @RequestParam String tag)
    {
        Message newMessage = new Message(text, tag);
        messageRepo.save(newMessage);

        return "redirect:/";
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
