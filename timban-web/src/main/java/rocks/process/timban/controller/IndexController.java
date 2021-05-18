package rocks.process.timban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 11.04.2021
 * Edit: 16.04.2021, 18.05.2021
 */

@Controller
@RequestMapping(path = {"/", "/index"})
public class IndexController {

    @GetMapping
    public RedirectView getIndex() {
        return new RedirectView("login");
    }

}
