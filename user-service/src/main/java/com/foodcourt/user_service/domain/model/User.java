package com.foodcourt.user_service.domain.model;

import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String last_name;
    private String document;
    private String phone;
    private LocalDate birthdate;
    private String email;
    private String password;
    private Role role;

    public User(String name, String last_name, String document, String phone,
                LocalDate birthdate, String email, String password, Role role) {
        this.name = name;
        this.last_name = last_name;
        this.document = document;
        this.phone = phone;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static User createOwner(String name, String last_name, String document, String phone,
                                   LocalDate birthdate, String email, String password) {
        // Validaciones de campos comunes y obligatorios
        validateCommonFields(name, last_name, document, phone, birthdate, email, password);

        // Validación específica para propietarios (mayoría de edad)
        if (!isAdult(birthdate)) {
            throw new ValidationException("User must be of legal age (18+).");
        }

        // Retorna una nueva instancia de User con el rol de propietario
        return new User(name, last_name, document, phone, birthdate, email, password, Role.PROPIETARIO);
    }

    public static User createAdmin(String name, String last_name, String document, String phone,
                                   LocalDate birthdate, String email, String password) {
        // Validaciones de campos comunes
        validateCommonFields(name, last_name, document, phone, birthdate, email, password);

        // Validaciones específicas para ADMIN si las hubiera
        return new User(name, last_name, document, phone, birthdate, email, password, Role.ADMINISTRADOR);
    }

    // Metodo para establecer el ID después de la persistencia
    public void assignId(String id) {
        this.id = id;
    }

    // Metodo para actualizar la contraseña (que ya vendra hasheada del adaptador)
    public void updatePassword(String hashedPassword) {
        this.password = hashedPassword;
    }

    // Metodos de Validación (privados, ya que son reglas internas del dominio)
    private static void validateCommonFields(String name, String lastName, String documentId, String phoneNumber,
                                             LocalDate dateOfBirth, String email, String password) {
        if (name == null || name.trim().isEmpty()) throw new ValidationException("Name cannot be empty");
        if (lastName == null || lastName.trim().isEmpty()) throw new ValidationException("Last name cannot be empty");
        if (documentId == null || documentId.trim().isEmpty()) throw new ValidationException("Document ID cannot be empty");
        if (!documentId.matches("\\d+")) throw new ValidationException("Document ID must be numeric");
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) throw new ValidationException("Phone number cannot be empty");
        if (!isValidPhoneNumber(phoneNumber)) throw new ValidationException("Invalid phone number format. Max 13 chars, starts with +, numeric.");
        if (dateOfBirth == null) throw new ValidationException("Date of birth cannot be empty");
        if (email == null || email.trim().isEmpty()) throw new ValidationException("Email cannot be empty");
        if (!isValidEmail(email)) throw new ValidationException("Invalid email format.");
        if (password == null || password.trim().isEmpty()) throw new ValidationException("Password cannot be empty");
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() <= 13 && phoneNumber.matches("^\\+\\d+$");
    }

    private static boolean isAdult(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears() >= 18;
    }
}
