package edu.usc.csci310.controller;

import edu.usc.csci310.model.User;
import edu.usc.csci310.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    UserRepository ur;

    @GetMapping("")
    public String loginRequest(
            long userid,
            String password
    ) {
        User user = ur.findByUserId(userid);
        if(password.equals(user.getPassword())) {
            return "Success";
        }
        else {
            return "Fail";
        }
    }
}
