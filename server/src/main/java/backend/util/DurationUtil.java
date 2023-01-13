package backend.util;

import common.model.request.Duration;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.TimeZone;

public class DurationUtil {

    public static boolean isIncludeDuration(LocalDateTime postRegistTime, Duration duration) {
        // else if 극혐
        if(duration == Duration.DAY){
            return calculateDay(postRegistTime);
        }
        else if(duration == Duration.WEEK){
            return calculateWeek(postRegistTime);
        }
        else if(duration == Duration.MONTH) {
            return calculateMonth(postRegistTime);
        }

        // ALL 일때
        return true;
    }

    private static boolean calculateDay(LocalDateTime postRegistTime) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        if(postRegistTime.getDayOfMonth() == currentTime.getDayOfMonth()) {
            return true;
        }

        return false;
    }

    private static boolean calculateWeek(LocalDateTime postRegistTime) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startTime = currentTime.minusDays(7);

        if(startTime.getDayOfYear() <= postRegistTime.getDayOfYear() &&
                postRegistTime.getDayOfYear() <= currentTime.getDayOfYear()) {
            return true;
        }

        return false;
    }

    private static boolean calculateMonth(LocalDateTime postRegistTime) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startTime = currentTime.minusMonths(1);

        int startTimeDay = startTime.getDayOfYear();
        int postTimeDay = postRegistTime.getDayOfYear();
        int currentTimeDay = currentTime.getDayOfYear();

        if(currentTime.getMonth() == Month.JANUARY) {
            postTimeDay += 365;
            currentTimeDay += 365;
        }

        if(startTimeDay <= postTimeDay &&
                postTimeDay <= currentTimeDay) {
            return true;
        }

        return false;
    }

}
