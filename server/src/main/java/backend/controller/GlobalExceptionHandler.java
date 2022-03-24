package backend.controller;

import common.message.BasicResponseMessage;
import common.model.reseponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(NoSuchElementException e) {
        ApiResponse<?> errorResponse = ApiResponse.withMessage(null, BasicResponseMessage.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
