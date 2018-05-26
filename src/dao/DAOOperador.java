package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vos.CategoriaServicio;
import vos.Cliente;
import vos.Espacio;
import vos.ListaRFC12;
import vos.Operador;
import vos.CategoriaOperador;
import vos.RFC1;
import vos.RFC12;
import vos.RFC13;
import vos.RFC3;
import vos.RFC5;
import vos.RFC6;

public class DAOOperador {
	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOOperador() {
		recursos = new ArrayList<Object>();
	}

	public void cerrarRecursos() {
		for (Object ob : recursos) {
			if (ob instanceof PreparedStatement) {
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
	}

	public void setConn(Connection con) {
		this.conn = con;
	}

	public ArrayList<Operador> darOperadores() throws SQLException, Exception {
		ArrayList<Operador> operadores = new ArrayList<Operador>();

		String sql = "SELECT * FROM OPERADORES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			long id = Long.parseLong(rs.getString("ID"));
			long documento;
			if(rs.getString("DOCUMENTO") == null)
			{
				documento = 0;
			}
			else
			{
				documento = Long.parseLong(rs.getString("DOCUMENTO"));
			}
			String nombre = rs.getString("NOMBRE");
			long registro;
			if(rs.getString("REGISTRO") == null)
			{
				registro = 0;
			}
			else
			{
				registro = Long.parseLong(rs.getString("REGISTRO"));
			}
			DAOCategoriaOperador daoCategoriaOperador = new DAOCategoriaOperador();			
			daoCategoriaOperador.setConn(conn);		
			CategoriaOperador categoria = daoCategoriaOperador.buscarCategoriaOperador(Long.parseLong(rs.getString("IDCATEGORIA")));			
			DAOEspacio daoEspacio = new DAOEspacio();
			daoEspacio.setConn(conn);

			List<Long> espacios = daoEspacio.buscarEspaciosIdOperador(id);

			operadores.add(new Operador(id, registro, nombre, categoria, espacios, documento));
		}
		
		prepStmt.close();
		
		return operadores;
	}

	public void addOperador(Operador operador) throws SQLException, Exception {
		String sql = "INSERT INTO OPERADORES (id, idCategoria, nombre, registro, documento) VALUES (";
		sql += operador.getId() + ",";
		sql += operador.getCategoria().getId() + ",'";
		sql += operador.getNombre() + "',";
		if(operador.getRegistro() == 0)
		{
			sql += "null,";
		}
		else
		{
			sql += operador.getRegistro() + ",";
		}
		if(operador.getDocumento() == 0)
		{
			sql += "null)";
		}
		else
		{
			sql += operador.getDocumento() + ")";
		}


		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		prepStmt.close();
	}

	public void updateOperador(Operador operador) throws SQLException, Exception {
		String sql = "UPDATE OPERADORES SET ";		
		sql += "nombre = '" + operador.getNombre() + "',";
		if(operador.getRegistro() == 0)
		{
			sql += "registro = null,";
		}
		else
		{
			sql += "registro = " + operador.getRegistro() + ",";
		}
		if(operador.getDocumento() == 0)
		{
			sql += "documento = null)";
		}
		else
		{
			sql += "documento = " + operador.getDocumento() + ",";
		}
		sql += "idCategoria = " + operador.getCategoria().getId();
		sql += " WHERE ID = " + operador.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		prepStmt.close();
	}

	public void deleteOperador(Operador operador) throws SQLException, Exception {
		String sql = "DELETE FROM OPERADORES";
		sql += " WHERE ID = " + operador.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		prepStmt.close();
	}

	public Operador buscarOperador(long id) throws SQLException, Exception {
		String sql = "SELECT * FROM OPERADORES WHERE ID = " + id ;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(!rs.next())
		{
			throw new Exception ("No se encontró ningún operador con el id = "+id);
		}

		long documento;
		if(rs.getString("DOCUMENTO") == null)
		{
			documento = 0;
		}
		else
		{
			documento = Long.parseLong(rs.getString("DOCUMENTO"));
		}
		String nombre = rs.getString("NOMBRE");
		long registro;
		if(rs.getString("REGISTRO") == null)
		{
			registro = 0;
		}
		else
		{
			registro = Long.parseLong(rs.getString("REGISTRO"));
		}
		DAOCategoriaOperador daoCategoriaOperador = new DAOCategoriaOperador();			
		daoCategoriaOperador.setConn(conn);		
		CategoriaOperador categoria = daoCategoriaOperador.buscarCategoriaOperador(Long.parseLong(rs.getString("IDCATEGORIA")));			
		DAOEspacio daoEspacio = new DAOEspacio();
		daoEspacio.setConn(conn);

		List<Long> espacios = daoEspacio.buscarEspaciosIdOperador(id);

		prepStmt.close();
		
		return new Operador(id, registro, nombre, categoria, espacios, documento);
	}


	public long buscarOperadorIdEspacio(long id) throws SQLException, Exception {
		String sql = "SELECT * FROM ESPACIOS WHERE ID =" + id ;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(!rs.next())
		{
			throw new Exception ("No se encontró ningun operador con el espacio que tiene id = "+id);
		}

		Long idOperador = Long.parseLong(rs.getString("IDOPERADOR"));
		
		prepStmt.close();
		
		return idOperador;
	}

	public List<Operador> buscarOperadoresPorCategoria(String categoria) throws SQLException, Exception {
		DAOCategoriaOperador daoCatOperador= new DAOCategoriaOperador();
		daoCatOperador.setConn(conn);

		CategoriaOperador catOp= daoCatOperador.buscarCategoriaOperadorNombre(categoria);

		String sql = "SELECT * FROM OPERADORES WHERE IDCATEGORIA  =" + catOp.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		ArrayList<Operador> listaRet = new ArrayList<>();
		while(!rs.next()) {
			long id = Long.parseLong(rs.getString("ID"));
			long documento = Long.parseLong(rs.getString("DOCUMENTO"));
			String nombre = rs.getString("NOMBRE");
			long registro = Integer.parseInt(rs.getString("REGISTRO"));
			DAOCategoriaOperador daoCategoriaOperador = new DAOCategoriaOperador();			
			daoCategoriaOperador.setConn(conn);					
			DAOEspacio daoEspacio = new DAOEspacio();
			daoEspacio.setConn(conn);

			List<Long> espacios = daoEspacio.buscarEspaciosIdOperador(id);

			listaRet.add(new Operador(id, registro, nombre, catOp, espacios, documento));		
		}
		
		prepStmt.close();
		
		return listaRet;
	}

	public Operador buscarOperadorNombre(String nombre) throws SQLException, Exception {
		String sql = "SELECT * FROM OPERADORES WHERE ID  ='" + nombre +"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
		{
			throw new Exception ("No se encontró ningun operador con el nombre = "+ nombre);
		}
		
		Long id = Long.parseLong(rs.getString("ID"));
		
		prepStmt.close();
		
		return buscarOperador(id);
	}

	// RFC1

	public List<RFC1> obtenerIngresosOperadores() throws SQLException, Exception {

		String sql = "SELECT ID, CASE WHEN INGRESOS IS NULL THEN 0 ELSE INGRESOS END AS INGRESOS "+
				"FROM OPERADORES LEFT OUTER JOIN (SELECT ESPACIOS.IDOPERADOR AS IDOP, SUM(RESERVAS.PRECIO) AS INGRESOS "+
				"FROM ESPACIOS INNER JOIN RESERVAS ON RESERVAS.IDESPACIO = ESPACIOS.ID "+
				"WHERE RESERVAS.FECHAINICIO > TO_DATE('2017-01-01','YYYY-MM-DD') "+
				"GROUP BY ESPACIOS.IDOPERADOR)TABLAINGRESOS ON OPERADORES.ID = TABLAINGRESOS.IDOP "+
				"ORDER BY OPERADORES.ID";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		List<RFC1> ingresos = new ArrayList<RFC1>();

		while (rs.next()) {
			RFC1 nuevo = new RFC1(Long.parseLong(rs.getString("ID")), Double.parseDouble(rs.getString("INGRESOS")));
			ingresos.add(nuevo);
		}

		prepStmt.close();
		
		return ingresos;
	}

	//RFC3

	public List<RFC3> obtenerOcupacionOperadores() throws SQLException, Exception
	{

		String sql = "SELECT OPERADORES.ID AS IDOPERADOR , CASE WHEN TABLACOMPARATIVA.SUMCONT/TABLACOMPARATIVA.SUMTOT IS NULL THEN 0 ELSE TABLACOMPARATIVA.SUMCONT/TABLACOMPARATIVA.SUMTOT END AS INDOCUPACION " +
				"FROM OPERADORES LEFT OUTER JOIN "+
				"(SELECT ESPACIOS.IDOPERADOR AS ID, SUM(TABLACONTEOCEROS.CONTEO) AS SUMCONT, SUM(TABLATOTAL.TOTAL) AS SUMTOT "+
				"FROM ESPACIOS,(SELECT ESPACIOS.ID, TABLACONTEO.CONTEO "+
				"FROM ESPACIOS LEFT OUTER JOIN (SELECT idEspacio, COUNT(id) AS CONTEO "+
				"FROM RESERVAS "+
				"WHERE fechaInicio < sysdate AND (fechaInicio + duracion) > sysdate "+
				"GROUP BY idEspacio) TABLACONTEO ON ESPACIOS.ID = TABLACONTEO.IDESPACIO) TABLACONTEOCEROS, ("+
				"SELECT idEspacio, COUNT(id) AS TOTAL "+
				"FROM RESERVAS "+
				"GROUP BY idEspacio) TABLATOTAL WHERE ESPACIOS.ID = TABLACONTEOCEROS.id AND TABLACONTEOCEROS.id = TABLATOTAL.idEspacio "+
				"GROUP BY idOperador) TABLACOMPARATIVA ON OPERADORES.ID = TABLACOMPARATIVA.ID "+
				"ORDER BY OPERADORES.ID ASC";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		List<RFC3> ocupaciones = new ArrayList<RFC3>();

		while (rs.next()) {
			RFC3 nuevo = new RFC3(Long.parseLong(rs.getString("IDOPERADOR")), Double.parseDouble(rs.getString("INDOCUPACION")));
			ocupaciones.add(nuevo);
		}

		prepStmt.close();
		
		return ocupaciones;
	}

	//RFC5

	public void obtenerUsosPorCategoria(List<RFC5> lista) throws SQLException, Exception
	{
		DAOCategoriaOperador daoCatOperador = new DAOCategoriaOperador();
		daoCatOperador.setConn(conn);

		DAOCategoriaServicio daoCatServicio = new DAOCategoriaServicio();
		daoCatServicio.setConn(conn);

		String sql = "SELECT CATEGORIASOPERADOR.ID, SUM(RESERVAS.DURACION) AS DIASTOTAL, SUM(RESERVAS.PRECIO) AS DINEROTOTAL "+
				"FROM OPERADORES, ESPACIOS, RESERVAS, CATEGORIASOPERADOR " +
				"WHERE OPERADORES.ID = ESPACIOS.IDOPERADOR AND ESPACIOS.ID = RESERVAS.IDESPACIO AND CATEGORIASOPERADOR.ID = OPERADORES.IDCATEGORIA "+
				"GROUP BY CATEGORIASOPERADOR.ID "+
				"ORDER BY CATEGORIASOPERADOR.ID ASC";		

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();	

		while (rs.next()) 
		{
			long id = Long.parseLong(rs.getString("ID"));
			String categoria = daoCatOperador.buscarCategoriaOperador(id).getNombre();
			int diasTotal = Integer.parseInt(rs.getString("DIASTOTAL"));
			double dineroTotal = Double.parseDouble(rs.getString("DINEROTOTAL"));

			String sqlC = "SELECT ID, IDCATEGORIA "+
					"FROM (SELECT DISTINCT CATEGORIASOPERADOR.ID, SERVICIOS.IDCATEGORIA "+
					"FROM OPERADORES, ESPACIOS, SERVICIOS, CATEGORIASOPERADOR "+
					"WHERE OPERADORES.ID = ESPACIOS.IDOPERADOR AND ESPACIOS.ID = SERVICIOS.IDESPACIO AND CATEGORIASOPERADOR.ID = OPERADORES.IDCATEGORIA "+
					"ORDER BY CATEGORIASOPERADOR.ID ASC) WHERE ID = " + id;

			System.out.println("SQL stmt:" + sqlC);
			PreparedStatement prepStmtC = conn.prepareStatement(sqlC);
			recursos.add(prepStmtC);
			ResultSet rsC = prepStmtC.executeQuery();

			List<String> servicios = new ArrayList<String>();

			while (rsC.next()) 
			{
				long idS = Long.parseLong(rsC.getString("IDCATEGORIA"));
				CategoriaServicio catServicio = daoCatServicio.buscarCategoriaServicio(idS);
				servicios.add(catServicio.getNombre());
			}

			RFC5 resultante =  new RFC5("Operador", categoria, diasTotal, dineroTotal, servicios);
			lista.add(resultante);
		}
		
		prepStmt.close();
	}

	//RFC6

	public RFC6 obtenerUsoPorUsuario(long id) throws SQLException, Exception
	{				
		DAOCategoriaServicio daoCatServicio = new DAOCategoriaServicio();
		daoCatServicio.setConn(conn);

		String sql = "SELECT OPERADORES.ID, SUM(RESERVAS.DURACION) AS DIASTOTAL, SUM(RESERVAS.PRECIO) AS DINEROTOTAL "+
				"FROM OPERADORES, ESPACIOS, RESERVAS "+
				"WHERE OPERADORES.ID = ESPACIOS.IDOPERADOR AND ESPACIOS.ID = RESERVAS.IDESPACIO AND OPERADORES.ID = " + id +
				" GROUP BY OPERADORES.ID "+
				"ORDER BY OPERADORES.ID ASC";		

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();	

		if(!rs.next()) 
		{
			buscarOperador(id);
			RFC6 resultante =  new RFC6(id, "Operador", 0, 0, new ArrayList<String>());		
			return resultante;	
		}
		else
		{
			int diasTotal = Integer.parseInt(rs.getString("DIASTOTAL"));
			double dineroTotal = Double.parseDouble(rs.getString("DINEROTOTAL"));

			String sqlC = "SELECT DISTINCT OPERADORES.ID, SERVICIOS.IDCATEGORIA "+
					"FROM OPERADORES, ESPACIOS, SERVICIOS "+
					"WHERE OPERADORES.ID = ESPACIOS.IDOPERADOR AND ESPACIOS.ID = SERVICIOS.IDESPACIO AND OPERADORES.ID = "+ id +
					" ORDER BY OPERADORES.ID ASC";

			System.out.println("SQL stmt:" + sqlC);
			PreparedStatement prepStmtC = conn.prepareStatement(sqlC);
			recursos.add(prepStmtC);
			ResultSet rsC = prepStmtC.executeQuery();

			List<String> servicios = new ArrayList<String>();

			while (rsC.next()) 
			{
				long idS = Long.parseLong(rsC.getString("IDCATEGORIA"));
				CategoriaServicio catServicio = daoCatServicio.buscarCategoriaServicio(idS);
				servicios.add(catServicio.getNombre());
			}

			RFC6 resultante =  new RFC6(id, "Operador", diasTotal, dineroTotal, servicios);	
			
			prepStmt.close();
			
			return resultante;
		}		
	}
	
	// RFC12
	
	public List<RFC12> consultarFuncionamiento(ListaRFC12 listaRFC12) throws SQLException, Exception
	{			
		DAOOperador daoOperador = new DAOOperador();
		DAOEspacio daoEspacio = new DAOEspacio();
		
		daoOperador.setConn(conn);
		daoEspacio.setConn(conn);
		
		List<RFC12> resultado = new ArrayList<RFC12>();
		
		String año = String.valueOf(listaRFC12.getAño());
		
		String sql1 = 	"SELECT TABLACONTEO.SEMANA, TABLACONTEO.ID AS IDOPERADOR, TABLACONTEO.CONTEO "+
						"FROM    (SELECT TABLASEMANAL.SEMANA, OPERADORES.ID, COUNT(TABLASEMANAL.ID) AS CONTEO "+
						        "FROM    (SELECT to_char(FECHARESERVA - 7/24,'IYYY') as AÑO, to_char(FECHARESERVA - 7/24,'IW') as SEMANA, RESERVAS.ID, RESERVAS.IDESPACIO "+
						                "FROM RESERVAS) TABLASEMANAL, ESPACIOS, OPERADORES "+
						        "WHERE AÑO = 2011 AND ESPACIOS.ID = TABLASEMANAL.IDESPACIO AND OPERADORES.ID = ESPACIOS.IDOPERADOR "+
						        "GROUP BY TABLASEMANAL.SEMANA, OPERADORES.ID) TABLACONTEO, "+
						        "(SELECT SEMANA, MAX(CONTEO) AS MAXIMO "+
						        "FROM    (SELECT TABLASEMANAL.SEMANA, OPERADORES.ID, COUNT(TABLASEMANAL.ID) AS CONTEO "+
						                "FROM    (SELECT to_char(FECHARESERVA - 7/24,'IYYY') as AÑO, to_char(FECHARESERVA - 7/24,'IW') as SEMANA, RESERVAS.ID, RESERVAS.IDESPACIO "+
						                        "FROM RESERVAS) TABLASEMANAL, ESPACIOS, OPERADORES "+
						                "WHERE AÑO = " + año + " AND ESPACIOS.ID = TABLASEMANAL.IDESPACIO AND OPERADORES.ID = ESPACIOS.IDOPERADOR "+
						                "GROUP BY TABLASEMANAL.SEMANA, OPERADORES.ID) "+
						        "GROUP BY SEMANA) TABLAMAXIMO "+
						"WHERE TABLACONTEO.SEMANA = TABLAMAXIMO.SEMANA AND TABLACONTEO.CONTEO = TABLAMAXIMO.MAXIMO "+
						"ORDER BY TABLACONTEO.SEMANA";
		
		String sql2 = 	"SELECT TABLACONTEO.SEMANA, TABLACONTEO.ID AS IDOPERADOR, TABLACONTEO.CONTEO "+
						"FROM    (SELECT TABLASEMANAL.SEMANA, OPERADORES.ID, COUNT(TABLASEMANAL.ID) AS CONTEO "+
						        "FROM    (SELECT to_char(FECHARESERVA - 7/24,'IYYY') as AÑO, to_char(FECHARESERVA - 7/24,'IW') as SEMANA, RESERVAS.ID, RESERVAS.IDESPACIO "+
						                "FROM RESERVAS) TABLASEMANAL, ESPACIOS, OPERADORES "+
						        "WHERE AÑO = 2011 AND ESPACIOS.ID = TABLASEMANAL.IDESPACIO AND OPERADORES.ID = ESPACIOS.IDOPERADOR "+
						        "GROUP BY TABLASEMANAL.SEMANA, OPERADORES.ID) TABLACONTEO, "+
						        "(SELECT SEMANA, MIN(CONTEO) AS MINIMO "+
						        "FROM    (SELECT TABLASEMANAL.SEMANA, OPERADORES.ID, COUNT(TABLASEMANAL.ID) AS CONTEO "+
						                "FROM    (SELECT to_char(FECHARESERVA - 7/24,'IYYY') as AÑO, to_char(FECHARESERVA - 7/24,'IW') as SEMANA, RESERVAS.ID, RESERVAS.IDESPACIO "+
						                        "FROM RESERVAS) TABLASEMANAL, ESPACIOS, OPERADORES "+
						                "WHERE AÑO = " + año + " AND ESPACIOS.ID = TABLASEMANAL.IDESPACIO AND OPERADORES.ID = ESPACIOS.IDOPERADOR "+
						                "GROUP BY TABLASEMANAL.SEMANA, OPERADORES.ID) "+
						        "GROUP BY SEMANA) TABLAMINIMO "+
						"WHERE TABLACONTEO.SEMANA = TABLAMINIMO.SEMANA AND TABLACONTEO.CONTEO = TABLAMINIMO.MINIMO "+
						"ORDER BY TABLACONTEO.SEMANA";
		
		String sql3 = 	"SELECT TABLAMAXIMO.SEMANA, TABLAOCUPACION.IDESPACIO, TABLAMAXIMO.MAXIMO "+
						"FROM    (SELECT SEMANA, MAX(OCUPACIONSEMANAL) AS MAXIMO "+
						        "FROM    (SELECT to_char(FECHAINICIO - 7/24,'IYYY') as AÑO, to_char(FECHAINICIO - 7/24,'IW') as SEMANA, RESERVAS.ID, RESERVAS.IDESPACIO, CASE WHEN RESERVAS.DURACION > 8 - TO_CHAR(FECHAINICIO, 'D') THEN (8 - TO_CHAR(FECHAINICIO, 'D'))/7 ELSE RESERVAS.DURACION/7 END AS OCUPACIONSEMANAL "+
						                "FROM RESERVAS) WHERE AÑO = " + año + " "+
						        "GROUP BY SEMANA)TABLAMAXIMO, "+
						        "(SELECT SEMANA, IDESPACIO, OCUPACIONSEMANAL "+
						        "FROM(SELECT to_char(FECHAINICIO - 7/24,'IYYY') as AÑO, to_char(FECHAINICIO - 7/24,'IW') as SEMANA, RESERVAS.ID, RESERVAS.IDESPACIO, CASE WHEN RESERVAS.DURACION > 8 - TO_CHAR(FECHAINICIO, 'D') THEN (8 - TO_CHAR(FECHAINICIO, 'D'))/7 ELSE RESERVAS.DURACION/7 END AS OCUPACIONSEMANAL "+
						            "FROM RESERVAS) "+
						        "WHERE AÑO = " + año + ")TABLAOCUPACION "+
						"WHERE TABLAMAXIMO.MAXIMO = TABLAOCUPACION.OCUPACIONSEMANAL AND TABLAMAXIMO.SEMANA = TABLAOCUPACION.SEMANA "+
						"ORDER BY TABLAMAXIMO.SEMANA";
		
		String sql4 = 	"SELECT TABLAMINIMO.SEMANA, TABLAOCUPACION.IDESPACIO, TABLAMINIMO.MINIMO "+
						"FROM    (SELECT SEMANA, MIN(OCUPACIONSEMANAL) AS MINIMO "+
						       	"FROM    (SELECT to_char(FECHAINICIO - 7/24,'IYYY') as AÑO, to_char(FECHAINICIO - 7/24,'IW') as SEMANA, RESERVAS.ID, RESERVAS.IDESPACIO, CASE WHEN RESERVAS.DURACION > 8 - TO_CHAR(FECHAINICIO, 'D') THEN (8 - TO_CHAR(FECHAINICIO, 'D'))/7 ELSE RESERVAS.DURACION/7 END AS OCUPACIONSEMANAL "+
						                "FROM RESERVAS) WHERE AÑO = " + año + " "+
						        "GROUP BY SEMANA)TABLAMINIMO, "+
						        "(SELECT SEMANA, IDESPACIO, OCUPACIONSEMANAL "+
						        "FROM(SELECT to_char(FECHAINICIO - 7/24,'IYYY') as AÑO, to_char(FECHAINICIO - 7/24,'IW') as SEMANA, RESERVAS.ID, RESERVAS.IDESPACIO, CASE WHEN RESERVAS.DURACION > 8 - TO_CHAR(FECHAINICIO, 'D') THEN (8 - TO_CHAR(FECHAINICIO, 'D'))/7 ELSE RESERVAS.DURACION/7 END AS OCUPACIONSEMANAL "+
						            "FROM RESERVAS) "+
						        "WHERE AÑO = " + año + ")TABLAOCUPACION "+
						"WHERE TABLAMINIMO.MINIMO = TABLAOCUPACION.OCUPACIONSEMANAL AND TABLAMINIMO.SEMANA = TABLAOCUPACION.SEMANA "+
						"ORDER BY TABLAMINIMO.SEMANA";
		
		String sql = "SELECT SEMANA, MIN(OPERADORMAS) AS OPERADORMAS, MIN(OPERADORMENOS) AS OPERADORMENOS, MIN(ESPACIOMAS) AS ESPACIOMAS, MIN(ESPACIOMENOS) AS ESPACIOMENOS "+
			         "FROM(SELECT T1.SEMANA, T1.IDOPERADOR AS OPERADORMAS, T2.IDOPERADOR AS OPERADORMENOS, T3.IDESPACIO AS ESPACIOMAS, T4.IDESPACIO AS ESPACIOMENOS "+
			    	    "FROM ("+sql1+")T1,("+sql2+")T2,("+sql3+")T3,("+sql4+")T4 "+
			    	 "WHERE T1.SEMANA = T2.SEMANA AND T2.SEMANA = T3.SEMANA AND T3.SEMANA = T4.SEMANA) "+
			         "GROUP BY SEMANA "+
			         "ORDER BY SEMANA";
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		recursos.add(prepStmt);
		
		long tiempo = System.currentTimeMillis();
		
		ResultSet rs = prepStmt.executeQuery();	
		
		tiempo = System.currentTimeMillis() - tiempo;
		
		
		
		while(rs.next())
		{
			int semana = rs.getInt("SEMANA");
			
			Operador operadorMas = daoOperador.buscarOperador(rs.getLong("OPERADORMAS"));
			Operador operadorMenos = daoOperador.buscarOperador(rs.getLong("OPERADORMENOS"));
			Espacio espacioMas = daoEspacio.buscarEspacio(rs.getLong("ESPACIOMAS"));
			Espacio espacioMenos =daoEspacio.buscarEspacio(rs.getLong("ESPACIOMENOS"));
			
			resultado.add(new RFC12(semana, operadorMas, operadorMenos, espacioMas, espacioMenos));
		}
		
		prepStmt.close();
		
		System.out.println("Esta consulta duró " + tiempo + " milisegundos");
		
		return resultado;
	}
}
