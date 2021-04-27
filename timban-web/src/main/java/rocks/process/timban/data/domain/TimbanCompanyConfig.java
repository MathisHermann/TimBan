package rocks.process.timban.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author: Sven Herzig
 * PairProgrammer: -
 * Reviewer: Mathis
 * Date: 26.04.2021
 */

@Entity
public class TimbanCompanyConfig {

    @Id
    @GeneratedValue
    private long id;

    private String companyName;

    private int companyHours;

    public TimbanCompanyConfig(String companyName, int companyHours) {
        this.companyName = companyName;
        this.companyHours = companyHours;
    }

    public TimbanCompanyConfig() {

    }

    /** Getter & Setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public int getCompanyHours() { return companyHours; }

    public void setCompanyHours(int companyHours) { this.companyHours = companyHours; }
}
