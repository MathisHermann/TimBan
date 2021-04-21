package com.itproject.timban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Mathis
 * Peer: Sven
 * Reviewer: -
 * Date: 21.04.2021
 */

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping
    public String getAdminView(Model model) {
        return "admin";
    }

}
