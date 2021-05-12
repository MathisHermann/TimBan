package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import rocks.process.timban.business.service.TimbanUserService;
import rocks.process.timban.data.domain.TimbanUser;

import java.util.Optional;

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
        Optional<TimbanUser> timbanUser = timbanUserService.getUserById(id);
        if (timbanUser.isPresent()) {
            model.addAttribute(
                    "user",
                    timbanUser.get()
            );

            return "overview";
        } else {
            return "admin";
        }
    }

}
