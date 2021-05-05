package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimbanUserService;

@Controller
@RequestMapping(path = "/overview")
public class OverviewController {

    @Autowired
    TimbanUserService timbanUserService;

    @GetMapping
    public String getOverview() {
        return "overview";
    }

    @GetMapping(path = "/{id}")
    public String getOverviewWithUser(Model model, @PathVariable Long id) {
        model.addAttribute(
                "user",
                timbanUserService.getUserById(id)
        );
        return "overview";
    }

}
