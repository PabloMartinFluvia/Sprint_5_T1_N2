package com.martinfluviapablo.s5t1n2.exceptions;

public class DuplicateDataException extends RuntimeException{
    private static final String DESCRIPTION = "Flor Conflict Exception";

    public DuplicateDataException(String detail){
        super(DESCRIPTION + ". "+detail);
    }
}
