package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.tools.LogToFile;


/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 29.04.2021
 * Edit:
 */

@Component
public class OnStartService implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    TimbanUserService timbanUserService;

    @Autowired
    TimbanTimeRecordService timbanTimeRecordService;


    private void logResults(boolean adminCreated, boolean fakeUsersCreated) {
        LogToFile.logSystem("info", "Admin Account " + (adminCreated ? "successfully" : "not") + " created.");
        LogToFile.logSystem("info", "Fake User Accounts " + (fakeUsersCreated ? "successfully" : "not") + " created.");
    }

    public boolean createFakeUsers() {
        try {
            timbanUserService.saveTimbanUser(new TimbanUser("Hans", "hans@example.com", "password", false, 80));
            timbanUserService.saveTimbanUser(new TimbanUser("Jakob", "jakob@example.com", "password", false, 1));
            timbanUserService.saveTimbanUser(new TimbanUser("Lisa", "lisa@example.com", "password", false, 800));
            timbanUserService.saveTimbanUser(new TimbanUser("Jessica", "jessica@example.com", "password", false, 24));
            timbanUserService.saveTimbanUser(new TimbanUser("Ruedi", "ruedi@example.com", "password", false, 41));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createAdminUser() {
        try {
            timbanUserService.saveTimbanUser(new TimbanUser("admin", "admin@example.com", "12345678", true, 0));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.logResults(this.createAdminUser(), this.createFakeUsers());
    }


    // ToDo: create fake timeRecords


}

