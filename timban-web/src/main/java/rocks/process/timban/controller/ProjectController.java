package rocks.process.timban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 19.05.2021
 */

@Controller
@RequestMapping(path = "/projects")
public class ProjectController {

    @GetMapping
    public String index() {
        return "projects";
    }

}
