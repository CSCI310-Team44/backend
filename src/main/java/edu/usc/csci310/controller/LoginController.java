package edu.usc.csci310.controller;

import edu.usc.csci310.model.User;
import edu.usc.csci310.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login/")
public class LoginController {

    @Autowired
    UserRepository ur;

    @GetMapping("authenticate")
    public String loginRequest(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        System.out.println("Requesting with username: "+username);
        User user = ur.findByUsername(username);
        if(user == null){
            System.out.println("user not found");
        }
        return "Success";
//        if(password.equals(user.getPassword())) {
//            return "Success";
//        }
//        else {
//            return "Fail";
//        }
    }
}
