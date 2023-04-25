package app.saboten.androidApp.extensions

import java.time.Duration
import java.time.LocalDateTime

fun LocalDateTime.toDuration(): Duration = Duration.between(this, LocalDateTime.now())

fun Duration.toAfterCreatedAt(): String {
    return when {
        this.toDays() != 0L -> {
            "${this.toDays()}일 전"
        }
        this.toHours() != 0L ->{
            "${this.toHours()}시간 전"
        }
        this.toMinutes() != 0L -> {
            "${this.toMinutes()}분 전"
        }
        else -> {
            "${this.seconds}초 전"
        }
    }
}