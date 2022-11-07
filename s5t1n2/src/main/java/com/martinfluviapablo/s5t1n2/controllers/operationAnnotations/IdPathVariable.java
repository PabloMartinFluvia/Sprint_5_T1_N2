package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import com.martinfluviapablo.s5t1n2.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Parameter(
        name = "id",
        in = ParameterIn.PATH,
        required = true,
        description = "Valid path variable: {id} > 0",
        schema = @Schema(
                type = "integer",
                minimum = "1"))
@ApiResponse(
        description = "BAD REQUEST: Invalid ID.",
        responseCode = "400",
        content = @Content(
                examples = @ExampleObject(
                        name = "InvalidIdErrorExample",
                        ref = "#/components/examples/InvalidIdErrorExample"),
                schema = @Schema(implementation = ErrorResponse.class)))
@Target({PARAMETER, METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdPathVariable {
}
