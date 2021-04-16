package com.itproject.timban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 16.04.2021
 */

@Controller
@RequestMapping(path = "/login")
public class LoginController {

    @GetMapping
    public String getLogin() {
        return "login";
    }

    @PostMapping
    public void postLogin() {
        System.out.println("Hello World.");
    }


}
