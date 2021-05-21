package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimbanProjectService;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 19.05.2021
 */

@Controller
@RequestMapping(path = "/projects")
public class ProjectController {

    @Autowired
    TimbanProjectService timbanProjectService;

    @GetMapping
    public String index(Model model)
    {
        try {
            model.addAttribute("projects", timbanProjectService.getAllTimbanProjects());
        } catch (Exception ignored) {

        }
        return "projects";
    }

}
