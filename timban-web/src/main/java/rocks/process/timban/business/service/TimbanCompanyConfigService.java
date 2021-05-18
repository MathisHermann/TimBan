package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.process.timban.data.domain.TimbanCompanyConfig;
import rocks.process.timban.data.repository.TimbanCompanyConfigRepository;

@Service
public class TimbanCompanyConfigService {

    @Autowired
    TimbanCompanyConfigRepository timbanCompanyConfigRepository;

    public TimbanCompanyConfig saveTimbanCompanyConfig(TimbanCompanyConfig timbanCompanyConfig) {
        return timbanCompanyConfigRepository.save(timbanCompanyConfig);
    }

    public int getCompanyHours() {
        return 42;
    }

}
