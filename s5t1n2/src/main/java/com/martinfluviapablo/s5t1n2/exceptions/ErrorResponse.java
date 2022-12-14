package com.martinfluviapablo.s5t1n2.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.List;

@Schema(name = "Error Response")
@Getter
public class ErrorResponse {

    @Schema(description = "Type of exception")
    private final String type;

    @Schema(description = "Cause(s)")
    private final List<String> message;

    @Schema(description = "HTTP error code")
    private final Integer code;

    public ErrorResponse(Exception exception, Integer code) {
        this.type = exception.getClass().getSimpleName();
        this.message = List.of(exception.getMessage());
        this.code = code;
    }

    public ErrorResponse(Exception exception, List<String> errors, Integer code){
        this.type = exception.getClass().getSimpleName();
        this.message = errors;
        this.code = code;
    }
}
