package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import com.martinfluviapablo.s5t1n2.exceptions.ErrorResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ApiResponse(
        description = "NOT FOUND: none flor matches with the provided ID.",
        responseCode = "404",
        content = @Content(
                examples = @ExampleObject(
                        name = "FlorNotFoundErrorExample",
                        ref = "#/components/examples/FlorNotFoundErrorExample"),
                schema = @Schema(implementation = ErrorResponse.class)))
@Target({PARAMETER, METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface FlorNotFoundResponse {
}
