package edu.usc.csci310.controller;

import edu.usc.csci310.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    NotificationService ns;

    @GetMapping("")
    public SseEmitter enableNotification(long userid) {
        // No timeout
        SseEmitter sse = new SseEmitter(-1L);
        ns.addNotifier(userid, sse);
        return sse;
    }
}
