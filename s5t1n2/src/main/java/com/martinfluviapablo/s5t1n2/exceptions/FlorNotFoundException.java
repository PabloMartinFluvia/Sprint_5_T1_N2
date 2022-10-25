package com.martinfluviapablo.s5t1n2.exceptions;

public class FlorNotFoundException extends RuntimeException{

    private static final String DESCRIPTION = "Flor Not Found Exception";

    public FlorNotFoundException(String detail){
        super(DESCRIPTION + ". "+detail);
    }
}
