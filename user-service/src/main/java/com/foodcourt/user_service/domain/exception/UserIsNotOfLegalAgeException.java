package com.foodcourt.user_service.domain.exception;

import com.foodcourt.user_service.domain.utils.DomainConstants;

public class UserIsNotOfLegalAgeException extends RuntimeException{
    public UserIsNotOfLegalAgeException() {
        super(DomainConstants.USER_IS_NOT_OF_LEGAL_AGE_MESSAGE);
    }
}
