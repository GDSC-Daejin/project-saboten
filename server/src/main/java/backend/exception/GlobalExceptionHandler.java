package backend.exception;

import common.message.BasicResponseMessage;
import common.message.ResponseMessage;
import common.model.reseponse.ApiResponse;
import io.sentry.Sentry;
import io.sentry.SentryLevel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        return makeErrorResponse(e.getErrorMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleArgumentTypeMismatch(final MethodArgumentTypeMismatchException e) {
        final ResponseMessage errorMessage = BasicResponseMessage.BAD_REQUEST;

        Sentry.configureScope(scope -> {
            scope.setLevel(SentryLevel.ERROR);
            scope.setTag("errorResponse", errorMessage.getMessage());
            Sentry.captureMessage(e.getMessage());
        });

        return makeErrorResponse(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException e) {
        Sentry.configureScope(scope -> {
            scope.setLevel(SentryLevel.ERROR);
            Sentry.captureMessage(e.getMessage());
        });

        final ResponseMessage errorMessage = BasicResponseMessage.INVALID_PARAMETER;
        return makeErrorResponse(errorMessage);
    }

    // Request Body와 형식이 맞지 않거나, JSON 형태를 지키지 않았을 경우 발생한다.
    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Sentry.configureScope(scope -> {
            scope.setLevel(SentryLevel.ERROR);
            Sentry.captureMessage(ex.getMessage());
        });

        final ResponseMessage errorMessage = BasicResponseMessage.INVALID_JSON;
        return makeErrorResponse(errorMessage);
    }

    // @Valid에 인한 예외 처리
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        Sentry.configureScope(scope -> {
            scope.setLevel(SentryLevel.ERROR);
            Sentry.captureMessage(e.getMessage());
        });

        final ResponseMessage errorMessage = BasicResponseMessage.INVALID_PARAMETER;
        return makeErrorResponse(errorMessage);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(final Exception ex) {
        Sentry.configureScope(scope -> {
            scope.setLevel(SentryLevel.ERROR);
            Sentry.captureMessage(ex.getMessage());
        });

        final ResponseMessage errorMessage = BasicResponseMessage.INTERNAL_SERVER_ERROR;
        return makeErrorResponse(errorMessage);
    }

    private ResponseEntity<Object> makeErrorResponse(ResponseMessage errorMessage) {
        ApiResponse<?> errorResponse = ApiResponse.withMessage(null, errorMessage);
        // 일단은 statusCode를 NOT_FOUND로 했는데 추후 ApiResponse 에서 status 추가 필요
        return ResponseEntity.status(errorMessage.getStatusCode()).body(errorResponse);
    }
}
