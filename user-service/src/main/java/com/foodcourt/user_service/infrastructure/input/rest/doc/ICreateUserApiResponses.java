package com.foodcourt.user_service.infrastructure.input.rest.doc;

import com.foodcourt.user_service.infrastructure.utils.InfrastructureConstants;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = InfrastructureConstants.RESPONSE_CODE_201),
        @ApiResponse(responseCode = "400", description = InfrastructureConstants.RESPONSE_CODE_400),
        @ApiResponse(responseCode = "409", description = InfrastructureConstants.RESPONSE_CODE_409)
})
public @interface ICreateUserApiResponses {
}
