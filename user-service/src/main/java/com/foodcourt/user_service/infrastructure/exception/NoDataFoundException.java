package com.foodcourt.user_service.infrastructure.exception;

import com.foodcourt.user_service.infrastructure.utils.InfrastructureConstants;

public class NoDataFoundException extends RuntimeException{
    public NoDataFoundException() {
        super(
                InfrastructureConstants.NOT_DATA_FOUND_MESSAGE
        );
    }
}
