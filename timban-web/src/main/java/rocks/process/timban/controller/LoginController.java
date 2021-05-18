package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimbanUserService;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 16.04.2021
 */

@Controller
@RequestMapping(path = "/login")
public class LoginController {

    @Autowired
    TimbanUserService timbanUserService;

    @GetMapping
    public String getLogin() {

        try {
            if (timbanUserService.getCurrentTimbanUser() != null)
                return "redirect:/dashboard";
        } catch (Exception ignored) {
        }

        return "login";
    }

}
