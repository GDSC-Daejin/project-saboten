package commonClient
import common.model.User
import common.model.reseponse.ApiResponse
import common.model.reseponse.message.UserResponseMessage
import commonClient.extension.isCodeEquals
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertTrue

class ApiResponseTest {

    @Test
    fun `만약 ApiResponse 가 내려왔을때 enum class 와 code 의 비교함수 isCodeEquals 를 true 로 통과해야함`() {

        val json = """
            {
              "data": {
                "id": 100,
                "nickname": "",
                "profile_photo_url": ""
              },
              "code": "USER_NOT_REGISTERED",
              "message": "유저가 가입되지 않았습니다."
            }
        """.trimIndent()

        val parsedResponse = Json.decodeFromString(
            ApiResponse.serializer(User.serializer()),
            json
        )

        println("parsedResponse: $parsedResponse")

        assertTrue(parsedResponse.isCodeEquals(UserResponseMessage.USER_NOT_REGISTERED))
    }

}