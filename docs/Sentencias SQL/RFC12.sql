SELECT to_char(FECHARESERVA - 7/24,'IYYY'), to_char(FECHARESERVA - 7/24,'IW'),MAX(PRECIO), COUNT(ID)
FROM RESERVAS
GROUP BY to_char(FECHARESERVA - 7/24,'IYYY'), to_char(FECHARESERVA - 7/24,'IW');

select trunc(FECHARESERVA, 'IW') week, max(precio)
from RESERVAS
group by trunc(FECHARESERVA, 'IW');

 SELECT EXTRACT (YEAR from FECHARESERVA), COUNT(ID) as Sum_Amt, 
 EXTRACT (month from FECHARESERVA) as MES
 FROM RESERVAS
 GROUP BY EXTRACT (YEAR from FECHARESERVA), EXTRACT (month from FECHARESERVA)
 ORDER BY MES;

 /* Monthly sum of values */
 SELECT SUM( Amount ) as Sum_Amt, 
 DATEPART (mm, Date) as MonNum
 FROM databse_name.table_name
 GROUP BY DATEPART (mm, Date)
 ORDER BY MonNum;