package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.repository.TimbanTimeRecordRepository;

import javax.validation.Valid;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 27.04.2021
 * Edit:
 */

@Service
public class TimbanTimeRecordService {

    @Autowired
    TimbanUserService timbanUserService;

    @Autowired
    private TimbanTimeRecordRepository timbanTimeRecordRepository;

    public TimbanTimeRecord saveTimbanTimeRecord(@Valid TimbanTimeRecord timbanTimeRecord, boolean isFromFaker) {
        // If the id is null, a new Time Record is created. Otherwise, the existing entity is updated.

        if (timbanTimeRecord.getId() == null) {
            if (!isFromFaker) {
                timbanUserService.changeCheckedInStatus(timbanTimeRecord);
            }
            return timbanTimeRecordRepository.save(timbanTimeRecord);
        } else if (timbanTimeRecordRepository.findById(timbanTimeRecord.getId()).isPresent())
            return timbanTimeRecordRepository.save(timbanTimeRecord);
        return null;
    }

    /**
     * This is a function. lol
     */
    public void calculate() {

        // retrieve data from db
        List<TimbanTimeRecord> arrayList = timbanTimeRecordRepository.findAll();

        int[][] diffAndWeekDay = new int[arrayList.size() / 2][2];


        // Iterate over list
        for (int i = 0; i < arrayList.size(); i += 2) {

            int dayOfFirst = arrayList.get(i).getTimestamp().atZone(ZoneId.systemDefault()).getDayOfMonth();
            int dayOfSecond = arrayList.get(i + 1).getTimestamp().atZone(ZoneId.systemDefault()).getDayOfMonth();

            if (dayOfFirst == dayOfSecond) {

                int difference = (int) (arrayList.get(i + 1).getTimestamp().toEpochMilli()
                        - (int) arrayList.get(i).getTimestamp().toEpochMilli()) / (60 * 1000);

                diffAndWeekDay[i / 2][0] = difference;

                diffAndWeekDay[i / 2][1] = dayOfFirst;

            }

        }

        ArrayList<Integer> allTimes = new ArrayList<>();

        for (int i = 0; i < diffAndWeekDay[diffAndWeekDay.length - 1][1]; i++) {

            int totalTime = 0;

            for (int[] ints : diffAndWeekDay) {

                if (i + 1 == ints[1]) {
                    totalTime += ints[0];
                }
/*
                System.out.print(ints[0] + " minutes : ");
                System.out.println(ints[1] + " week day");
  */          }

            allTimes.add(totalTime);
            System.out.println(totalTime);

        }
    }

}