package backend

import backend.user.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfiguration {

    @Bean
    fun routes(
        userHandler: UserHandler
    ) = Routes.router(
        userHandler = userHandler
    )

}