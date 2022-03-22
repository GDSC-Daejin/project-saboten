import org.gradle.api.artifacts.dsl.DependencyHandler

object SpringFox {
    // swagger 3.0, swaggerUi 3.0 같이 추가 해줌.
    const val swagger3 = "io.springfox:springfox-boot-starter:_"
}

object Mockk {
    const val mockk = "io.mockk:mockk:_"
}

object Postgresql {
    const val postgre = "org.postgresql:postgresql:_"
}

object Lombok {
    const val lombok = "org.projectlombok:lombok:_"
}

object H2 {
    const val h2 = "com.h2database:h2:_"
}

object SpringFramework {
    object Boot {
        const val bootTest = "org.springframework.boot:spring-boot-starter-test"
        const val jpa = "org.springframework.boot:spring-boot-starter-data-jpa"
    }
}

object Sentry {
    const val sentry = "io.sentry:sentry-spring-boot-starter:_"
    const val logback = "io.sentry:sentry-logback:_"
}

// serverDependency로 하나로 쓸까, 아니면 기능별로 분리해서 관리할까 고민중
fun DependencyHandler.serverDependency() {
    add("implementation", SpringFramework.Boot.jpa)
    add("implementation", Postgresql.postgre)
    add("implementation", Lombok.lombok)
    add("implementation", SpringFox.swagger3)
    add("implementation", Sentry.sentry)
    add("implementation", Sentry.logback)

    add("testImplementation", SpringFramework.Boot.bootTest)
    add("testImplementation", Mockk.mockk)

    add("annotationProcessor", Lombok.lombok)

    add("runtimeOnly", H2.h2)
}