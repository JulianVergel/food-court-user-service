package com.foodcourt.user_service.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles") // Mapea a la tabla 'roles' de tu DB
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Mapea a la columna 'id'
    private Long id;

    @Column(name = "name", unique = true, nullable = false) // Mapea a la columna 'name'
    private String name; // El nombre textual del rol (Propietario, Administrador, etc.)

    @Column(name = "description") // Mapea a la columna 'description'
    private String description; // Descripción del rol
}