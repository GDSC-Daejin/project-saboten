package commonClient
import common.model.reseponse.user.UserResponse
import common.model.reseponse.ApiResponse
import common.message.UserResponseMessage
import commonClient.extension.isCodeEquals
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertTrue

class IsoStringToLocalDateTimeTest {

    @Test
    fun testLocalDateTime() {
        val isoString = "2022-05-03T12:00:00.489099"
        println(LocalDateTime.parse(isoString).toString())
        assertTrue(LocalDateTime.parse(isoString).toString().isNotEmpty())
    }

}