package commonClient

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.MockSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import common.model.reseponse.user.Gender
import common.model.reseponse.user.UserInfo
import commonClient.data.cache.MeCache
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
    fun beforeTest() {
        val testSettings = MockSettings()
        meCache = MeCache(testSettings.toSuspendSettings())
    }

    @Test
    fun observeTest() = runTest {

        meCache.save(
            UserInfo(
                id = Random.nextLong(),
                nickname = "Harry",
                profilePhotoUrl = "",
                email = "harry.park@mathpresso.com",
                introduction = "Hello I'm Harry",
                age = 24,
                gender = Gender.M
            )
        )

        val savedMe = meCache.me.firstOrNull()
        println("observeTest : Me=$savedMe")
        assertTrue(savedMe != null)

        meCache.flush()

        val deletedMe = meCache.me.firstOrNull()
        println("observeTest : Me=$deletedMe")
        assertTrue(deletedMe == null)
    }

}