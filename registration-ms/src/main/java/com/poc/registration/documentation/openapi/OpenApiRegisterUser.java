package com.poc.registration.documentation.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn.*;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.poc.registration.domain.User;
import java.lang.annotation.*;

@Operation(summary = "Registers a User by creating a User resource. After successful creation a welcome email is sent to the user")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created",
                content = {@Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = User.class)))}),
        @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content)})
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface OpenApiRegisterUser {
}