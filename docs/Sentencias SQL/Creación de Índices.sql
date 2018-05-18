CREATE INDEX FECHAINICIOINDEX
ON RESERVAS(FECHAINICIO);

CREATE INDEX IDESPACIOINDEX
ON RESERVAS(IDESPACIO);

CREATE INDEX IDCLIENTEINDEX
ON RESERVAS(IDCLIENTE);

CREATE BITMAP INDEX IDCATEGORIASERVICIOINDEX
ON SERVICIOS(IDCATEGORIA);

CREATE INDEX FECHARETIROINDEX
ON ESPACIOS(FECHARETIRO);

CREATE BITMAP INDEX IDVINCULOINDEX
ON CLIENTES(IDVINCULO);

CREATE BITMAP INDEX IDCATEGORIAOPERADORINDEX
ON OPERADORES(IDCATEGORIA);

CREATE INDEX IDOPERADORINDEX
ON ESPACIOS(IDOPERADOR);

CREATE BITMAP INDEX IDCATEGORIAHABITACIONINDEX
ON HABITACIONES(IDCATEGORIA);

CREATE INDEX FECHARESERVAINDEX
ON RESERVAS(FECHARESERVA);

CREATE INDEX FECHAFININDEX
ON RESERVAS(FECHARESERVA+DURACION);