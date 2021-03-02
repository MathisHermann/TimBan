/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimBanUserService;

@Controller
@RequestMapping(path = "/")
public class IndexController {

    @Autowired
    TimBanUserService timBanUserService;

    @GetMapping
    public String getIndexView() throws Exception {
        return "index.html";
    }

}
