package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import com.martinfluviapablo.s5t1n2.controllers.FlorModelAssembler;
import com.martinfluviapablo.s5t1n2.exceptions.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
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
        summary = "Request for add one flor",
        description = "Only name and country must be provided in body. \n\n " +
                "Success: flor resource (with self relation link). \n\n " +
                "Error(s): body request: has values for fields pk_FlorID and/or tipusFlor \n\n" +
                "OR fields value(s) name and/or country are not valid OR implies duplicate data (both).")
@RequestBody(
        description = "Required JSON Body to add a flor. Fields to NOT include: pk_FlorID and/or tipusFlor.",
        required = true,
        content = @Content(schema = @Schema(ref = "#/components/schemas/FlorPostSchema")))
                //content example included in schema
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
                        "Required fields not provided OR values don't respect constraints.\n\n" +
                        "OR Has been provided values for fields pk_FlorID and/or tipusFlor.",
                responseCode = "400",
                content = @Content(
                        examples = @ExampleObject(
                                name = "InvalidPostErrorExample",
                                ref = "#/components/examples/InvalidPostErrorExample"),
                        schema = @Schema(implementation = ErrorResponse.class))),
})
@DuplicatedDataResponse // response 409 when new nom and pais identical in other flor (distinct id)
@Target(METHOD)
@Retention(RUNTIME)
public @interface PostOneFlorOperation {
}
