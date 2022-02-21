package backend.config

import backend.model.TestModel
import backend.repository.TestRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
//@DataJpaTest
internal class GlobalPropertySourceTest {
    @Autowired
    val test = GlobalPropertySource()

    @Autowired
    lateinit var testRepository: TestRepository

    @Test
    fun `read database info`() {
        // given
        val driverName = test.getDriverClassName()
        val url = test.getUrl()
        val userName = test.getUsername()
        val password = test.getPassword()

        // when
        println(test.toString())

        // then
        assertThat(driverName).isNotNull
        assertThat(url).isNotNull
        assertThat(userName).isNotNull
        assertThat(password).isNotNull
    }

    @Test
    fun `Insert Database Check`() {
        // given
        val testEntity = TestModel()
        testEntity.username = "panda"

        // when
        val newEntity =  testRepository.save(testEntity)

        // then
        println(newEntity.id)
        assertThat(newEntity).isNotNull

        val existingEntities = testRepository.findByUsername(newEntity.username)
        println(existingEntities)
        existingEntities.forEach {
            println(it.id)
        }
        assertThat(existingEntities).isNotEmpty
    }
}