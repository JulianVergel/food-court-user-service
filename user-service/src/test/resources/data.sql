-- src/test/resources/data.sql
-- Este script inserta datos en las tablas de la base de datos H2 de prueba.
-- Se ejecuta DESPUES de schema.sql

INSERT INTO roles (id, name, description) VALUES (1, 'Propietario', 'Administra su restaurante, menú y empleados.');
INSERT INTO roles (id, name, description) VALUES (2, 'Administrador', 'Gestiona el sistema, crea restaurantes y cuentas de propietarios.');
INSERT INTO roles (id, name, description) VALUES (3, 'Empleado', 'Atiende pedidos, los prepara y entrega.');
INSERT INTO roles (id, name, description) VALUES (4, 'Cliente', 'Realiza pedidos y sigue su trazabilidad.');