package backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        ObjectMapperConfig.ObjectMapperSnakeCase.class,
})
public @interface ObjectMapperConfig {
    class ObjectMapperSnakeCase {
        // TODO : 임시방편으로 강제로 스네이크 표시로 변환하게는 했는데....
        // 코틀린의 SerialName을 자바 ObjectMapper가 인식하게 하는 방법을 찾아야할듯... (아무리 찾아도 안되더라고요..)
        @Bean
        ObjectMapper objectMapperSnakeCase() {
            return new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        }
    }
}
