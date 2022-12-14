package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Operation(
        summary = "Request for delete one flor",
        description = " The flor's ID (valid) must be specified as path variable.\n\n"+
                "Success: ID value valid (even if found or not).\n\n"+
                "Error(s): ID value not valid.")
@ApiResponse(
        description = "NO CONTENT. Success: flor with the provided id has been deleted (if existed).",
        responseCode = "204")
@IdPathVariable // documentation ID in path variable + response if not valid
@Target(METHOD)
@Retention(RUNTIME)
public @interface DeleteOneFlorOperation {
}
