package com.foodcourt.user_service.domain.utils.validators;

import com.foodcourt.user_service.domain.exception.InvalidDomainDataException;
import com.foodcourt.user_service.domain.exception.UserIsNotOfLegalAgeException;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.utils.constants.DomainConstants;

import java.time.LocalDate;

public class UserValidator {
    private UserValidator() {
    }

    public static void validateLegalAge(LocalDate birthdate) {
        if (LocalDate.now().minusYears(DomainConstants.MAJORITY_AGE).isBefore(birthdate)) {
            throw new UserIsNotOfLegalAgeException();
        }
    }

    public static void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new InvalidDomainDataException(DomainConstants.FIELD_NAME_REQUIRED_MESSAGE);
        }

        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new InvalidDomainDataException(DomainConstants.FIELD_LASTNAME_REQUIRED_MESSAGE);
        }

        if (user.getDocument() == null || !DomainConstants.NUMERIC_PATTERN.matcher(user.getDocument()).matches()) {
            throw new InvalidDomainDataException(DomainConstants.FIELD_DOCUMENT_NUMERIC_MESSAGE);
        }

        if (user.getPhone() == null || !DomainConstants.PHONE_PATTERN.matcher(user.getPhone()).matches()) {
            throw new InvalidDomainDataException(DomainConstants.FIELD_PHONE_FORMAT_MESSAGE);
        }

        if (user.getBirthdate() == null) {
            throw new InvalidDomainDataException(DomainConstants.FIELD_BIRTHDATE_REQUIRED_MESSAGE);
        }
        validateLegalAge(user.getBirthdate());

        if (user.getEmail() == null || !DomainConstants.EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new InvalidDomainDataException(DomainConstants.FIELD_EMAIL_FORMAT_MESSAGE);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new InvalidDomainDataException(DomainConstants.FIELD_PASSWORD_REQUIRED_MESSAGE);
        }
    }
}
