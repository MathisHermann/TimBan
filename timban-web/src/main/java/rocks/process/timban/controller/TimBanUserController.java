/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rocks.process.timban.business.service.TimBanUserService;
import rocks.process.timban.data.domain.TimBanUser;

@Controller
public class TimBanUserController {

    @Autowired
    private TimBanUserService timBanUserService;

    @GetMapping("/login")
    public String getLoginView() {
        return "login.html";
    }

    @GetMapping("/register")
    public String getRegisterView() {
        return "register.html";
    }

    @PostMapping("/register")
    public ResponseEntity<Void> postRegister(@RequestBody TimBanUser timBanUser)  {
        try {
            timBanUserService.saveTimBanUser(timBanUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/dashboard")
    public String getDashboardView() throws Exception {
        return "dashboard.html";
    }

    @GetMapping("/faq")
    public String getFAQView() throws Exception {
        return "faq.html";
    }

    @GetMapping("/user/settings")
    public String getProfileView() {
        return "../user/settings.html";
    }

    @GetMapping("/profile")
    public @ResponseBody
    TimBanUser getProfile() {
        return timBanUserService.getCurrentTimBanUser();
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> putProfile(@RequestBody TimBanUser timBanUser) {
        try {
            timBanUser.setId(timBanUserService.getCurrentTimBanUser().getId());
            timBanUserService.saveTimBanUser(timBanUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/validate", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<Void> init() {
        return ResponseEntity.ok().build();
    }
}