package com.foodcourt.user_service.infrastructure.adapter.persistence;

import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.infrastructure.adapter.out.persistence.JpaUserRepositoryAdapter;
import com.foodcourt.user_service.infrastructure.adapter.out.persistence.entity.RoleJpaEntity;
import com.foodcourt.user_service.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.foodcourt.user_service.infrastructure.adapter.out.persistence.repository.SpringDataRoleRepository;
import com.foodcourt.user_service.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Disabled("Deshabilitado temporalmente debido a problemas de inicialización de DB en tests")
@DataJpaTest
@Import({JpaUserRepositoryAdapter.class})
@ActiveProfiles("test")
@DisplayName("JpaUserRepositoryAdapter Integration Tests")
class JpaUserRepositoryAdapterTest {

    @Autowired
    private JpaUserRepositoryAdapter jpaUserRepositoryAdapter;

    @Autowired
    private SpringDataRoleRepository springDataRoleRepository;

    @Autowired
    private SpringDataUserRepository springDataUserRepository;


    @Test
    @DisplayName("Debe guardar un nuevo usuario propietario y recuperar su ID")
    void shouldSaveNewOwnerUserAndAssignId() {
        // Given
        Optional<RoleJpaEntity> propietarioRole = springDataRoleRepository.findByName("Propietario"); // Busca por el nombre en español
        assertTrue(propietarioRole.isPresent(), "El rol 'Propietario' debe existir en la DB de prueba.");

        User newUser = User.createOwner(
                "Ana", "Lopez", "1000000002", "+573109876543",
                LocalDate.of(1995, 3, 20), "ana.lopez@example.com", "pass456"
        );

        // When
        User savedUser = jpaUserRepositoryAdapter.save(newUser);

        // Then
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertTrue(Long.parseLong(savedUser.getId()) > 0);

        Optional<UserEntity> foundEntity = springDataUserRepository.findById(Long.parseLong(savedUser.getId()));
        assertTrue(foundEntity.isPresent());
        assertEquals("Ana", foundEntity.get().getName());
        assertEquals("Lopez", foundEntity.get().getLast_name()); // Añadido
        assertEquals("1000000002", foundEntity.get().getDocument()); // Añadido
        assertEquals("+573109876543", foundEntity.get().getPhone()); // Añadido
        assertEquals(LocalDate.of(1995, 3, 20), foundEntity.get().getBirthdate()); // Añadido
        assertEquals("ana.lopez@example.com", foundEntity.get().getEmail()); // Añadido
        assertEquals("Propietario", foundEntity.get().getRole().getName()); // Verifica el nombre del rol guardado
        assertEquals(Role.PROPIETARIO, savedUser.getRole()); // Verifica el rol de dominio del savedUser
    }

    @Test
    @DisplayName("No debe guardar usuario si el email ya existe en la base de datos (validación DB)")
    void shouldNotSaveUserIfEmailAlreadyExistsInDb() {
        // Given
        RoleJpaEntity propietarioRole = springDataRoleRepository.findByName("Propietario")
                .orElseThrow(() -> new RuntimeException("El rol 'Propietario' debe existir en la DB de prueba."));

        UserEntity existingUserEntity = new UserEntity(
                null, "Existente", "Email", "1000000003", "+573201112233",
                LocalDate.of(1980, 1, 1), "existing.db@example.com", "hashedPass", propietarioRole
        );
        springDataUserRepository.saveAndFlush(existingUserEntity); // saveAndFlush para asegurar que se persista antes de la segunda operación

        // When
        // Aquí probamos el método existsByEmail del adaptador, que es una llamada directa a la DB
        boolean exists = jpaUserRepositoryAdapter.existsByEmail("existing.db@example.com");

        // Then
        assertTrue(exists);
    }

    @Test
    @DisplayName("Debe encontrar un usuario por email si existe")
    void shouldFindUserByEmailIfExists() {
        // Given
        RoleJpaEntity propietarioRole = springDataRoleRepository.findByName("Propietario")
                .orElseThrow(() -> new RuntimeException("El rol 'Propietario' debe existir en la DB de prueba."));

        UserEntity existingUserEntity = new UserEntity(
                null, "Buscar", "Email", "1000000005", "+573207778899",
                LocalDate.of(1988, 7, 7), "findme@example.com", "hashedPass", propietarioRole
        );
        springDataUserRepository.saveAndFlush(existingUserEntity);

        // When
        Optional<User> foundUser = jpaUserRepositoryAdapter.findByEmail("findme@example.com");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("Buscar", foundUser.get().getName());
        assertEquals("findme@example.com", foundUser.get().getEmail());
        assertEquals(Role.PROPIETARIO, foundUser.get().getRole()); // Compara con el enum de dominio
    }

    @Test
    @DisplayName("Debe devolver Optional.empty si el email no existe")
    void shouldReturnEmptyOptionalIfEmailDoesNotExist() {
        // When
        Optional<User> foundUser = jpaUserRepositoryAdapter.findByEmail("nonexistent@example.com");

        // Then
        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("Debe encontrar un usuario por documento de identidad si existe")
    void shouldFindUserByDocumentIdIfExists() {
        // Given
        RoleJpaEntity propietarioRole = springDataRoleRepository.findByName("Propietario")
                .orElseThrow(() -> new RuntimeException("El rol 'Propietario' debe existir en la DB de prueba."));

        UserEntity existingUserEntity = new UserEntity(
                null, "Buscar", "Documento", "1000000006", "+573201234500",
                LocalDate.of(1989, 9, 9), "finddoc@example.com", "hashedPass", propietarioRole
        );
        springDataUserRepository.saveAndFlush(existingUserEntity);

        // When
        Optional<User> foundUser = jpaUserRepositoryAdapter.findByDocument("1000000006");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("Buscar", foundUser.get().getName());
        assertEquals("1000000006", foundUser.get().getDocument());
        assertEquals(Role.PROPIETARIO, foundUser.get().getRole()); // Compara con el enum de dominio
    }

    @Test
    @DisplayName("Debe devolver false si el email no existe al verificar unicidad")
    void shouldReturnFalseIfEmailDoesNotExistForUniquenessCheck() {
        // When
        boolean exists = jpaUserRepositoryAdapter.existsByEmail("unique.new@example.com");

        // Then
        assertFalse(exists);
    }

    @Test
    @DisplayName("Debe devolver true si el email ya existe al verificar unicidad")
    void shouldReturnTrueIfEmailAlreadyExistsForUniquenessCheck() {
        // Given
        RoleJpaEntity propietarioRole = springDataRoleRepository.findByName("Propietario")
                .orElseThrow(() -> new RuntimeException("El rol 'Propietario' debe existir en la DB de prueba."));

        UserEntity existingUserEntity = new UserEntity(
                null, "Exist", "Unicidad", "1000000007", "+573201112244",
                LocalDate.of(1985, 2, 2), "check.exists@example.com", "hashedPass", propietarioRole
        );
        springDataUserRepository.saveAndFlush(existingUserEntity);

        // When
        boolean exists = jpaUserRepositoryAdapter.existsByEmail("check.exists@example.com");

        // Then
        assertTrue(exists);
    }
}