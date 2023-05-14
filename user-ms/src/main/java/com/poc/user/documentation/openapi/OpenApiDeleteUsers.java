package com.poc.user.documentation.openapi;

import com.poc.user.domain.response.ApiErrorResponse;
import com.poc.user.domain.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Operation(summary = "Deletes users  (the users remains on DB but cannot be read after deletion)")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "At least on user deleted successfully",
                content = {@Content(mediaType = "application/json",schema =@Schema(implementation = UserResponse.class))}),
        @ApiResponse(responseCode = "404", description = "No user found to be deleted",
                content = {@Content(mediaType = "application/json", schema =@Schema(implementation = ApiErrorResponse.class))})})
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface OpenApiDeleteUsers {
}