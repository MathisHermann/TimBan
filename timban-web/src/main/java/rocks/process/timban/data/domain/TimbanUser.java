
/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.timban.data.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Author: Sven Herzig
 * PairProgrammer: Mathis Hermann
 * Reviewer: -
 * Date: 06.04.2021
 */

@Entity
public class TimbanUser {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Please provide a name.")
    private String userName;

    @Email(message = "Please provide a valid e-mail.")
    @NotEmpty(message = "Please provide an e-mail.")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // only create object property from JSON
    private String password;

    private boolean currentlyCheckedIn;

    private Long companyBelongingId;

    private boolean isAdmin;
    private long weeklyHours;
    private Instant createdOn;
    private Instant changedOn;


    public TimbanUser(Long id, String userName, String email, String password, boolean isAdmin, long weeklyHours, Instant createdOn, Instant changedOn) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.weeklyHours = weeklyHours;
        this.createdOn = createdOn;
        this.changedOn = changedOn;
    }

    public TimbanUser(String userName, String email, String password, boolean isAdmin, long weeklyHours, boolean currentlyCheckedIn) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.weeklyHours = weeklyHours;
        this. currentlyCheckedIn = currentlyCheckedIn;
    }

    public TimbanUser(){

    }

    /* v.2
     private ArrayList<ProjectRole> projectRole;
     private ArrayList<Project> projects;
     */

    /* v.2
     public boolean isInProject(Project project) {
     ArrayList.contains(project); ???
     }
     */

    /* v.2
     hasRole ToDo
     */

    /** Getter & Setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        String transientPassword = this.password;
        this.password = null;
        return transientPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public long getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(long weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getChangedOn() {
        return changedOn;
    }

    public void setChangedOn(Instant deletedOn) {
        this.changedOn = deletedOn;
    }

    public boolean isCurrentlyCheckedIn() {
        return currentlyCheckedIn;
    }

    public void setCurrentlyCheckedIn(boolean currentlyCheckedIn) {
        this.currentlyCheckedIn = currentlyCheckedIn;
    }

    public void setCompanyBelonging(Long companyBelongingId) {
        this.companyBelongingId = companyBelongingId;
    }

    public Long getCompanyBelongingId() {
        return companyBelongingId;
    }

}