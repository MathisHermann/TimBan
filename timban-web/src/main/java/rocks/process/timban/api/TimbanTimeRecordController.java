package rocks.process.timban.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.process.timban.business.service.TimbanTimeRecordService;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.data.repository.TimbanTimeRecordRepository;
import rocks.process.timban.tools.LogToFile;

import java.util.List;
import java.util.Optional;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 16.04.2021
 * Updated: 27.04.2021
 * <p>
 * This class handles all the requests regarding the model Record. Supporting all CRUD operations.
 */
@RestController
@RequestMapping(path = "/api/records")
public class TimbanTimeRecordController {

    @Autowired
    private TimbanTimeRecordRepository timbanTimeRecordRepository;

    @Autowired
    private TimbanTimeRecordService timbanTimeRecordService;

    /**
     * Get a list of all Records
     */
    @GetMapping
    public List<TimbanTimeRecord> getAllRecord() {
        return timbanTimeRecordRepository.findAll();
    }

    /**
     * Get all records from one user
     */
    @GetMapping(path = "/users/{id}")
    public List<TimbanTimeRecord> getRecordsByUser(@PathVariable Long id) {
        return timbanTimeRecordRepository.findAllByUserId(id);
    }

    /**
     * Get a single record out of many
     */
    @GetMapping(path = "/{id}")
    public Optional<TimbanTimeRecord> getSingleRecord(@PathVariable Long id) {
        return timbanTimeRecordRepository.findById(id);
    }

    /**
     * Create new Record
     */
    @PostMapping
    public ResponseEntity<TimbanTimeRecord> createRecord(@RequestBody TimbanTimeRecord timbanTimeRecord) {
        try {
            timbanTimeRecord.setStopRecording(!timbanTimeRecord.getStartRecording());
            TimbanTimeRecord updatedTimbanTimeRecord = timbanTimeRecordService.saveTimbanTimeRecord(timbanTimeRecord, false);
            LogToFile.logUser("Record created; User: " + updatedTimbanTimeRecord.getUserId() + "; Timestamp: "
                    + updatedTimbanTimeRecord.getTimestamp() + "; Action: "
                    + (updatedTimbanTimeRecord.getStartRecording() ? "Start recording" : "Stop recording"));

                return new ResponseEntity<>(updatedTimbanTimeRecord, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(timbanTimeRecord, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update existing Record
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<TimbanTimeRecord> updateUser(@RequestBody TimbanTimeRecord timbanTimeRecord, @PathVariable Long id) throws Exception {
        try {
            timbanTimeRecord.setId(id);
            if (timbanTimeRecord.getTimestamp() == null)
                throw new Exception("Entry not valid!");
            TimbanTimeRecord updatedTimbanTimeRecord = timbanTimeRecordService.saveTimbanTimeRecord(timbanTimeRecord, false);
            LogToFile.logUser("Record updated; Id: " + timbanTimeRecord.getId() + "; User: " + updatedTimbanTimeRecord.getUserId() + "; Timestamp: "
                    + updatedTimbanTimeRecord.getTimestamp() + "; Action: "
                    + (updatedTimbanTimeRecord.getStartRecording() ? "Start recording" : "Stop recording"));

            return new ResponseEntity<>(updatedTimbanTimeRecord, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(timbanTimeRecord, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Delete record
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        try {
            timbanTimeRecordRepository.deleteById(id);
            LogToFile.logUser("Record deleted: " + (id));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
