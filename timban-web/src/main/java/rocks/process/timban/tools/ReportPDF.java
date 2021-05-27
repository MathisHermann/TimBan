package rocks.process.timban.tools;

import com.itextpdf.text.*;
import com.itextpdf.text.api.Spaceable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.process.timban.business.service.TimbanTimeRecordService;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.domain.TimbanUser;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 24.05.2021
 */

@Service
public class ReportPDF {

    @Autowired
    TimbanTimeRecordService timbanTimeRecordService;

    public String reportToPDF(
            TimbanUser timbanUser) {

        List<TimbanTimeRecord> timeRecords = timbanTimeRecordService.getTimeRecordListByTimeSpanAndId(timbanUser.getId(), "month");

        int[][] diffAndWeekDay = timbanTimeRecordService.createDiffAndWeekDayTable(timbanUser.getId(), "month");

        String userName = timbanUser.getUserName();
        int totalTime = timbanTimeRecordService.calculate(timbanUser.getId(), "month");

        return createDocument(userName, diffAndWeekDay, totalTime);
    }

    private String createDocument(String userName, int[][] diffAndWeekDay, int totalTime) {
        String location = null;
        try {

            String path = "timban-web/src/main/resources/static/reports/" + userName.replace(" ", "_");
            location = "/reports/" + userName.replace(" ", "_");

            File theDir = new File(path);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + "/TimeReport.pdf"));

            document.open();

            Font fontBig = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Font fontSmall = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            PdfPTable table = new PdfPTable(2);
            addTableHeader(table);
            addRows(table, diffAndWeekDay);
            table.setSpacingBefore(20);
            table.setSpacingAfter(20);


            Paragraph reportHeader = new Paragraph("Time Report of " + userName, fontBig);
            Paragraph totalTimeRow = new Paragraph("Total working time of month " + LocalDate.now().getMonth() + ": " + LocalTime.ofSecondOfDay(totalTime * 60L), fontBig);
            Paragraph reportFooter = new Paragraph("Generated on " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), fontSmall);

            document.add(reportHeader);
            document.add(table);

            document.add(totalTimeRow);
            document.add(reportFooter);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;

    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Day", "Time Worked")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, int[][] diffAndWeekDay) {
        int rows = diffAndWeekDay.length;
        int cols = diffAndWeekDay[0].length;
        int[][] reversedDiffAndWeekDay = new int[rows][cols];

        for (int i = rows - 1; i >= 0; i--) {
            reversedDiffAndWeekDay[rows - 1 - i] = diffAndWeekDay[i];
        }

        for (int[] i : reversedDiffAndWeekDay) {
            LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i[1]);
            table.addCell(localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            table.addCell(LocalTime.ofSecondOfDay(i[0] * 60L).toString());
        }
    }

}
