package backend.config

import backend.model.TestModel
import backend.repository.TestRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import javax.transaction.Transactional

@SpringBootTest
@ActiveProfiles("dev")
//@DataJpaTest
@Transactional
internal class DatabaseConfigTest {
    @Autowired
    lateinit var testRepository: TestRepository

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