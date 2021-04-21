package com.itproject.timban.api;

import com.itproject.timban.config.LogToFile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 16.04.2021
 */

@RestController
@RequestMapping(path = "/api/records")
public class RecordController {

    /**
     * Get a list of all Records
     * @return
     */
    @GetMapping
    public ArrayList<String> getAllRecord() {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("World");
        return arrayList;
    }

    /**
     * Create new Record
     */
    @PostMapping
    public void createRecord() { //@RequestBody Record record) {

    }

    /**
     * Get a single record out of many
     * @param model
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public String getSingleRecord(Model model, @PathVariable String id) {
        model.addAttribute("record", "Record");
        return "dashboard";
    }
    

}
