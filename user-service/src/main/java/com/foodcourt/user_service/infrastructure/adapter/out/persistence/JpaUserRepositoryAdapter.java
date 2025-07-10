package com.foodcourt.user_service.infrastructure.adapter.out.persistence;

import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.port.out.UserRepositoryPort;
import com.foodcourt.user_service.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.foodcourt.user_service.infrastructure.adapter.out.persistence.entity.RoleJpaEntity;
import com.foodcourt.user_service.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import com.foodcourt.user_service.infrastructure.adapter.out.persistence.repository.SpringDataRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository springDataUserRepository;
    private final SpringDataRoleRepository springDataRoleRepository;

    private static final Map<Role, String> DOMAIN_ROLE_TO_DB_NAME_MAP;
    private static final Map<String, Role> DB_NAME_TO_DOMAIN_ROLE_MAP;

    static {
        DOMAIN_ROLE_TO_DB_NAME_MAP = new HashMap<>();
        DOMAIN_ROLE_TO_DB_NAME_MAP.put(Role.PROPIETARIO, "Propietario");
        DOMAIN_ROLE_TO_DB_NAME_MAP.put(Role.ADMINISTRADOR, "Administrador");
        DOMAIN_ROLE_TO_DB_NAME_MAP.put(Role.EMPLEADO, "Empleado");
        DOMAIN_ROLE_TO_DB_NAME_MAP.put(Role.CLIENTE, "Cliente");

        DB_NAME_TO_DOMAIN_ROLE_MAP = new HashMap<>();
        DB_NAME_TO_DOMAIN_ROLE_MAP.put("Propietario", Role.PROPIETARIO);
        DB_NAME_TO_DOMAIN_ROLE_MAP.put("Administrador", Role.ADMINISTRADOR);
        DB_NAME_TO_DOMAIN_ROLE_MAP.put("Empleado", Role.EMPLEADO);
        DB_NAME_TO_DOMAIN_ROLE_MAP.put("Cliente", Role.CLIENTE);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = toEntity(user);
        UserEntity savedEntity = springDataUserRepository.save(userEntity);
        user.assignId(String.valueOf(savedEntity.getId()));
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email)
                .map(this::toDomain);
    }

    // AQUI EL CAMBIO PARA EL ERROR findByDocument
    @Override // <--- Asegúrate que esta anotación esté presente
    public Optional<User> findByDocument(String document) { // <--- CAMBIO DE documentId a document
        return springDataUserRepository.findByDocument(document) // <--- CAMBIO DE findByDocumentId a findByDocument
                .map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataUserRepository.existsByEmail(email);
    }

    // AQUI EL CAMBIO PARA EL ERROR existsByDocumentId
    @Override // <--- Asegúrate que esta anotación esté presente
    public boolean existsByDocument(String document) { // <--- CAMBIO DE documentId a document
        return springDataUserRepository.existsByDocument(document); // <--- CAMBIO DE existsByDocumentId a existsByDocument
    }


    private UserEntity toEntity(User domainUser) {
        UserEntity entity = new UserEntity();
        if (domainUser.getId() != null) {
            entity.setId(Long.valueOf(domainUser.getId()));
        }
        entity.setName(domainUser.getName());
        entity.setLast_name(domainUser.getLast_name());
        entity.setDocument(domainUser.getDocument());
        entity.setPhone(domainUser.getPhone());
        entity.setBirthdate(domainUser.getBirthdate());
        entity.setEmail(domainUser.getEmail());
        entity.setPassword(domainUser.getPassword());

        final String roleNameInDb = getDbRoleName(domainUser.getRole());

        RoleJpaEntity roleJpaEntity = springDataRoleRepository.findByName(roleNameInDb)
                .orElseThrow(() -> new RuntimeException("Role not found in DB: " + roleNameInDb + " (Check data.sql and DB population)"));

        entity.setRole(roleJpaEntity);
        return entity;
    }

    private User toDomain(UserEntity entity) {
        final Role domainRole = getDomainRoleFromDbName(entity.getRole().getName());

        User userFromDb = new User(
                entity.getName(),
                entity.getLast_name(),
                entity.getDocument(),
                entity.getPhone(),
                entity.getBirthdate(),
                entity.getEmail(),
                entity.getPassword(),
                domainRole
        );
        userFromDb.assignId(String.valueOf(entity.getId()));
        return userFromDb;
    }

    private String getDbRoleName(Role domainRole) {
        String dbName = DOMAIN_ROLE_TO_DB_NAME_MAP.get(domainRole);
        if (dbName == null) {
            throw new IllegalArgumentException("Domain role not mapped to DB role name: " + domainRole.name());
        }
        return dbName;
    }

    private Role getDomainRoleFromDbName(String dbRoleName) {
        Role domainRole = DB_NAME_TO_DOMAIN_ROLE_MAP.get(dbRoleName);
        if (domainRole == null) {
            throw new IllegalArgumentException("DB role name not mapped to domain role: " + dbRoleName);
        }
        return domainRole;
    }
}