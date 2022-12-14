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
        summary = "Request for replace/update one flor",
        description = "Only id, name and country must be provided in body. \n\n " +
                "Success: flor resource (with self relation link). \n\n " +
                "Error(s): body request: has value for field tipusFlor \n\n" +
                "OR fields value(s) id, name and/or country are not valid OR update implies duplicate data (both). \n\n" +
                "OR flor resource to replace not found.")
@RequestBody(
        description = "Required JSON to update a flor. DO NOT include tipusFlor.",
        required = true,
        content = @Content(schema = @Schema(ref = "#/components/schemas/FlorPutSchema")))
                //content example included in schema
@ApiResponses({
        @ApiResponse(
                description = "OK",
                responseCode = "200",
                content = @Content(
                        examples = @ExampleObject(
                                name = "UpdatedFlorExample",
                                summary = "200 JSON Body Response",
                                ref = "#/components/examples/UpdatedFlorExample"),
                        schema = @Schema(implementation = FlorModelAssembler.FlorModel.class))),
        @ApiResponse(
                description =  "BAD REQUEST: could be due multiple reasons.\n\n" +
                        "Required fields not provided OR values don't respect constraints.\n\n" +
                        "OR Has been provided value for field tipusFlor.",
                responseCode = "400",
                content = @Content(
                        examples = @ExampleObject(
                                name = "InvalidPutErrorExample",
                                ref = "#/components/examples/InvalidPutErrorExample"),
                        schema = @Schema(implementation = ErrorResponse.class))),
})
@FlorNotFoundResponse // 404 response id doesn't mach any flor
@DuplicatedDataResponse // response 409 when new nom and pais identical in other flor (distinct id)
@Target(METHOD)
@Retention(RUNTIME)
public @interface PutOneFlorOperation {
}
