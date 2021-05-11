package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.tools.LogToFile;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 29.04.2021
 * Edit: 03.05.2021
 */

@Component
public class OnStartService implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    TimbanUserService timbanUserService;

    @Autowired
    TimbanTimeRecordService timbanTimeRecordService;

    private boolean adminCreated = false;
    private boolean fakeUsersCreated = false;
    private boolean fakeTimeRecordsCreated = false;

    /**
     * Execute the faker and creation of the admin user.
     *
     * @param event - Not relevant
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (timbanUserService.getAllTimbanUsers().size() > 0) {
            if (!adminCreated)
                this.createAdminUser();
            if (!fakeUsersCreated)
                this.createFakeUsers();
            if (!fakeTimeRecordsCreated)
                this.createFakeTimeRecords();
        }
        this.logResults();
    }

    /**
     * Log the results of the fakers to the system log-file
     */
    private void logResults() {
        LogToFile.logSystem("info", "Admin Account " + (adminCreated ? "successfully" : "not") + " created.");
        LogToFile.logSystem("info", "Fake User Accounts " + (fakeUsersCreated ? "successfully" : "not") + " created.");
        LogToFile.logSystem("info", "Fake Time Records " + (fakeTimeRecordsCreated ? "successfully" : "not") + " created.");
        timbanTimeRecordService.calculate();
    }

    /**
     * Create Fake Users that are used for testing.
     */
    public void createFakeUsers() {
        fakeUsersCreated = true;
        try {
            timbanUserService.saveTimbanUser(new TimbanUser("Hans", "hans@example.com", "password", false, 80, false));
            timbanUserService.saveTimbanUser(new TimbanUser("Jakob", "jakob@example.com", "password", false, 1, false));
            timbanUserService.saveTimbanUser(new TimbanUser("Lisa", "lisa@example.com", "password", false, 800, false));
            timbanUserService.saveTimbanUser(new TimbanUser("Jessica", "jessica@example.com", "password", false, 24, false));
            timbanUserService.saveTimbanUser(new TimbanUser("Ruedi", "ruedi@example.com", "password", false, 41, false));
        } catch (Exception e) {
            e.printStackTrace();
            fakeUsersCreated = false;
        }
    }

    /**
     * Create The Admin User. Only this will be executed in productive.
     */
    public void createAdminUser() {
        adminCreated = true;
        try {
            timbanUserService.saveTimbanUser(new TimbanUser("admin", "admin@example.com", "12345678", true, 0, false));
        } catch (Exception e) {
            e.printStackTrace();
            adminCreated = false;
        }
    }

    /**
     * Create Fake Time Records that are used for testing.
     */
    public void createFakeTimeRecords() {
        fakeTimeRecordsCreated = true;
        try {
            for (TimbanUser timbanUser : timbanUserService.getAllTimbanUsers()) {
                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                timbanUser.isCurrentlyCheckedIn(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                Instant.now()
                        ), true);

                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().plus(90, ChronoUnit.MINUTES)
                        ), true);
            }

            for (TimbanUser timbanUser : timbanUserService.getAllTimbanUsers()) {
                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                timbanUser.isCurrentlyCheckedIn(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().plus(180, ChronoUnit.MINUTES)
                        ), true);

                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().plus(270, ChronoUnit.MINUTES)
                        ), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fakeTimeRecordsCreated = false;
        }
    }

}

