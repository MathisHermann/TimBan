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
@RequestMapping(path = "/reporting")
public class ReportingController {

    @GetMapping
    public String getReporting(Model model) {
        model.addAttribute("message", "Hello World of reporting!");
        return "reporting";
    }

}
