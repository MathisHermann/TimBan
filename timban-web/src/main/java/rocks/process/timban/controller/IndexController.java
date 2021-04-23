package rocks.process.timban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 11.04.2021
 * Edit: 16.04.2021
 */

@Controller
@RequestMapping(path = {"/", "/index"})
public class IndexController {

    @GetMapping
    public String getIndex(Model model) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("world");
        model.addAttribute("arrayList", arrayList);
        return "index";
    }

}
