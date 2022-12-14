package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import com.martinfluviapablo.s5t1n2.controllers.FlorModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Operation(
        summary = "Request for get one flor.",
        description = "The flor's ID (valid) must be specified as path variable. \n\n " +
                "Success: flor resource (with self relation link). \n\n " +
                "Error(s): if flor not found OR ID value not valid.")
@IdPathVariable // documentation ID in path variable + response if not valid
@ApiResponse(
        description = "OK.",
        responseCode = "200",
        content = @Content(
                examples = @ExampleObject(
                        name = "OneFlorExample",
                        ref = "#/components/examples/OneFlorExample"),
                schema = @Schema(implementation = FlorModelAssembler.FlorModel.class)))
@FlorNotFoundResponse // 404 response id doesn't mach any flor
@Target(METHOD)
@Retention(RUNTIME)
public @interface GetOneFlorOperation {
}
