package com.foodcourt.user_service.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK para UserEntity

    private String name;
    private String last_name;

    @Column(name = "document", unique = true, nullable = false)
    private String document;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @ManyToOne // Un usuario tiene un rol, un rol puede tener muchos usuarios
    @JoinColumn(name = "id_rol", nullable = false)
    private RoleJpaEntity role; // Ahora es un objeto de la entidad RoleJpaEntity
}