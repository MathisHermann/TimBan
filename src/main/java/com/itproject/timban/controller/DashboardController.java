package com.itproject.timban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 16.04.2021
 */

@Controller
@RequestMapping(path = "/dashboard")
public class DashboardController {

    @GetMapping
    public String getDashboard(Model model) {
        model.addAttribute("message", "Hello World of dashboard!");
        return "dashboard";
    }

}
