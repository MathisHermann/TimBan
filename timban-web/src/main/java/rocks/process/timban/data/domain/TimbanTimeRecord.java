package rocks.process.timban.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    private String timestamp; // TODO refresh discussion about Timestamp once tackled

    public TimbanTimeRecord(Long id, Long userId, boolean startRecording, boolean stopRecording, String timestamp) {
        this.id = id;
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

    public String getTimestamp() { return timestamp; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
