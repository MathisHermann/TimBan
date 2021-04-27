
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

    private boolean isAdmin;
    private long weeklyHours;
    private Timestamp createdOn;
    private Timestamp deletedOn;


    public TimbanUser(Long id, String userName, String email, String password, boolean isAdmin, long weeklyHours, Timestamp createdOn, Timestamp deletedOn) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.weeklyHours = weeklyHours;
        this.createdOn = createdOn;
        this.deletedOn = deletedOn;
    }

    public TimbanUser(String userName, String email, String password, boolean isAdmin, long weeklyHours) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.weeklyHours = weeklyHours;
    }

    public TimbanUser(){

    }





    /** v.2
     private ArrayList<ProjectRole> projectRole;
     private ArrayList<Project> projects;
     */



    /** v.2
     public boolean isInProject(Project project) {
     ArrayList.contains(project); ???
     }
     */

    /** v.2
     hasRole ToDo
     */

    public void changePassword() {
        //ToDo
    }

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

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }
}