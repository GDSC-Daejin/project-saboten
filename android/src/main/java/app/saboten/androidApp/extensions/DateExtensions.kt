package app.saboten.androidApp.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.math.roundToInt
import kotlin.time.DurationUnit

fun LocalDateTime.asDurationStringFromNow(): String {
    val now = Clock.System.now()
    val duration = now - this.toInstant(TimeZone.UTC)
    // int 로 반올림 (왜냐면 double 이라서 나노초 이하 단위 까지 계산이 되기 때문)
    val days = duration.toDouble(DurationUnit.DAYS).roundToInt()
    val hours = duration.toDouble(DurationUnit.HOURS).roundToInt()
    val minutes = duration.toDouble(DurationUnit.MINUTES).roundToInt()
    val seconds = duration.toDouble(DurationUnit.SECONDS).roundToInt()
    return when {
        days > 0 -> "${days}일 전"
        hours > 0 -> "${hours}시간 전"
        minutes > 0 -> "${minutes}분 전"
        seconds > 0 -> "${seconds}초 전"
        else -> "방금"
    }
}