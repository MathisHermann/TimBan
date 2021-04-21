package com.itproject.timban.api;

import com.itproject.timban.config.LogToFile;
import com.itproject.timban.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 16.04.2021
 *
 * This class handles all the requests regarding the model Record. Supporting all CRUD operations.
 */
@RestController
@RequestMapping(path = "/api/records")
public class RecordController {

    /**
     * Get a list of all Records
     */
    @GetMapping
    public ArrayList<String> getAllRecord() {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("World");
        return arrayList;
    }

    /**
     * Get a single record out of many
     */
    @GetMapping(path = "/{id}")
    public String getSingleRecord(Model model, @PathVariable String id) {
        // ToDo: Add Business Service to retrieve all Records
        model.addAttribute("record", "Record");
        return "dashboard";
    }

    /**
     * Create new Record
     */
    @PostMapping
    //public ResponseEntity<Record> createRecord(Record record) {
    public void createRecord() {
        // ToDo: Add business service to save record later

        // Todo: Remove the comment of the logger below and check validity
        // LogToFile.logUser("Record created; User: " + record.getUser() + "; Timestamp: " + record.getTimestamp() + "; Action: " + record.getAction());
    }

    /**
     * Update existing Record
     */
    @PutMapping
    // public ResponseEntity<Record> updateUser(@RequestBody Record record) {
    public void updateRecord() {
        // ToDo: Add business service to save record later

        // Todo: Remove the comment of the logger below and check validity
        // LogToFile.logUser("Record updated; User: " + record.getUser() + "; Timestamp: " + record.getTimestamp() + "; Action: " + record.getAction());

    }

    /**
     * Delete record
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteRecord() {
        // ToDo: Add business service to delete record later

        // Todo: Remove the comment of the logger below and check validity
        // LogToFile.logUser("Record deleted; User: " + record.getUser() + "; Timestamp: " + record.getTimestamp() + "; Action: " + record.getAction());
        return ResponseEntity.ok().build();
    }

}
