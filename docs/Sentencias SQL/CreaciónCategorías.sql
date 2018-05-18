CREATE TABLE VINCULOS(
id NUMBER(10,0),
nombre VARCHAR2(30),
descripcion VARCHAR2(100)
);

CREATE TABLE CATEGORIASOPERADOR(
id NUMBER(10,0),
nombre VARCHAR2(30),
descripcion VARCHAR2(100)
);

CREATE TABLE CATEGORIASHABITACION(
id NUMBER(10,0),
nombre VARCHAR2(30),
descripcion VARCHAR2(100)
);

CREATE TABLE CATEGORIASSERVICIO(
id NUMBER(10,0),
nombre VARCHAR2(30),
descripcion VARCHAR2(100)
);

--Agregación de vínculos con la universidad

ALTER TABLE VINCULOS
ADD CONSTRAINT PK_VINCULOS
PRIMARY KEY (id);

ALTER TABLE VINCULOS
MODIFY nombre NOT NULL;

INSERT INTO VINCULOS (id, nombre, descripcion)
VALUES (1, 'ESTUDIANTE', 'Estudiante vigente de la Universidad de los Andes');

INSERT INTO VINCULOS (id, nombre, descripcion)
VALUES (2, 'PROFESOR', 'Profesor vigente en contrato de la Universidad de los Andes');

INSERT INTO VINCULOS (id, nombre, descripcion)
VALUES (3, 'PROFESOR_INVITADO', 'Profesor invitado vigente de la Universidad de los Andes');

INSERT INTO VINCULOS (id, nombre, descripcion)
VALUES (4, 'PADRE_DE_ESTUDIANTE', 'Padre de estudiante vigente de la Universidad de los Andes');

INSERT INTO VINCULOS (id, nombre, descripcion)
VALUES (5, 'EMPLEADO', 'Empleado con contrato vigente en la Universidad de los Andes');

INSERT INTO VINCULOS (id, nombre, descripcion)
VALUES (6, 'EGRESADO', 'Egresado de la Universidad de los Andes');

INSERT INTO VINCULOS (id, nombre, descripcion)
VALUES (7, 'INVITADO_A_EVENTO', 'Invitado a evento temporal en la Universidad de los Andes');

--Agregación de categorías operador

ALTER TABLE CATEGORIASOPERADOR
ADD CONSTRAINT PK_CATEGORIASOPERADOR
PRIMARY KEY (id);

ALTER TABLE CATEGORIASOPERADOR
MODIFY nombre NOT NULL;

INSERT INTO CATEGORIASOPERADOR (id, nombre, descripcion)
VALUES (1, 'HOTEL', 'Empresa de hotelería registrada como hotel');

INSERT INTO CATEGORIASOPERADOR (id, nombre, descripcion)
VALUES (2, 'HOSTAL', 'Empresa de hotelería registrada como hostal');

INSERT INTO CATEGORIASOPERADOR (id, nombre, descripcion)
VALUES (3, 'PERSONA_NATURAL', 'Persona natural que pueda proveer espacios de alojamiento');

INSERT INTO CATEGORIASOPERADOR (id, nombre, descripcion)
VALUES (4, 'MIEMBRO_DE_LA_COMUNIDAD', 'Miembro de la comunidad universitaria que puede proveer espacios de alojamiento');

INSERT INTO CATEGORIASOPERADOR (id, nombre, descripcion)
VALUES (5, 'VECINO', 'Vecino de la universidad que pueda proveer espacios de alojamiento');

INSERT INTO CATEGORIASOPERADOR (id, nombre, descripcion)
VALUES (6, 'VIVIENDA_UNIVERSITARIA', 'Empresa de alojamiento registrada como vivienda universitaria');

--Agregación de categorías habitación

ALTER TABLE CATEGORIASHABITACION
ADD CONSTRAINT PK_CATEGORIASHABITACION
PRIMARY KEY (id);

ALTER TABLE CATEGORIASHABITACION
MODIFY nombre NOT NULL;

INSERT INTO CATEGORIASHABITACION (id, nombre, descripcion)
VALUES (1, 'ESTÁNDAR', 'Habitación estándar en cualquier espacio de alojamiento');

INSERT INTO CATEGORIASHABITACION (id, nombre, descripcion)
VALUES (2, 'SEMISUITE', 'Habitación con nivel de calidad alto en hoteles');

INSERT INTO CATEGORIASHABITACION (id, nombre, descripcion)
VALUES (3, 'SUITE', 'Habitación con nivel de calidad superior en hoteles');

--Agregación de categorías servicio

ALTER TABLE CATEGORIASSERVICIO
ADD CONSTRAINT PK_CATEGORIASSERVICIO
PRIMARY KEY (id);

ALTER TABLE CATEGORIASSERVICIO
MODIFY nombre NOT NULL;

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (1, 'SEGURO', 'Servicio de seguro para espacios que necesiten asegurar su arrendamiento según las reglas de negocio');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (2, 'RECEPCIÓN', 'Servicio de recepción para un espacio dado');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (3, 'COCINA', 'Servicio de cocina integrado en un espacio arrendado');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (4, 'BAÑO_COMPARTIDO', 'Servicio de baño compartido en el espacio con acceso común');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (5, 'BAÑO_PRIVADO', 'Servicio de baño privado en habitaciones con acceso unitario');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (6, 'GIMNASIO', 'Servicio de gimnasio público/privado en el espacio de arrendamiento o las zonas comunes');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (7, 'ASEO', 'Servicio de aseo periódico para el espacio arrendado');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (8, 'TV', 'Servicio de televisión por cable o cualquier medio en el espacio de arrendamiento');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (9, 'INTERNET', 'Servicio de conexión a la red en el espacio arrendado');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (10, 'SERVICIOS_PÚBLICOS', 'Servicios públicos (luz, agua, gas, entre otros) en el espacio de arrendamiento');

INSERT INTO CATEGORIASSERVICIO (id, nombre, descripcion)
VALUES (11, 'OTRO', 'Otros servicios que no entren en las categorías anteriores');