package backend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource


@Configuration
@PropertySource("classpath:config.properties")
// @ConfigurationProperties("spring.datasource")
class GlobalPropertySource {
    @Value("\${spring.datasource.driverClassName}")
    private lateinit var driverClassName: String

    @Value("\${spring.datasource.url}")
    private lateinit var url : String

    @Value("\${spring.datasource.username}")
    private lateinit var username : String

    @Value("\${spring.datasource.password}")
    private lateinit var password : String

    fun getDriverClassName() : String = driverClassName

    fun getUrl() : String = url

    fun getUsername() : String = username

    fun getPassword() : String = password

//    @Bean
//    fun dataSource(): DataSource? {
//        val dataSource = DriverManagerDataSource()
//        dataSource.setDriverClassName(driverClassName)
//        dataSource.url = url
//        dataSource.username = username
//        dataSource.password = password
//        return dataSource
//    }

    override fun toString(): String {
        return "driverClassName = ${driverClassName}, url = ${url}, username = ${username}, password = ${password}"
    }
}