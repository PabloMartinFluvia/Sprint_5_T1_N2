package com.martinfluviapablo.s5t1n2.exceptions;

public class DuplicateDataException extends RuntimeException{

    public DuplicateDataException(int id){
        super("Ja existeix una altra flor (id: "+id+") amb idèntic nom i país.");
    }
}
