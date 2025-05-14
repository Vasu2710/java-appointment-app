package com.example.auth.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "HI, I am running" + request.getSession().getId();
    }

//    @GetMapping("/csrf")
//    public CsrfToken (HttpServletRequest request){
//        return (CsrfToken)request.getAttribute("_csrf");
//
//    }



}
