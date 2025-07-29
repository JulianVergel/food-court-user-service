package com.foodcourt.user_service.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastName;
    @Column(unique = true, nullable = false, length = 10)
    private String document;
    @Column(unique = true, nullable = false, length = 13)
    private String phone;
    private LocalDate birthdate;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private RoleEntity role;

    @Column(name = "id_restaurant")
    private Long restaurantId;
}
