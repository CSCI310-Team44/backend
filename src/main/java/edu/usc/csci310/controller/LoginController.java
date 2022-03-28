package edu.usc.csci310.controller;

import edu.usc.csci310.model.User;
import edu.usc.csci310.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    UserRepository ur;

    public String loginRequest(
            @RequestParam(value = "userid") long userId,
            @RequestParam(value = "password") String password
    ) {
        User user = ur.findByUserId(userId);
        if(password.equals(user.getPassword())) {
            return "Success";
        }
        else {
            return "Fail";
        }
    }
}
