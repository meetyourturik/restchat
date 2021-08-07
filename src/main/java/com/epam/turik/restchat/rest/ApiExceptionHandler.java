package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.rest.exceptions.InvalidEnumFieldException;
import com.epam.turik.restchat.rest.exceptions.InvalidFieldException;
import com.epam.turik.restchat.rest.exceptions.ProblemException;
import com.epam.turik.restchat.rest.exceptions.ShouldHaveBeenThrownEarlierException;
import com.epam.turik.restchat.rest.objects.ProblemDTO;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = PSQLException.class)
    public ResponseEntity<ProblemDTO> handle(PSQLException exception) {
        log.warn("psql");
        ShouldHaveBeenThrownEarlierException ex = new ShouldHaveBeenThrownEarlierException(exception.getServerErrorMessage().getDetail());
        return response(ex);
    }

    // должен ловиться раньше видимо
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ProblemDTO> handle(IllegalArgumentException exception) {
        log.warn("illegal arg");
        ShouldHaveBeenThrownEarlierException ex = new ShouldHaveBeenThrownEarlierException(exception.getLocalizedMessage());
        return response(ex);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ProblemDTO> handle(BindException exception) {
        InvalidEnumFieldException ex = new InvalidEnumFieldException(exception.getTarget().getClass().getName(), exception.getFieldErrors());
        return response(ex);
    }

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
