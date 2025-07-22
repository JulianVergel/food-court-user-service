package com.foodcourt.user_service.infrastructure.configuration;

import com.foodcourt.user_service.infrastructure.output.jpa.entity.RoleEntity;
import com.foodcourt.user_service.infrastructure.output.jpa.entity.UserEntity;
import com.foodcourt.user_service.infrastructure.output.jpa.repository.IRoleRepository;
import com.foodcourt.user_service.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear rol Propietario si no existe
        RoleEntity ownerRole = roleRepository.findByName("Propietario")
                .orElseGet(() -> roleRepository.save(new RoleEntity(null, "Propietario", "Rol para propietarios")));

        // Crear rol Administrador si no existe
        RoleEntity adminRole = roleRepository.findByName("Administrador")
                .orElseGet(() -> roleRepository.save(new RoleEntity(null, "Administrador", "Rol de administrador")));

        // Crear rol Empleado si no existe
        RoleEntity employeeRole = roleRepository.findByName("Empleado")
                .orElseGet(() -> roleRepository.save(new RoleEntity(null, "Empleado", "Rol de Empleado")));

        // Crear rol Empleado si no existe
        RoleEntity customerRole = roleRepository.findByName("Cliente")
                .orElseGet(() -> roleRepository.save(new RoleEntity(null, "Cliente", "Rol de Cliente")));

        // Crear un usuario Propietario de prueba si no existe
        if (!userRepository.existsByEmail("propietario@example.com")) {
            UserEntity ownerUser = new UserEntity();
            ownerUser.setName("Test");
            ownerUser.setLastName("Owner");
            ownerUser.setDocument("1122334455");
            ownerUser.setPhone("+573001112233");
            ownerUser.setBirthdate(LocalDate.of(1990, 1, 1));
            ownerUser.setEmail("propietario@example.com");
            ownerUser.setPassword(passwordEncoder.encode("1234")); // Contraseña conocida
            ownerUser.setRole(ownerRole);
            userRepository.save(ownerUser);
        }

        // Crear un usuario Administrador de prueba si no existe
        if (!userRepository.existsByEmail("admin@example.com")) {
            UserEntity adminUser = new UserEntity();
            adminUser.setName("Admin");
            adminUser.setLastName("User");
            adminUser.setDocument("9999999999");
            adminUser.setPhone("+573009998877");
            adminUser.setBirthdate(LocalDate.of(1990, 1, 1));
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Contraseña conocida
            adminUser.setRole(adminRole); // Asigna el rol de Administrador
            userRepository.save(adminUser);
        }

        // Crear un usuario Empleado de prueba si no existe
        if (!userRepository.existsByEmail("employee@example.com")) {
            UserEntity adminUser = new UserEntity();
            adminUser.setName("Employee");
            adminUser.setLastName("User");
            adminUser.setDocument("9999999998");
            adminUser.setPhone("+573009998887");
            adminUser.setBirthdate(LocalDate.of(1990, 1, 1));
            adminUser.setEmail("employee@example.com");
            adminUser.setPassword(passwordEncoder.encode("employee123")); // Contraseña conocida
            adminUser.setRole(employeeRole);
            userRepository.save(adminUser);
        }

        // Crear un usuario Cliente de prueba si no existe
        if (!userRepository.existsByEmail("customer@example.com")) {
            UserEntity adminUser = new UserEntity();
            adminUser.setName("Customer");
            adminUser.setLastName("User");
            adminUser.setDocument("9999999988");
            adminUser.setPhone("+573009988887");
            adminUser.setBirthdate(LocalDate.of(1990, 1, 1));
            adminUser.setEmail("customer@example.com");
            adminUser.setPassword(passwordEncoder.encode("customer123")); // Contraseña conocida
            adminUser.setRole(customerRole);
            userRepository.save(adminUser);
        }

        // Crear un usuario Cliente de prueba si no existe
        if (!userRepository.existsByEmail("customer3@example.com")) {
            UserEntity adminUser = new UserEntity();
            adminUser.setName("Customer3");
            adminUser.setLastName("User3");
            adminUser.setDocument("9939999288");
            adminUser.setPhone("+573009983287");
            adminUser.setBirthdate(LocalDate.of(1990, 1, 1));
            adminUser.setEmail("customer3@example.com");
            adminUser.setPassword(passwordEncoder.encode("customer3")); // Contraseña conocida
            adminUser.setRole(customerRole);
            userRepository.save(adminUser);
        }
    }
}
