package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.rest.exceptions.InvalidFieldException;
import com.epam.turik.restchat.rest.exceptions.ProblemException;
import com.epam.turik.restchat.rest.objects.ProblemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    // wrong enum
//    @ExceptionHandler(value = BindException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ProblemDTO handleEnum(BindException ex) {
//        return new ProblemDTO("BindException","Wrong ENUM parameter in request", HttpStatus.BAD_REQUEST.value());
//    }
//    @ExceptionHandler(value = UserNotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ExceptionDTO handleUserNotFound(UserNotFoundException e) {
//        return new ExceptionDTO("UserNotFoundException","can't find user with id " + e.getId(), HttpStatus.NOT_FOUND.value());
//    }


    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDTO> handle(MethodArgumentTypeMismatchException exception) {
        InvalidFieldException ex = new InvalidFieldException(exception.getName(), exception.getValue());
        return response(ex);
    }

    @ExceptionHandler(value = ProblemException.class)
    public ResponseEntity<ProblemDTO> handle(ProblemException exception) {
        return response(exception);
    }

    private ResponseEntity<ProblemDTO> response(ProblemException exception) {
        log.error("{} ({})", exception.getTitle(), exception.getDetail(), exception);

        ProblemDTO dto = new ProblemDTO();
        dto.setTitle(exception.getTitle());
        dto.setDetail(exception.getDetail());
        dto.setType(exception.getType());
        dto.setViolations(exception.getViolations());
        MediaType   contentType = MediaType.APPLICATION_PROBLEM_JSON;
        HttpHeaders headers     = new HttpHeaders();
        headers.setContentType(contentType);
        return new ResponseEntity<>(dto, headers, exception.getStatus());
    }

}
