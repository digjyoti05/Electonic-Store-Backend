package com.Digjyoti.electronic.store.exceptions;

import com.Digjyoti.electronic.store.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.aspectj.lang.reflect.DeclareAnnotation.Kind.Field;
@RestControllerAdvice

public class GlobalExceptionHandler {
    private  Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundExcepation.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundException(ResourceNotFoundExcepation ex){
        logger.info("Exception Handler Invoked !!");
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

    }
//    Method Not Found Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response=new HashMap<>();
        allErrors.stream().forEach(objectError -> {String message= objectError.getDefaultMessage();
           String field= ((FieldError)objectError).getField();
            response.put(field,message);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }
//    handling Bad Api exception
    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> BadApiException(BadApiRequestException ex){
        logger.info("Bad Api request !!");
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }

}
