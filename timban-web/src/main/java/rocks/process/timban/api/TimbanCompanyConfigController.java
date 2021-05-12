package rocks.process.timban.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.process.timban.business.service.TimbanCompanyConfigService;
import rocks.process.timban.business.service.TimbanUserService;
import rocks.process.timban.data.domain.TimbanCompanyConfig;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.data.repository.TimbanCompanyConfigRepository;
import rocks.process.timban.tools.LogToFile;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/company-config")
public class TimbanCompanyConfigController {

    @Autowired
    TimbanUserService timbanUserService;

    @Autowired
    TimbanCompanyConfigRepository timbanCompanyConfigRepository;

    @Autowired
    TimbanCompanyConfigService timbanCompanyConfigService;

    @GetMapping
    public Optional<TimbanCompanyConfig> getCompanyConfig() {
        try {
            TimbanUser timbanUser = timbanUserService.getCurrentTimbanUser();
            return timbanCompanyConfigRepository.findById(timbanUser.getId());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @PostMapping
    public ResponseEntity<TimbanCompanyConfig> createTimbanCompanyConfig(@RequestBody TimbanCompanyConfig timbanCompanyConfig) {
        try {
            if (timbanUserService.getCurrentTimbanUser().isAdmin()) {
                TimbanCompanyConfig updatedTimbanCompanyConfig = timbanCompanyConfigService.saveTimbanCompanyConfig(timbanCompanyConfig);
                LogToFile.logUser("Company Config created; Company ID: " + updatedTimbanCompanyConfig.getId() + "; Timestamp: ");
                return new ResponseEntity<>(updatedTimbanCompanyConfig, HttpStatus.OK);
            } else {
                throw new Exception("User ist not an admin");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(timbanCompanyConfig, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<TimbanCompanyConfig> updateTimbanCompanyConfig(@RequestBody TimbanCompanyConfig timbanCompanyConfig) {
        try {
            if (timbanUserService.getCurrentTimbanUser().isAdmin()) {
                TimbanCompanyConfig updatedTimbanCompanyConfig = timbanCompanyConfigService.saveTimbanCompanyConfig(timbanCompanyConfig);
                LogToFile.logUser("Company Config updated; Company ID: " + updatedTimbanCompanyConfig.getId() + "; Timestamp: ");
                return new ResponseEntity<>(updatedTimbanCompanyConfig, HttpStatus.OK);
            } else {
                throw new Exception("User ist not an admin");
            }

        } catch (Exception e) {
            return new ResponseEntity<>(timbanCompanyConfig, HttpStatus.BAD_REQUEST);
        }
    }

}
