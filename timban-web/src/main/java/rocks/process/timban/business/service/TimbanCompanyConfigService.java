package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.process.timban.data.domain.TimbanCompanyConfig;
import rocks.process.timban.data.repository.TimbanCompanyConfigRepository;

/**
 * Author: Sven Herzig
 * PairProgrammer: -
 * Reviewer:
 * Date: 26.04.2021
 */

@Service
public class TimbanCompanyConfigService {

    @Autowired
    TimbanCompanyConfigRepository timbanCompanyConfigRepository;

    public TimbanCompanyConfig saveTimbanCompanyConfig(TimbanCompanyConfig timbanCompanyConfig) {
        return timbanCompanyConfigRepository.save(timbanCompanyConfig);
    }

    public int getWeeklyCompanyHours() {
        return 42;
    }

    public int getMonthlyCompanyHours() {return getWeeklyCompanyHours() * getYearlyCompanyHours()/12;}

    public int getYearlyCompanyHours() {return getWeeklyCompanyHours() * 52;}
}
