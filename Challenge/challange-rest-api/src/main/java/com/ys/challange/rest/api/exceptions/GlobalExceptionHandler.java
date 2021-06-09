package com.ys.challange.rest.api.exceptions;

import com.ys.challange.rest.api.model.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> generateException(ResponseStatusException re)
    {
        String[] message = re.getMessage().split(" ");

        ErrorDto dto = new ErrorDto();
        dto.setTimestamp(new Date().toString());
        dto.setStatus( String.valueOf( re.getStatus().value()));
        dto.setErrorMessage(message[1]);
        dto.setErrorDescription(re.getReason());
        logger.error("Exception Occured : ",re);

        return new ResponseEntity<ErrorDto>(dto,re.getStatus());
    }
}
