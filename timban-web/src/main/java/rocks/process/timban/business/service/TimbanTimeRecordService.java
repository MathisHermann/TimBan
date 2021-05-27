package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.repository.TimbanTimeRecordRepository;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;


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
            if (!isFromFaker)
                timbanUserService.changeCheckedInStatus(timbanTimeRecord);
            else
                timbanTimeRecord.setProjectId(0L);
            return timbanTimeRecordRepository.save(timbanTimeRecord);
        } else if (timbanTimeRecordRepository.findById(timbanTimeRecord.getId()).isPresent()) {
            timbanTimeRecord.setUserId(timbanTimeRecordRepository.findById(timbanTimeRecord.getId()).get().getUserId());
            return timbanTimeRecordRepository.save(timbanTimeRecord);
        }
        return null;
    }

    /**
     * Parent function to calculate the sum of the worked time.
     * userId defines for which user the time is calculated.
     * timeSpan defines for which timespan the worked time is calculated. timeSpan can have the value "day", "month" or "year"
     */
    public int calculate(Long userId, String timeSpan) {

        // Array of the each time record pair grouped by day
        int[][] diffAndWeekDay = createDiffAndWeekDayTable(userId, timeSpan);

        // Calculate the sum of all daily times
        int totalAllTime = 0;
        for (int[] i : diffAndWeekDay)
            totalAllTime += i[0];

        return totalAllTime;
    }

    public int[][] createDiffAndWeekDayTable(Long userId, String timeSpan) {
        // retrieve data from db
        List<TimbanTimeRecord> timeRecords = getTimeRecordListByTimeSpanAndId(userId, timeSpan);

        if (timeRecords.size() % 2 != 0)
            timeRecords.add(new TimbanTimeRecord(userId, true, false, Instant.now()));

        // Sort the list that the newest record is the first
        timeRecords.sort(Comparator.comparing(TimbanTimeRecord::getTimestamp).reversed());

        // Array of the each time record pair grouped by day
        int[][] diffAndWeekDay = new int[timeRecords.size() / 2][2];

        // Iterate over list and extract the time difference between
        for (int i = 0; i < timeRecords.size(); i += 2) {

            int dayOfFirst = timeRecords.get(i + 1).getTimestamp().atZone(ZoneId.systemDefault()).getDayOfMonth();
            int dayOfSecond = timeRecords.get(i).getTimestamp().atZone(ZoneId.systemDefault()).getDayOfMonth();

            if (dayOfFirst == dayOfSecond) {
                int difference = (int) (timeRecords.get(i).getTimestamp().getEpochSecond()
                        - (int) timeRecords.get(i + 1).getTimestamp().getEpochSecond()) / 60;
                diffAndWeekDay[i / 2][0] = difference;
                diffAndWeekDay[i / 2][1] = dayOfFirst;
            }
        }

        return diffAndWeekDay;
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

    public Long getTotalTimeOfCurrentDay(Long userId) {
        return calculate(userId, "day") * 60L;
    }

    public LocalTime getWeekly(Long userId) {
        return LocalTime.ofSecondOfDay(calculate(userId, "week") * 60L);
    }

    public LocalTime getMonthly(Long userId) {
        return LocalTime.ofSecondOfDay(calculate(userId, "month") * 60L);
    }

    public LocalTime getYearly(Long userId) {
        return LocalTime.ofSecondOfDay(calculate(userId, "year") * 60L);
    }

    public TimbanTimeRecord getTimeRecordById(Long id) {
        return timbanTimeRecordRepository.findById(id).get();
    }

    public List<TimbanTimeRecord> getTimeRecordByProjectId(Long projectId) {
        return timbanTimeRecordRepository.findAllByProjectId(projectId);
    }
}