package com.foodcourt.user_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String lastName;
    private String document;
    private String phone;
    private LocalDate birthdate;
    private String email;
    private String password;
    private Role role;

    public boolean isOfLegalAge() {
        return LocalDate.now().minusYears(18).isAfter(birthdate);
    }
}
