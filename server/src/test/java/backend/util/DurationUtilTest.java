package backend.util;

import common.model.request.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class DurationUtilTest {

    // Hot Post 게시물 조회 테스트는 Controller 단위가 아닌 유닛 단위 테스트가 훨씬 간편하기 때문에
    // 유닛 테스트로 진행을 하였음.
    @Nested
    @DisplayName("DurationUtil Day Test")
    class DurationUtilDAY {
        @Test
        public void DAY_포함됨() {
            LocalDateTime todayTime = LocalDateTime.now();
            todayTime = todayTime.minusMinutes(1);

            boolean result = DurationUtil.isIncludeDuration(todayTime, Duration.DAY);
            assertThat(result).isEqualTo(true);
        }

        @Test
        public void DAY_포함되지않음() {
            LocalDateTime todayTime = LocalDateTime.now();
            todayTime = todayTime.minusDays(1);

            boolean result = DurationUtil.isIncludeDuration(todayTime, Duration.DAY);
            assertThat(result).isEqualTo(false);
        }
    }

    @Nested
    @DisplayName("DurationUtil Week Test")
    class DurationUtilWeek {
        @Test
        public void WEEK_포함됨() {
            LocalDateTime weekTime = LocalDateTime.now();
            weekTime = weekTime.minusDays(4);

            boolean result = DurationUtil.isIncludeDuration(weekTime, Duration.WEEK);
            assertThat(result).isEqualTo(true);
        }

        @Test
        public void WEEK_포함되지않음() {
            LocalDateTime weekTime = LocalDateTime.now();
            weekTime = weekTime.minusDays(9);

            boolean result = DurationUtil.isIncludeDuration(weekTime, Duration.WEEK);
            assertThat(result).isEqualTo(false);
        }
    }

    @Nested
    @DisplayName("DurationUtil Month Test")
    class DurationUtilMonth {
        @Test
        public void MONTH_포함됨() {
            LocalDateTime monthTime = LocalDateTime.now();
            monthTime = monthTime.minusDays(15);

            boolean result = DurationUtil.isIncludeDuration(monthTime, Duration.MONTH);
            assertThat(result).isEqualTo(true);
        }

        @Test
        public void MONTH_포함되지않음() {
            LocalDateTime monthTime = LocalDateTime.now();
            monthTime = monthTime.minusMonths(3);

            boolean result = DurationUtil.isIncludeDuration(monthTime, Duration.MONTH);
            assertThat(result).isEqualTo(false);
        }
    }

    @Nested
    @DisplayName("DurationUtil All Test")
    class DurationUtilAll {
        @Test
        public void ALL_포함됨() {
            LocalDateTime allTime = LocalDateTime.now();
            allTime = allTime.minusMonths(8);

            boolean result = DurationUtil.isIncludeDuration(allTime, Duration.ALL);
            assertThat(result).isEqualTo(true);
        }

    }
}
