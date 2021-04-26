package rocks.process.timban.data.domain;

import javax.persistence.Entity;

/**
 * Author: Sven Herzig
 * PairProgrammer: -
 * Reviewer: -
 * Date: 26.04.2021
 */

// @Entity
public class TimbanCompanyConfig {

    private String companyName;
    private int companyHours;

    public TimbanCompanyConfig(String companyName, int companyHours) {
        this.companyName = companyName;
        this.companyHours = companyHours;
    }

    /** Getter & Setters */

    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public int getCompanyHours() { return companyHours; }

    public void setCompanyHours(int companyHours) { this.companyHours = companyHours; }
}
