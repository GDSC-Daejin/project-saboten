package backend.util;

import common.model.request.Duration;

import java.time.LocalDateTime;
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

        if(startTime.getDayOfMonth() <= postRegistTime.getDayOfMonth() &&
                postRegistTime.getDayOfMonth() <= currentTime.getDayOfMonth()) {
            return true;
        }

        return false;
    }

    private static boolean calculateMonth(LocalDateTime postRegistTime) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startTime = currentTime.minusMonths(1);

        if(startTime.getDayOfMonth() <= postRegistTime.getDayOfMonth() &&
            postRegistTime.getDayOfMonth() <= currentTime.getDayOfMonth()) {
            return true;
        }

        return false;
    }

}
