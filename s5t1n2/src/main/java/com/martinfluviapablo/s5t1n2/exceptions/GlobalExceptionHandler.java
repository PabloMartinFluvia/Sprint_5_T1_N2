package com.martinfluviapablo.s5t1n2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice // returned methods values are http body response
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // value type provided impossible formatting to required type
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) throws NullPointerException{
        List<String> message = List.of(
                    ex.getName() + " ha de ser del tipus " + ex.getRequiredType().getName());
        return new ErrorResponse(ex, message, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @Valid argument fails
    @ExceptionHandler(MethodArgumentNotValidException.class )
    protected ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return new ErrorResponse(ex,errors, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class )
    public ErrorResponse handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(
                    //violation.getRootBeanClass().getName() + " " +
                    //violation.getPropertyPath() + ": " +
                    violation.getMessage());
        }
        //ex.getConstraintViolations().stream().map(cv -> cv.getMessage()).toList(); //alternativa per aconseguir la List
        return new ErrorResponse(ex,errors,HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    // When not able to read HttpMessage (ex: POST without body)
    public ErrorResponse handleBadRequest(HttpMessageNotReadableException ex){
        //TODO: customize message (too much info showed)
        return new ErrorResponse(ex, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NoHandlerFoundException.class, // no endpoint mapped for the request
            FlorNotFoundException.class // flor resource not found
            })
    public ErrorResponse handleNotFound(Exception ex){
        return new ErrorResponse(ex,HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateDataException.class)
    // Trying to save one resource with identical data to other resource (only different id)
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
