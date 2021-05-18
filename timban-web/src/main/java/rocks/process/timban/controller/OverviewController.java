package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimbanCompanyConfigService;
import rocks.process.timban.business.service.TimbanTimeRecordService;
import rocks.process.timban.business.service.TimbanUserService;
import rocks.process.timban.data.domain.TimbanUser;

import java.util.Optional;

/**
 * Author: Mathis
 * Peer: Sven
 * Reviewer: -
 * Date: 21.04.2021, 12.05.2021
 *
 * This class handles all request regarding the frontend view "overview". Only admin users can open this page.
 * This view is shown, when a particular user is to be seen.
 */

@Controller
@RequestMapping(path = "/overview")
public class OverviewController {

    @Autowired
    TimbanUserService timbanUserService;

    @Autowired
    TimbanTimeRecordService timbanTimeRecordService;

    @Autowired
    TimbanCompanyConfigService timbanCompanyConfigService;

    @GetMapping
    public String getOverview() {
        return "overview";
    }

    /**
     * Get the Overview view. There is one attribute added to the model that is sent to the frontend.
     * The attribute user is the single user the admin user wants to have a look at.
     */
    @GetMapping(path = "/{id}")
    public String getOverviewWithUser(Model model, @PathVariable Long id) {
        try {
            if (timbanUserService.getCurrentTimbanUser().isAdmin()) {
                Optional<TimbanUser> timbanUser = timbanUserService.getUserById(id);
                if (timbanUser.isPresent()) {
                    model.addAttribute(
                            "user", timbanUser.get()
                    );
                    model.addAttribute(
                            "weekly", timbanTimeRecordService.getWeekly(timbanUser.get().getId())
                    );
                    model.addAttribute(
                            "monthly", timbanTimeRecordService.getMonthly(timbanUser.get().getId())
                    );
                    model.addAttribute(
                            "yearly",  timbanTimeRecordService.getYearly(timbanUser.get().getId())
                    );

                    model.addAttribute(
                            "weeklyCompanyHours", timbanCompanyConfigService.getWeeklyCompanyHours()
                    );

                    model.addAttribute(
                            "monthlyCompanyHours", timbanCompanyConfigService.getMonthlyCompanyHours()
                    );

                    model.addAttribute(
                            "yearlyCompanyHours", timbanCompanyConfigService.getYearlyCompanyHours()
                    );

                    // valid return if there is no error
                    return "overview";
                } else {
                    // redirect to the view "admin" if there is no user present with the given id
                    return "redirect:/admin";
                }

            } else {
                // redirect to view "dashboard" if the user is not an admin.
                return "redirect:/dashboard";
            }
        } catch (Exception e) {
            // return the view "login" if visitor is not logged in
            return  "login";
        }
    }

}
