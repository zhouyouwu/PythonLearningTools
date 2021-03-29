package club.zhouyouwu.graduate.knowledgemanagement.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class DateUtil {

    public static List<String> calcTimeRange(String startTime, String endTime){

        List<String> dateList = new LinkedList<>();

        LocalDate date1 = LocalDate.parse(startTime);
        LocalDate date2 = LocalDate.parse(endTime);
        while (date1.compareTo(date2) <= 0){
            dateList.add(date1.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            date1 = date1.plusDays(1);
        }

        return dateList;
    }
}
