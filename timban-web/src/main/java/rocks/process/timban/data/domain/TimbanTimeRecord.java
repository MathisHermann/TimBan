package rocks.process.timban.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

/**
 * Author: Sven Herzig
 * PairProgrammer: -
 * Reviewer: Mathis
 * Date: 26.04.2021
 * Update: 27.04.2021
 */

@Entity
public class TimbanTimeRecord {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private boolean startRecording;
    private boolean stopRecording;
    private Instant timestamp;
    private Long projectId;

    public TimbanTimeRecord(Long userId, boolean startRecording, boolean stopRecording, Instant timestamp) {
        this.userId = userId;
        this.startRecording = startRecording;
        this.stopRecording = stopRecording;
        this.timestamp = timestamp;
    }

    public TimbanTimeRecord() {

    }

    /** Getter & Setters */

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId/*TimbanUser.getId()*/; } // TODO get foreign Key

    public boolean getStartRecording() { return startRecording; }

    public void setStartRecording(boolean startRecording) { this.startRecording = startRecording; }

    public boolean getStopRecording() { return stopRecording; }

    public void setStopRecording(boolean stopRecording) { this.stopRecording = stopRecording; }

    public Instant getTimestamp() { return timestamp; }

    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
