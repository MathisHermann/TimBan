package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimbanCompanyConfigService;
import rocks.process.timban.business.service.TimbanUserService;

/**
 * Author: Mathis
 * Peer: Sven
 * Reviewer: -
 * Date: 21.04.2021, 12.05.2021
 *
 * This class handles all request regarding the frontend view "admin". Only admin users can open this page.
 * In this view, the admin can see a list of all users with some information.
 */

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private TimbanUserService timbanUserService;

    @Autowired
    TimbanCompanyConfigService timbanCompanyConfigService;

    /**
     * Get the admin view. There are two attributes added to the model that is sent to the frontend.
     * The attribute users is a list of all users and the corresponding information.
     * The attribute companyHours is the global amount of hours each employee of 100% has to work.
     *
     * If the user is admin and no exception is thrown, the mentioned attributes are sent with the view "admin".
     * If there is no logged in user as "currentTimbanUser", the view "login" is sent.
     * If the current user is not an admin user, they are sent the view "dashboard".
     */
    @GetMapping
    public String getAdminView(Model model) {
        try {
            if (timbanUserService.getCurrentTimbanUser().isAdmin()) {
                model.addAttribute("users", timbanUserService.getAllTimbanUsers());
                model.addAttribute("weeklyCompanyHours", timbanCompanyConfigService.getWeeklyCompanyHours());
                model.addAttribute("monthlyCompanyHours", timbanCompanyConfigService.getMonthlyCompanyHours());
                model.addAttribute("yearlyCompanyHours", timbanCompanyConfigService.getYearlyCompanyHours());

                // Return if there are no exceptions and everything works as expected.
                return "admin";
            } else {
                // Redirect if the current user is not an admin user.
                return "redirect:/dashboard";
            }
        } catch (Exception e) {
            // Return if the visitor is not logged in or login is not valid.
            return "redirect:/login";
        }

    }



}