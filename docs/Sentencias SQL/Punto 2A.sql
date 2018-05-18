SELECT TabC.NomCons AS NombreTabla, NumColumnas, NumColumnasPK, NumColsNotNull, NumColumnasFKs
FROM   (SELECT T.TABLE_NAME as NomCols, COUNT(columns.COLUMN_NAME) AS NumColumnas
        FROM ALL_TABLES T JOIN ALL_TAB_COLUMNS columns
            ON T.TABLE_NAME = columns.TABLE_NAME
        WHERE T.owner = 'ISIS2304A121810'
        GROUP BY T.TABLE_NAME) JOIN 
            (SELECT cons.TABLE_NAME as NomCons, COUNT(CASE WHEN CONSTRAINT_NAME LIKE 'PK_%'
            THEN 1 END) AS NumColumnasPK, COUNT(CASE WHEN cons.CONSTRAINT_NAME LIKE 'FK_%' THEN 1 END) AS NumColumnasFKS
            FROM ALL_CONS_COLUMNS cons
            WHERE cons.owner = 'ISIS2304A121810'
            GROUP BY cons.TABLE_NAME) TabC
        ON NomCons = NomCols JOIN
            (SELECT TC.TABLE_NAME AS NomColsN, COUNT(CASE WHEN NULLABLE = 'N' THEN 1 END)
            AS NumColsNotNull
            FROM ALL_TAB_COLUMNS TC
            WHERE TC.owner = 'ISIS2304A121810'
            GROUP BY TC.TABLE_NAME) TabColsN
        ON NomCons= NomColsN
ORDER BY NombreTabla;

SELECT tabcols.TABLE_NAME AS NombreTabla,tabcols.DATA_TYPE AS TipoDatoCol,
    NVL(NumCons, 0) AS NumRestColsEseTipoDeDato,NumCols NumColsConEseTipoDato
FROM   (SELECT TABLE_NAME,DATA_TYPE, COUNT(*) AS NumCons
        FROM   (SELECT TABLE_NAME, COLUMN_NAME
                FROM ALL_CONS_COLUMNS)
                NATURAL JOIN
               (SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE 
                FROM ALL_TAB_COLUMNS)
        GROUP BY TABLE_NAME,DATA_TYPE) TabCons
    RIGHT OUTER JOIN
       (SELECT cols.TABLE_NAME, cols.DATA_TYPE,COUNT(*) AS NumCols
        FROM ALL_TAB_COLUMNS cols
            RIGHT OUTER JOIN ALL_TABLES tabs
                ON cols.TABLE_NAME = tabs.TABLE_NAME
        WHERE tabs.OWNER = 'ISIS2304A121810'
        GROUP BY cols.TABLE_NAME, cols.DATA_TYPE) TabCols
    ON tabcons.TABLE_NAME = tabcols.TABLE_NAME AND tabcons.DATA_TYPE = tabcols.DATA_TYPE
ORDER BY tabcols.TABLE_NAME,tabcols.DATA_TYPE;

SELECT TABLE_NAME AS NombreTabla, CONSTRAINT_NAME AS NombrePK, COLUMN_NAME AS NombreColPK,
        DATA_TYPE AS TipoDatoColPK, POSITION AS OrdenColPK, NumIndicesColPK
FROM ALL_TAB_COLUMNS NATURAL
    JOIN   (SELECT COLUMN_NAME, COUNT(*) AS NumIndicesColPK
            FROM ALL_IND_COLUMNS
            WHERE INDEX_OWNER='ISIS2304A121810S'
            GROUP BY COLUMN_NAME)
    NATURAL JOIN
           (SELECT TABLE_NAME,CONSTRAINT_NAME, POSITION, COLUMN_NAME
            FROM ALL_CONS_COLUMNS NATURAL
            JOIN ALL_CONSTRAINTS
            WHERE CONSTRAINT_TYPE = 'P'
                AND OWNER='ISIS2304A121810')
ORDER BY TABLE_NAME, CONSTRAINT_NAME, DATA_TYPE, POSITION;

SELECT TABLE_NAME AS NombreTabla, COLUMN_NAME AS NombreCol,CONSTRAINT_NAME AS NombreConstraintColumna, 
    (SELECT TABLE_NAME FROM ALL_CONS_COLUMNS CONS 
        WHERE OWNER = 'ISIS2304A121810'
        AND CONS.CONSTRAINT_NAME = R_CONSTRAINT_NAME ) AS NombreTablaRefFK  
FROM ALL_CONS_COLUMNS NATURAL JOIN ALL_CONSTRAINTS NATURAL JOIN ALL_TAB_COLUMNS  
WHERE OWNER = 'ISIS2304A121810' AND NOT CONSTRAINT_TYPE IS NULL 
ORDER BY TABLE_NAME, COLUMN_NAME, CONSTRAINT_NAME;