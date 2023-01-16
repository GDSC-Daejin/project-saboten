package backend.util;

import common.model.request.Duration;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

public class DurationUtil {

    public static boolean isIncludeDuration(LocalDateTime postRegistTime, Duration duration) {
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

        return isIncludePostTimeInDuration(startTime, postRegistTime, currentTime);
    }

    private static boolean calculateMonth(LocalDateTime postRegistTime) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startTime = currentTime.minusMonths(1);

        return isIncludePostTimeInDuration(startTime, postRegistTime, currentTime);
    }

    private static boolean isIncludePostTimeInDuration(LocalDateTime startTime, LocalDateTime postRegistTime, LocalDateTime currentTime) {
        int startTimeDay = startTime.getDayOfYear();
        int postTimeDay = postRegistTime.getDayOfYear();
        int currentTimeDay = currentTime.getDayOfYear();

        if(startTime.getMonth() == Month.DECEMBER && currentTime.getMonth() == Month.JANUARY) {
            if(postRegistTime.getMonth() == Month.DECEMBER) {
                currentTimeDay += 365;
            }
            else if(postRegistTime.getMonth() == Month.JANUARY) {
                postTimeDay += 365;
                currentTimeDay += 365;
            }
        }

        return checkIncludeDuration(startTimeDay, postTimeDay, currentTimeDay);
    }

    private static boolean checkIncludeDuration(int startTimeDay, int postTimeDay, int currentTimeDay) {
        if(startTimeDay <= postTimeDay && postTimeDay <= currentTimeDay) {
            return true;
        }

        return false;
    }

}
