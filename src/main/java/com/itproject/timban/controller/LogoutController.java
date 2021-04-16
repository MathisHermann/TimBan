package com.itproject.timban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 16.04.2021
 */

@Controller
@RequestMapping(path = "/logout")
public class LogoutController {

    @GetMapping
    public String getLogout() {
        return "logout";
    }

}