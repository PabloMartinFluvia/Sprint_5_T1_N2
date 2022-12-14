package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import com.martinfluviapablo.s5t1n2.controllers.FlorModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Operation(
        summary = "Request for get all flors.",
        description = "Success: All resources of flor (each one with link to self relation)." +
                " If none found the array field (florsList) is empty.")
@ApiResponse(
        description = "OK",
        responseCode = "200",
        content = @Content(
                schema = @Schema(implementation = FlorModelAssembler.FlorsCollection.class),
                examples = @ExampleObject(
                        name = "AllFlorsExample",
                        ref = "#/components/examples/AllFlorsExample")))
@Target(METHOD)
@Retention(RUNTIME)
public @interface GetAllFlorOperation {
}
