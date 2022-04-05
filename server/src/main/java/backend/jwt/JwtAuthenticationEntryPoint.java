package backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.message.BasicResponseMessage;
import common.message.ResponseMessage;
import common.model.reseponse.ApiResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        ResponseMessage errorMessage = BasicResponseMessage.UNAUTHORIZED;
        ApiResponse apiResponse = ApiResponse.withMessage(null, errorMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(apiResponse);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorMessage.getStatusCode());
        response.getWriter().write(json);
    }
}