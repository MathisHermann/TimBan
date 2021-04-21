package com.itproject.timban.models;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Author: Sven Herzig
 * PairProgrammer: Mathis Hermann
 * Reviewer: -
 * Date: 06.04.2021
 */

public class User {
    private long id;
    private String userName;
    private String email;
    private String password;
    private boolean isAdmin;
    private long weeklyHours;
    private Timestamp createdOn;
    private Timestamp deletedOn;

    /** v.2
        private ArrayList<ProjectRole> projectRole;
        private ArrayList<Project> projects;
     */

    public User(long id, String userName, String email, String password, boolean isAdmin, long weeklyHours, Timestamp createdOn, Timestamp deletedOn) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.weeklyHours = weeklyHours;
        this.createdOn = createdOn;
        this.deletedOn = deletedOn;
    }

    public User(String userName, String email, String password, boolean isAdmin, long weeklyHours) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.weeklyHours = weeklyHours;
    }


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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return password;
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

