package backend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource

@Configuration
@Profile("dev")
//@PropertySource("classpath:application-prod.properties")
class DatabaseConfig {
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

    override fun toString(): String {
        return "driverClassName = ${driverClassName}, url = ${url}, username = ${username}, password = ${password}"
    }
}