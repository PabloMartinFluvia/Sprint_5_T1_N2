package com.martinfluviapablo.s5t1n2.exceptions;

public class FlorNotFoundException extends RuntimeException{

    public FlorNotFoundException(int id){
        super("No existeix cap flor amb id: " + id);
    }
}
