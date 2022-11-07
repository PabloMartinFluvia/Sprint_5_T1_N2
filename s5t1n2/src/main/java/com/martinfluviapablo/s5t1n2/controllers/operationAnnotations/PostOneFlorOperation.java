package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import com.martinfluviapablo.s5t1n2.controllers.FlorModelAssembler;
import com.martinfluviapablo.s5t1n2.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Operation(
        summary = "Request for Add one flor",
        description = "When requesting for adding one specific flor only name and country must be provided. \n\n " +
                "If the body is valid without conflict the response is 201 CREATED. \n\n " +
                "If the body is valid BUT implies duplicate data the response is a 4XX error.\n\n"+
                "If the body has values for fields pk_FlorID and/or tipusFlor the response is a 4XX error.")
@RequestBody(
        description = "Required JSON to create a flor. DO NOT include pk_FlorID and/or tipusFlor.",
        required = true,
        //content example included in schema
        content = @Content(schema = @Schema(ref = "#/components/schemas/FlorPostSchema")))
@ApiResponses({
        @ApiResponse(
                description = "CREATED",
                responseCode = "201",
                content = @Content(
                        examples = @ExampleObject(
                                name = "OneFlorExample",
                                summary = "201 JSON Body Response",
                                ref = "#/components/examples/OneFlorExample"),
                        schema = @Schema(implementation = FlorModelAssembler.FlorModel.class))),
        @ApiResponse(
                description = "BAD REQUEST: could be due multiple reasons.\n\n" +
                        "String values don't respect contraints.\n\n" +
                        "Has been provided values for fields pk_FlorID and/or tipusFlor.\n\n" +
                        "-> Check message in response.",
                responseCode = "400",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject)),
        @ApiResponse(
                description = "CONFLICT: already exist one flor with identical name and country.",
                responseCode = "409",
                content = @Content(
                        examples = @ExampleObject(
                                name = "FlorDuplicatedErrorExample",
                                ref = "#/components/examples/FlorDuplicatedErrorExample"),
                        schema = @Schema(implementation = ErrorResponse.class))),

})
@Target(METHOD)
@Retention(RUNTIME)
public @interface PostOneFlorOperation {
}
