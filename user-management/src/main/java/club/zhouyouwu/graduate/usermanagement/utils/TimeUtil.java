package club.zhouyouwu.graduate.usermanagement.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static String timeFormat1 = "yyyy-MM-dd HH:mm:ss";
    public static String timeFormat2 = "yyyy-MM-dd";

    public static String currentTimeConvTo(String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return LocalDateTime.now().format(formatter);
    }
}
