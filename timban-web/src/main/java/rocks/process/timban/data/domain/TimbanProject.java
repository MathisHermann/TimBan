package rocks.process.timban.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 19.05.2021
 */

@Entity
public class TimbanProject {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String projectName;

    public TimbanProject() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
