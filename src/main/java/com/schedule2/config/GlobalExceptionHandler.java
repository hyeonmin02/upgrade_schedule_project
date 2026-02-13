package com.schedule2.config;

import com.schedule2.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // DTO에 선언된 @NotBlank, @Size 등 Validation 검증이 실패하면 Spring이 알아서 MethodArgumentNotValidException 자동 발생
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException e) {

                        //검증 결과 모음상자.실패한필드가져와.DTO에 적어둔 에러메시지값가져와 담음
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }
}


