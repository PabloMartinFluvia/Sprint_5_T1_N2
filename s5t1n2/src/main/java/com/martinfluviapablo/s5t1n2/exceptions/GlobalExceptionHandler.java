package com.martinfluviapablo.s5t1n2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice // returned methods values are http body response
public class GlobalExceptionHandler{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class, // value type provided impossible formatting to required type
            MethodArgumentNotValidException.class, // @Valid argument fails
            ConstraintViolationException.class, //failed validation constraint  OK
            HttpMessageNotReadableException.class // When not able to read HttpMessage (ex: POST without body)
            })
    public ErrorResponse handleBadRequest(Exception ex){
        return new ErrorResponse(ex,HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
            {NoHandlerFoundException.class, // no endpoint mapped for the request
            FlorNotFoundException.class})   // no resource found
    public ErrorResponse handleNotFound(Exception ex){
        return new ErrorResponse(ex,HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
            (DuplicateDataException.class) // Trying to save one resource with identical data to other resource (only different id)
    public ErrorResponse handleConflict(Exception ex){
        return new ErrorResponse(ex,HttpStatus.CONFLICT.value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleCriticException(Exception ex){
        ex.printStackTrace(); //must be solved or handled when is detected
        return new ErrorResponse(ex,HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
