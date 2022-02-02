package backend.docs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

private const val API_VERSION = "1.0"

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun sabotenApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            //.apis(RequestHandlerSelectors.basePackage("server.src.main.java.backend.controller"))
            .build()
            .apiInfo(this.metaInfo())
    }

    private fun metaInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Project Saboten")
            .description("GDSC-DJU 선인장 (선택장애 인간들을 위한 장) 프로젝트")
            .version(API_VERSION)
            .termsOfServiceUrl("https://github.com/GDSC-Daejin/project-saboten.git")
            .license("MIT")
            .licenseUrl("https://license.com")
            .contact(
                Contact(
                    "Contact Us",
                    "https://github.com/GDSC-Daejin/project-saboten.git",
                    "shinequasar@naver.com"
                )
            )
            .build()
    }




}