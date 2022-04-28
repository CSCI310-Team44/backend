package edu.usc.csci310.controller;

import edu.usc.csci310.model.User;
import edu.usc.csci310.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @GetMapping("profilepic")
    public ResponseEntity<Resource> profilePic(long userid) {
        try {
            final ByteArrayResource bar = new ByteArrayResource(Files.readAllBytes(Paths.get(
                    "./resources2/user/" + userid + "/profile.jpg"
            )));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentLength(bar.contentLength())
                    .body(bar);
        }
        catch (IOException ioe) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

}
