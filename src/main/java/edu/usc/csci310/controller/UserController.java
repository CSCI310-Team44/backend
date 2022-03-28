package edu.usc.csci310.controller;

import edu.usc.csci310.model.User;
import edu.usc.csci310.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    UserRepository ur;

    @GetMapping("name")
    public String loginRequest(
            @RequestParam(value = "userid") long userid
    ) {
        System.out.println("Requesting with userid: "+userid);
        User user = ur.findByUserId(userid);
        if(user == null){
            System.out.println("user not found");
            return "User Not Found";
        }else{
            return user.getFname() + " " +user.getLname();
        }
    }

}
