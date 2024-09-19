package org.finterest.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionAdvice {
    @ExceptionHandler(PasswordMissmatchException.class)
    public ResponseEntity<?> handlePasswordError(Exception ex) {
        return ResponseEntity.status(400)
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleError(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(500)
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(ex.getMessage());
    }

}
