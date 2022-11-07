package com.martinfluviapablo.s5t1n2.controllers.operationAnnotations;

import com.martinfluviapablo.s5t1n2.controllers.FlorModelAssembler;
import com.martinfluviapablo.s5t1n2.model.dto.FlorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Operation(
        summary = "Request for all flors",
        description = "When requesting for all flors the expected response is all resources of Flor" +
                " (with HATEOAs links). If none found is returned an empty array and a self link.")
@ApiResponse(
        description = "OK: CollectionModel of all flors.",
        responseCode = "200",
        content = @Content(
                examples = @ExampleObject(
                        name = "AllFlorsExample",
                        ref = "#/components/examples/AllFlorsExample"),
                //If in content only examples -> empty schema in documentation (not autodetected)
                schema = @Schema(implementation = FlorModelAssembler.FlorCollection.class)))
@Target(METHOD)
@Retention(RUNTIME)
public @interface GetAllFlorOperation {
}
