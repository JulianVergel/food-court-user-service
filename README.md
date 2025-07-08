# Microservicio de Usuarios (food-court-user-service)

## Descripción General

Este repositorio contiene el código fuente del Microservicio de Usuarios, uno de los componentes clave de la arquitectura de la Plazoleta de Comidas. Su principal responsabilidad es la gestión centralizada de todos los usuarios del sistema y sus roles asociados, incluyendo Clientes, Administradores, Propietarios de Restaurantes y Empleados. Provee funcionalidades esenciales para el registro, la autenticación y la gestión básica de perfiles.

## Funcionalidades Clave

* **Gestión de Usuarios:**
    * Registro de nuevos Clientes.
    * Creación de cuentas para Propietarios de Restaurantes y Empleados por parte de un Administrador.
    * Consulta, edición y eliminación de perfiles de usuario.
* **Autenticación y Autorización:**
    * Inicio de sesión para todos los tipos de usuarios.
    * Generación y validación de tokens de seguridad (JWT u otro mecanismo) para el acceso a otros servicios.
    * Gestión de roles y permisos.

## Arquitectura

Este microservicio está diseñado siguiendo los principios de la Arquitectura Hexagonal (Puertos y Adaptadores). Esto asegura una clara separación de la lógica de negocio del dominio de los detalles de infraestructura (bases de datos, frameworks web, etc.), haciendo el código más modular, testable y adaptable a cambios futuros.

### Componentes Principales

* **Núcleo (Dominio y Servicios de Aplicación):** Contiene la lógica de negocio fundamental del servicio de usuarios, como la validación de usuarios, reglas de registro y autenticación.
* **Puertos:** Interfaces que definen cómo el núcleo interactúa con el mundo exterior (APIs de entrada y repositorios de salida).
* **Adaptadores de Entrada (Driving Adapters):** Implementaciones de las APIs REST que exponen las funcionalidades del servicio.
* **Adaptadores de Salida (Driven Adapters):** Implementaciones que conectan el núcleo con la base de datos y otras infraestructuras (ej., hashing de contraseñas).

## Tecnologías Utilizadas

* **Lenguaje de Programación:** Java
* **Framework:** Spring Boot
* **Base de Datos:** PostgreSQL
* **Gestión de Dependencias:** Gradle
* **Seguridad:** Spring Security (para autenticación/autorización), JWT (para tokens).

## Cómo Empezar

### Prerequisitos

* Java Amazon Corretto 17
* Gradle
* Una instancia de base de datos PostgreSQL en ejecución.
