package com.foodcourt.user_service.domain.utils.constants;

import java.util.regex.Pattern;

public class DomainConstants {
    private DomainConstants() {
    }

    public static final int MAJORITY_AGE = 18;
    public static final String USER_IS_NOT_OF_LEGAL_AGE_MESSAGE = "El usuario no es mayor de edad";

    public static final String ROLE_OWNER = "Propietario";
    public static final String ROLE_EMPLOYEE = "Empleado";
    public static final String USER_ALREADY_EXISTS_DOCUMENT_MESSAGE = "El documento ya está registrado";
    public static final String USER_ALREADY_EXISTS_EMAIL_MESSAGE = "El correo ya está registrado";
    public static final String USER_ALREADY_EXISTS_PHONE_MESSAGE = "El celular ya está registrado";

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    public static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-9]+$");
    public static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,12}$");

    public static final String FIELD_NAME_REQUIRED_MESSAGE = "El nombre es obligatorio";
    public static final String FIELD_LASTNAME_REQUIRED_MESSAGE = "El apellido es obligatorio";
    public static final String FIELD_DOCUMENT_NUMERIC_MESSAGE = "El documento de identidad debe ser numérico";
    public static final String FIELD_PHONE_FORMAT_MESSAGE = "El formato del celular es inválido";
    public static final String FIELD_BIRTHDATE_REQUIRED_MESSAGE = "La fecha de nacimiento es obligatoria";
    public static final String FIELD_EMAIL_FORMAT_MESSAGE = "El correo debe tener un formato válido";
    public static final String FIELD_PASSWORD_REQUIRED_MESSAGE = "La clave es obligatoria";
}
