package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimbanUserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @Autowired
    TimbanUserService timbanUserService;

    @RequestMapping("/error")
    public String renderErrorPage(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            switch (statusCode) {
                case 404:
                    return "404";
                case 403:
                    return "403";
                default:
                    try {
                        if (timbanUserService.getCurrentTimbanUser() == null)
                            return "redirect:/dashboard";
                        else
                            return "login";
                    } catch (Exception e) {
                        return "login";
                    }
            }
        }
        return "login";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
