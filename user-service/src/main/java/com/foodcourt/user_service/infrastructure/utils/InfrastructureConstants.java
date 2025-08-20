package com.foodcourt.user_service.infrastructure.utils;

public class InfrastructureConstants {
    private InfrastructureConstants() {
    }

    public static final String OWNER_CREATED_SUCCESSFULLY_MESSAGE = "Propietario creado exitosamente";
    public static final String EMPLOYEE_CREATED_SUCCESSFULLY_MESSAGE = "Empleado creado exitosamente";
    public static final String CUSTOMER_CREATED_SUCCESSFULLY_MESSAGE = "Cliente creado exitosamente";

    public static final String USERNAME_NOT_FOUND_MESSAGE = "Usuario no encontrado con el email ";
    public static final String NOT_DATA_FOUND_MESSAGE = "No se encontraron datos en la base de datos";

    public static final String ROLE_OWNER = "ROLE_Propietario";

    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_ID = "id";
    public static final String CLAIM_RESTAURANT_ID = "restaurantId";

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final int BEARER_PREFIX_SIZE = 7;
    public static final String USER_NOT_FOUND_MESSAGE = "Usuario no encontrado ";

    public static final String SWAGGER_TAG_USER_API_NAME = "User API";
    public static final String SWAGGER_TAG_USER_API_DESCRIPTION = "Endpoints para la gestión de usuarios";

    public static final String SUMMARY_CREATE_OWNER = "Crear un nuevo Propietario";
    public static final String SUMMARY_GET_USER_BY_ID = "Obtener un usuario por ID";
    public static final String SUMMARY_CREATE_EMPLOYEE = "Crear un nuevo Empleado";
    public static final String SUMMARY_CREATE_CUSTOMER = "Crear un nuevo Cliente";
    public static final String SUMMARY_LOGIN = "Inicio de sesion";

    public static final String RESPONSE_CODE_200 = "Recurso encontrado exitosamente";
    public static final String RESPONSE_CODE_201 = "Recurso creado exitosamente";
    public static final String RESPONSE_CODE_400 = "Petición inválida. Por favor, verifique los datos de entrada";
    public static final String RESPONSE_CODE_409 = "El recurso ya existe.";
}
