package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimbanUserService;

/**
 * Author: Mathis
 * Peer: Sven
 * Reviewer: -
 * Date: 21.04.2021
 */

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private TimbanUserService timbanUserService;

    @GetMapping
    public String getAdminView(Model model) {
        try {
            if (timbanUserService.getCurrentTimbanUser().isAdmin())
                return "admin";
            else
                return "dashboard";
        } catch (Exception e) {
            return "login";
        }

    }
}