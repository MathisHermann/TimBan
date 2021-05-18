package rocks.process.timban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rocks.process.timban.business.service.TimbanTimeRecordService;
import rocks.process.timban.business.service.TimbanUserService;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.repository.TimbanTimeRecordRepository;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 16.04.2021
 */

@Controller
@RequestMapping(path = "/dashboard")
public class DashboardController {

    @Autowired
    TimbanTimeRecordRepository timbanTimeRecordRepository;

    @Autowired
    TimbanUserService timbanUserService;

    @Autowired
    TimbanTimeRecordService timbanTimeRecordService;

    @GetMapping
    public String getDashboard(Model model) {

        /* TODO
         * Add a list of the timerecordings of the current month to the model (remove current attribute message..)
         */

        if (timbanUserService.getCurrentTimbanUser() != null) {

            ArrayList<TimbanTimeRecord> timeRecords = (ArrayList<TimbanTimeRecord>) timbanTimeRecordRepository.findAllByUserId(timbanUserService.getCurrentTimbanUser().getId());

            timeRecords.sort(Comparator.comparing(TimbanTimeRecord::getTimestamp).reversed());

            model.addAttribute("records", timeRecords);
            model.addAttribute("user", timbanUserService.getCurrentTimbanUser());
            model.addAttribute("totalTimeOfCurrentDay", timbanTimeRecordService.getTotalTimeOfCurrentDay(timbanUserService.getCurrentTimbanUser().getId()));
        } else {
            model.addAttribute("records", timbanTimeRecordRepository.findAll());
        }
        return "dashboard";
    }

}
