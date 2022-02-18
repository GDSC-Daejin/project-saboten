package backend.config

import backend.model.TestModel
import backend.repository.TestRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@SpringBootTest
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
internal class GlobalPropertySourceTest {
    @Autowired
    val test = GlobalPropertySource()

//    @Autowired
//    lateinit var dataSource : DataSource
//
//    @Autowired
//    lateinit var jdbcTemplate : JdbcTemplate

    @Autowired
    lateinit var testRepository: TestRepository

    @Test
    fun `should do something`() {
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
    fun `Connect Database`() {
        // given
        val testEntity = TestModel()

        // when
        val newEntity =  testRepository.save(testEntity)

        // then
        println(newEntity.toString())
        assertThat(newEntity).isNotNull
    }
}