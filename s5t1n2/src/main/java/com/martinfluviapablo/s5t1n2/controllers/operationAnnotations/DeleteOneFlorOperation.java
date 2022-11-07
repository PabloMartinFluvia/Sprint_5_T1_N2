package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Operation(
        summary = "Request for Delete one flor",
        description = "When requesting for deleting one specific flor his ID must be specified in the path. \n\n " +
                "If the ID is valid the response is 204 NO CONTENT (even if found or not). \n\n " +
                "If the ID it's not valid the response is a 4XX error.")
@IdPathVariable
@ApiResponse(description = "NO CONTENT: Success, if existed it has been deleted.", responseCode = "204")
@Target(METHOD)
@Retention(RUNTIME)
public @interface DeleteOneFlorOperation {
}
