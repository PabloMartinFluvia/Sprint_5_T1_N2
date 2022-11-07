package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import com.martinfluviapablo.s5t1n2.controllers.FlorModelAssembler;
import com.martinfluviapablo.s5t1n2.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Operation(
        summary = "Request for one flor",
        description = "When requesting for one specific flor his ID must be specified in the path. \n\n " +
                "If found the response is the flor (with HATEOAs links). \n\n " +
                "If not found or the ID it's not valid the response is a 4XX error.")
@IdPathVariable
@ApiResponses(value = {
        @ApiResponse(
                description = "OK: Model of one flor.",
                responseCode = "200",
                content = @Content(
                        examples = @ExampleObject(
                                name = "OneFlorExample",
                                ref = "#/components/examples/OneFlorExample"),
                        //If in content only examples -> empty schema in documentation (not autodetected)
                        schema = @Schema(implementation = FlorModelAssembler.FlorModel.class))),
        @ApiResponse(
                description = "NOT FOUND: doesn't exist such flor with the provided id.",
                responseCode = "404",
                content = @Content(
                        examples = @ExampleObject(
                                name = "FlorNotFoundErrorExample",
                                ref = "#/components/examples/FlorNotFoundErrorExample"),
                        schema = @Schema(implementation = ErrorResponse.class))),
})
@Target(METHOD)
@Retention(RUNTIME)
public @interface GetOneFlorOperation {
}
