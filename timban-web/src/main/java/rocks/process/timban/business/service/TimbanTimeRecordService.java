package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.repository.TimbanTimeRecordRepository;

import javax.validation.Valid;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 27.04.2021
 * Edit:
 */

@Service
public class TimbanTimeRecordService {

    @Autowired
    TimbanUserService timbanUserService;

    @Autowired
    private TimbanTimeRecordRepository timbanTimeRecordRepository;

    public TimbanTimeRecord saveTimbanTimeRecord(@Valid TimbanTimeRecord timbanTimeRecord) {
        // If the id is null, a new Time Record is created. Otherwise, the existing entity is updated.

        if (timbanTimeRecord.getId() == null) {
            timbanUserService.setUserCurrentlyCheckedIn(timbanTimeRecord);
            return timbanTimeRecordRepository.save(timbanTimeRecord);
        } else if (timbanTimeRecordRepository.findById(timbanTimeRecord.getId()).isPresent())
            return timbanTimeRecordRepository.save(timbanTimeRecord);
        return null;
    }
}