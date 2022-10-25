package com.martinfluviapablo.s5t1n2.exceptions;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String type;
    private final String message;
    private final Integer code;

    public ErrorResponse(Exception exception, Integer code) {
        this.type = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.code = code;
    }

    @Override
    public String toString() {
        return "ERROR: {" +
                " Type ='" + type +
                "; Message='" + message +
                "; Code=" + code +
                " }";
    }
}
