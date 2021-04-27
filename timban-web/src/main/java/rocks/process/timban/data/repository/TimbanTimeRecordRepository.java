package rocks.process.timban.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.process.timban.data.domain.TimbanTimeRecord;

import java.util.List;


/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 27.04.2021
 * Edit:
 */

public interface TimbanTimeRecordRepository extends JpaRepository<TimbanTimeRecord, Long> {
    List<TimbanTimeRecord> findAllByUserId(Long userId);
}
