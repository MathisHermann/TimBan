package rocks.process.timban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Lars
 * Peer: -
 * Reviewer: -
 * Date: 21.05.2021
 */

@Controller
@RequestMapping(path = "/recordSettings")
public class RecordSettingsController {

    @GetMapping
    public String index() {
        return "recordSettings";
    }

}