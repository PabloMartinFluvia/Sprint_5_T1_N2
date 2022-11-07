package com.martinfluviapablo.s5t1n2.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "Error Response")
@Getter
public class ErrorResponse {

    @Schema(description = "Type of exception")
    private final String type;

    @Schema(description = "Cause")
    private final String message;

    @Schema(description = "HTTP error code")
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
