package commonClient

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.MockSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import common.model.Gender
import common.model.reseponse.user.UserInfoResponse
import commonClient.data.cache.MeCache
import commonClient.utils.JsName
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalSettingsApi::class)
class MeCacheTest {

    lateinit var meCache: MeCache

    @BeforeTest
    fun setup() {
        val testSettings = MockSettings()
        meCache = MeCache(testSettings.toSuspendSettings())
    }

    @Test
    @JsName("UserCacheTest")
    fun `GIVEN UserInfo cached and delete WHEN UserInfo Flow is observing Then UserInfo Flow value should be changed`() = runTest {

        // Given
        val userInfoResponse = UserInfoResponse(
            id = Random.nextLong(),
            nickname = "Harry",
            profilePhotoUrl = "",
            email = "harry.park@mathpresso.com",
            introduction = "Hello I'm Harry",
            age = 24,
            gender = Gender.M
        )

        // When
        meCache.save(userInfoResponse)

        // Then
        val savedMe = meCache.me.firstOrNull()
        println("observeTest : Me=$savedMe")
        assertTrue(savedMe != null)

        // When
        meCache.flush()

        // Then
        val deletedMe = meCache.me.firstOrNull()
        println("observeTest : Me=$deletedMe")
        assertTrue(deletedMe == null)
    }

}