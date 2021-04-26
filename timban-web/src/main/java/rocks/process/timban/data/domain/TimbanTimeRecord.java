package rocks.process.timban.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Author: Sven Herzig
 * PairProgrammer: -
 * Reviewer: -
 * Date: 26.04.2021
 */

@Entity
public class TimbanTimeRecord {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private boolean startRecording;
    private boolean stopRecording;
    private Timestamp timestamp; // TODO refresh discussion about Timestamp once tackled

    public TimbanTimeRecord(Long id, Long userId, boolean startRecording, boolean stopRecording, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.startRecording = startRecording;
        this.stopRecording = stopRecording;
        this.timestamp = timestamp;
    }

    /** Getter & Setters */

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.id = userId; } // TODO get foreign Key

    public boolean getStartRecording() { return startRecording; }

    public void setStartRecording(boolean startRecording) { this.startRecording = startRecording; }

    public boolean getStopRecording() { return stopRecording; }

    public void setStopRecording(boolean stopRecording) { this.stopRecording = stopRecording; }

    public Timestamp getTimestamp() { return timestamp; }

    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
