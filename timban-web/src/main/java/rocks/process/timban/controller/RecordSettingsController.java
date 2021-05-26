package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimbanProjectService;
import rocks.process.timban.business.service.TimbanTimeRecordService;
import rocks.process.timban.business.service.TimbanUserService;

/**
 * Author: Lars
 * Peer: -
 * Reviewer: -
 * Date: 21.05.2021
 */

@Controller
@RequestMapping(path = "/record-settings")
public class RecordSettingsController {

    @Autowired
    TimbanUserService timbanUserService;

    @Autowired
    TimbanTimeRecordService timbanTimeRecordService;

    @Autowired
    TimbanProjectService timbanProjectService;

    @GetMapping(path = "/{id}")
    public String index(Model model, @PathVariable Long id)
    {
        try {
            model.addAttribute("user", timbanUserService.getCurrentTimbanUser());
            model.addAttribute("record", timbanTimeRecordService.getTimeRecordById(id));
            model.addAttribute("projects", timbanProjectService.getAllTimbanProjects());
            return "recordSettings";
        } catch (Exception e) {
            return "redirect:/dashboard";
        }
    }

}