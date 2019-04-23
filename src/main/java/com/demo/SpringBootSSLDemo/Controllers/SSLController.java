package com.demo.SpringBootSSLDemo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSLController {
    @RequestMapping("/secured")
    public String secured(){
        return "Welcome User !";
    }
}
