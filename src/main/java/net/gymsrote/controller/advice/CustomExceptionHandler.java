package net.gymsrote.controller.advice;
import lombok.Data;
import net.gymsrote.controller.advice.exception.CommonRestException;
import net.gymsrote.controller.advice.exception.CommonRuntimeException;
import net.gymsrote.controller.advice.exception.DataConflictException;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.advice.exception.RemoteUploadException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.LoginException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidInputDataException.class,
    	LoginException.class,
    	RemoteUploadException.class,
    	CommonRestException.class,
    	CommonRuntimeException.class,
    	DataConflictException.class,
    	InvalidInputDataException.class
    	})
    ResponseEntity<?> offerNotValidHandler(Exception exc, ServletWebRequest request) {

        APIError apiError = new APIError();

        apiError.setTimeStamp(LocalDateTime.now());
        apiError.setPathUri(request.getDescription(true));
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setErrors(Arrays.asList(exc.getMessage()));

        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> errors = fieldErrors.stream()
                .map(err -> err.getField() + " : " + err.getDefaultMessage())
                .collect(Collectors.toList());


        APIError apiError = new APIError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setTimeStamp(LocalDateTime.now());
        apiError.setPathUri(request.getDescription(false));
        apiError.setErrors(errors);

        return new ResponseEntity<>(apiError, headers, apiError.getStatus());
    }
}

@Data
class APIError {
    private HttpStatus status;
    private List<String> errors;
    private LocalDateTime timeStamp;
    private String pathUri;
}

