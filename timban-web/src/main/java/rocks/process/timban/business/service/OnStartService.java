package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import rocks.process.timban.data.domain.TimbanProject;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.tools.LogToFile;
import rocks.process.timban.tools.ReportPDF;

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

    @Autowired
    TimbanProjectService timbanProjectService;

    @Autowired
    ReportPDF reportPDF;

    private final boolean runsProductive = Boolean.getBoolean(System.getenv("RUN_PROD"));
    private boolean adminCreated = false;
    private boolean noProjectCreated = false;
    private boolean fakeUsersCreated = false;
    private boolean fakeTimeRecordsCreated = false;
    private boolean fakeProjectsCreated = false;

    /**
     * Execute the faker and creation of the admin user.
     *
     * @param event - Not relevant
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("Hello World : " + runsProductive);
        if (!adminCreated)
            this.createAdminUser();
        if (!noProjectCreated)
            this.createNoProject();
        if (!fakeUsersCreated && !runsProductive)
            this.createFakeUsers();
        if (!fakeTimeRecordsCreated && !runsProductive)
            this.createFakeTimeRecords();
        if (!fakeProjectsCreated && !runsProductive)
            this.createFakeProjects();

        this.logResults();
    }

    private void createNoProject() {
        noProjectCreated = true;
        try {
            TimbanProject timbanProject = new TimbanProject("No Project");
            timbanProjectService.save(timbanProject);
        } catch (Exception e) {
            e.printStackTrace();
            noProjectCreated = false;
        }
    }

    /**
     * Log the results of the fakers to the system log-file
     */
    private void logResults() {
        LogToFile.logSystem("info", "Admin Account " + (adminCreated ? "successfully" : "not") + " created.");
        LogToFile.logSystem("info", "Fake User Accounts " + (fakeUsersCreated ? "successfully" : "not") + " created.");
        LogToFile.logSystem("info", "Fake Time Records " + (fakeTimeRecordsCreated ? "successfully" : "not") + " created.");
        LogToFile.logSystem("info", "Fake Projects " + (fakeProjectsCreated ? "successfully" : "not") + " created.");
        // timbanTimeRecordService.calculate(2L, "week");
    }

    /**
     * Create Fake Projects
     */
    private void createFakeProjects() {
        fakeProjectsCreated = true;
        try {
            timbanProjectService.save(new TimbanProject("Antonio's Project"));
            timbanProjectService.save(new TimbanProject("Lars' Project"));
            timbanProjectService.save(new TimbanProject("Sven's Project"));
            timbanProjectService.save(new TimbanProject("Mathis' Project"));
        } catch (Exception e) {
            e.printStackTrace();
            fakeProjectsCreated = false;
        }
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
            String userName = System.getenv("INSERT_USERNAME");
            String email = System.getenv("INSERT_EMAIL");
            String password = System.getenv("INSERT_PASSWORD");

            if (userName != null && email != null && password != null)
                timbanUserService.saveTimbanUser(new TimbanUser(userName, email, password, true, 0, false));
            else
                adminCreated = false;

        } catch (Exception e) {
            adminCreated = false;
        }

        if (!adminCreated) {
            try {
                timbanUserService.saveTimbanUser(new TimbanUser("admin", "admin@example.com", "12345678", true, 0, false));
            } catch (Exception e) {
                e.printStackTrace();
                adminCreated = false;
            }
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
                                Instant.now().minus(280, ChronoUnit.MINUTES).minus(10080 * 52, ChronoUnit.MINUTES)
                        ), true);

                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().minus(180, ChronoUnit.MINUTES).minus(10080 * 52, ChronoUnit.MINUTES)
                        ), true);
            }
            for (TimbanUser timbanUser : timbanUserService.getAllTimbanUsers()) {
                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                timbanUser.isCurrentlyCheckedIn(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().minus(170, ChronoUnit.MINUTES)
                        ), true);

                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().minus(90, ChronoUnit.MINUTES)
                        ), true);
            }

            for (TimbanUser timbanUser : timbanUserService.getAllTimbanUsers()) {
                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                timbanUser.isCurrentlyCheckedIn(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().minus(270, ChronoUnit.MINUTES)
                        ), true);

                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().minus(180, ChronoUnit.MINUTES)
                        ), true);
            }
            for (TimbanUser timbanUser : timbanUserService.getAllTimbanUsers()) {
                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                timbanUser.isCurrentlyCheckedIn(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().minus(280, ChronoUnit.MINUTES).minus(9080, ChronoUnit.MINUTES)
                        ), true);

                timbanTimeRecordService.saveTimbanTimeRecord(
                        new TimbanTimeRecord(
                                timbanUser.getId(),
                                !timbanUser.isCurrentlyCheckedIn(),
                                timbanUser.isCurrentlyCheckedIn(),
                                Instant.now().minus(180, ChronoUnit.MINUTES).minus(9080, ChronoUnit.MINUTES)
                        ), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fakeTimeRecordsCreated = false;
        }
    }

}

