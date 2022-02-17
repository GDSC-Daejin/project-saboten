package backend.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class GlobalPropertySourceTest {
    @Autowired
    val test = GlobalPropertySource()

    @Autowired
    var dataSource: DataSource

    @Autowired
    var jdbcTemplate: JdbcTemplate

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

        // when

        // then

    }
}