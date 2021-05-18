package rocks.process.timban.business.service;

import org.apache.tomcat.util.threads.StopPooledThreadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.repository.TimbanTimeRecordRepository;

import javax.validation.Valid;
import java.sql.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

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
    public int calculate(Long userId, String timeSpan) {

        // retrieve data from db
        List<TimbanTimeRecord> timeRecords = getTimeRecordListByTimeSpanAndId(userId, timeSpan);

        // Array of the each time record pair grouped by day
        int[][] diffAndWeekDay = new int[timeRecords.size() / 2][2];

        // Time grouped by day (info about the day)
        ArrayList<Integer> allTimes = new ArrayList<>();

        // Iterate over list and extract the time difference between
        for (int i = 0; i < timeRecords.size(); i += 2) {

            int dayOfFirst = timeRecords.get(i).getTimestamp().atZone(ZoneId.systemDefault()).getDayOfMonth();
            int dayOfSecond = timeRecords.get(i + 1).getTimestamp().atZone(ZoneId.systemDefault()).getDayOfMonth();

            if (dayOfFirst == dayOfSecond) {
                int difference = (int) (timeRecords.get(i + 1).getTimestamp().toEpochMilli()
                        - (int) timeRecords.get(i).getTimestamp().toEpochMilli()) / (60 * 1000);
                diffAndWeekDay[i / 2][0] = difference;
                diffAndWeekDay[i / 2][1] = dayOfFirst;
            }
        }

        // Add the daily times to the corresponding list
        for (int i = 0; i < diffAndWeekDay[diffAndWeekDay.length - 1][1]; i++) {
            int totalTime = 0;
            for (int[] ints : diffAndWeekDay) {
                if (i + 1 == ints[1]) {
                    totalTime += ints[0];
                }
            }
            allTimes.add(totalTime);
        }

        // Calculate the sum
        int totalAllTime = 0;
        for (int i : allTimes)
            totalAllTime += i;

        return totalAllTime;
    }

    public ArrayList<TimbanTimeRecord> getTimeRecordListByTimeSpanAndId(Long userId, String timeSpan) {

        // retrieve data from db
        List<TimbanTimeRecord> allTimeRecords = timbanTimeRecordRepository.findAllByUserId(userId);

        // Empty list where time records of the timespan are saved
        ArrayList<TimbanTimeRecord> filteredTimeRecords = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(Date.from(Instant.now()));

        int currentDay = cal.get(Calendar.DAY_OF_YEAR);
        int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        for (TimbanTimeRecord timeRecord : allTimeRecords) {
            cal.setTime(Date.from(timeRecord.getTimestamp()));

            if (cal.get(Calendar.DAY_OF_YEAR) == currentDay && cal.get(Calendar.YEAR) == currentYear && timeSpan.equals("day")) {
                filteredTimeRecords.add(timeRecord);
            } else if (cal.get(Calendar.WEEK_OF_YEAR) == currentWeek && cal.get(Calendar.YEAR) == currentYear && timeSpan.equals("week")) {
                filteredTimeRecords.add(timeRecord);
            } else if (cal.get(Calendar.MONTH) == currentMonth && cal.get(Calendar.YEAR) == currentYear && timeSpan.equals("month")) {
                filteredTimeRecords.add(timeRecord);
            } else if (cal.get(Calendar.YEAR) == currentYear && timeSpan.equals("year")) {
                filteredTimeRecords.add(timeRecord);
            }
        }

        return filteredTimeRecords;
    }

    public LocalTime getTotalTimeOfCurrentDay(Long userId) {
        return LocalTime.ofSecondOfDay(calculate(userId, "day") * 60L);
    }

    public LocalTime getWeekly(Long userId) {
        return LocalTime.ofSecondOfDay(calculate(userId, "week") * 60L);
    }

    public LocalTime getMonthly(Long userId) {return LocalTime.ofSecondOfDay(calculate(userId, "month") * 60L);
    }

    public LocalTime getYearly(Long userId) {
        return LocalTime.ofSecondOfDay(calculate(userId, "year") * 60L);
    }

}