SELECT ID
FROM(SELECT ID, COUNT(ID) AS CONTEO
FROM (SELECT ESPACIOS.ID
FROM ESPACIOS INNER JOIN SERVICIOS ON ESPACIOS.ID = SERVICIOS.IDESPACIO
WHERE ESPACIOS.ID NOT IN(SELECT ID
FROM ESPACIOS
WHERE FECHARETIRO < TO_DATE('02-12-2020','DD-MM-YYYY')) AND ESPACIOS.ID NOT IN (SELECT idEspacio
FROM(SELECT idEspacio, COUNT(ID) as CONTEO
FROM RESERVAS
WHERE FECHAINICIO > TO_DATE('02-12-2016','DD-MM-YYYY') AND FECHAINICIO + DURACION < TO_DATE('02-12-2020','DD-MM-YYYY')
GROUP BY idEspacio)) AND (SERVICIOS.IDCATEGORIA = 11 OR SERVICIOS.IDCATEGORIA=7))
GROUP BY ID) TABLACATEGORIAS
WHERE TABLACATEGORIAS.CONTEO = 2;

SELECT ESPACIOS.ID
FROM ESPACIOS INNER JOIN SERVICIOS ON ESPACIOS.ID = SERVICIOS.IDESPACIO
WHERE ESPACIOS.ID NOT IN(SELECT ID
FROM ESPACIOS
WHERE FECHARETIRO < TO_DATE('02-12-2020','DD-MM-YYYY')) AND ESPACIOS.ID NOT IN (SELECT idEspacio
FROM(SELECT idEspacio, COUNT(ID) as CONTEO
FROM RESERVAS
WHERE FECHAINICIO > TO_DATE('02-12-2016','DD-MM-YYYY') AND FECHAINICIO + DURACION < TO_DATE('02-12-2020','DD-MM-YYYY')
GROUP BY idEspacio));