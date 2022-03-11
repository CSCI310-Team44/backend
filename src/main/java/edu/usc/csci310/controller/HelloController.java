package edu.usc.csci310.controller;

import edu.usc.csci310.model.User;
import edu.usc.csci310.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/test1")
    public String test1() {
        User a = userRepo.findByUserId((long)1);
        return a.getLname();
    }
}
