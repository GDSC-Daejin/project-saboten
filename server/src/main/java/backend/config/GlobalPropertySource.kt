package backend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:db.config.properties")
@ConfigurationProperties("spring.datasource")
class GlobalPropertySource {
    @Value("\${driverClassName}")
    private lateinit var driverClassName: String

    @Value("\${url}")
    private lateinit var url : String

    @Value("\${username}")
    private lateinit var username : String

    @Value("\${password}")
    private lateinit var password : String

    fun getDriverClassName() : String = driverClassName

    fun getUrl() : String = url

    fun getUsername() : String = username

    fun getPassword() : String = password
}