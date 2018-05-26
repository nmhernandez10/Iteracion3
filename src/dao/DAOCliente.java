package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.CategoriaServicio;
import vos.Cliente;
import vos.Espacio;
import vos.ListaRFC10;
import vos.RFC10;
import vos.RFC13;
import vos.RFC5;
import vos.RFC6;
import vos.RFC8;
import vos.Reserva;
import vos.Vinculo;

public class DAOCliente {
	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOCliente() {
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

	public ArrayList<Cliente> darClientes() throws SQLException, Exception {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		String sql = "SELECT * FROM CLIENTES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			long id = Long.parseLong(rs.getString("ID"));
			long identificacion = Long.parseLong(rs.getString("DOCUMENTO"));
			String nombre = rs.getString("NOMBRE");
			int edad = Integer.parseInt(rs.getString("EDAD"));
			String direccion = rs.getString("DIRECCION");
			DAOVinculo daoVinculo = new DAOVinculo();			
			daoVinculo.setConn(conn);		
			Vinculo vinculo= daoVinculo.buscarVinculo(Long.parseLong(rs.getString("IDVINCULO")));	
			DAOReserva daoReserva = new DAOReserva();
			daoReserva.setConn(conn);

			List<Long> reservas = daoReserva.buscarReservasIdCliente(id);

			clientes.add(new Cliente(id, identificacion, nombre, edad, direccion, vinculo, reservas));
		}
		
		prepStmt.close();
		
		return clientes;
	}

	public void addCliente(Cliente cliente) throws SQLException, Exception {
		String sql = "INSERT INTO CLIENTES (id, idVinculo, documento, nombre, edad, direccion) VALUES (";
		sql += cliente.getId() + ",";
		sql += cliente.getVinculo().getId() + ",";
		sql += cliente.getDocumento() + ",'";
		sql += cliente.getNombre() + "',";		
		sql += cliente.getEdad() + ",'";
		sql += cliente.getDireccion() + "')";		

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		prepStmt.close();
	}

	public void updateCliente(Cliente cliente) throws SQLException, Exception {
		String sql = "UPDATE CLIENTES SET ";
		sql += "idVinculo = " + cliente.getVinculo().getId() + ",";
		sql += "documento = " + cliente.getDocumento() + ",";
		sql += "nombre = '" + cliente.getNombre() + "',";		
		sql += "edad = " + cliente.getEdad() + ",";
		sql += "direccion = '" + cliente.getDireccion();
		sql += "' WHERE ID = " + cliente.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		prepStmt.close();
	}

	public void deleteCliente(Cliente cliente) throws SQLException, Exception {
		String sql = "DELETE FROM CLIENTES";
		sql += " WHERE ID = " + cliente.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		prepStmt.close();
	}

	public Cliente buscarCliente(long id) throws SQLException, Exception {
		String sql = "SELECT * FROM CLIENTES WHERE ID  =" + id;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
		{
			throw new Exception ("No se encontró ningún cliente con el id = "+id);
		}
		
		long identificacion = Long.parseLong(rs.getString("DOCUMENTO"));
		String nombre = rs.getString("NOMBRE");
		int edad = Integer.parseInt(rs.getString("EDAD"));
		String direccion = rs.getString("DIRECCION");
		DAOVinculo daoVinculo = new DAOVinculo();			
		daoVinculo.setConn(conn);		
		Vinculo vinculo= daoVinculo.buscarVinculo(Long.parseLong(rs.getString("IDVINCULO")));			

		DAOReserva daoReserva = new DAOReserva();
		daoReserva.setConn(conn);

		List<Long> reservas = daoReserva.buscarReservasIdCliente(id);

		prepStmt.close();
		
		return new Cliente(id, identificacion, nombre, edad, direccion, vinculo, reservas);
	}

	public Cliente buscarClienteIdReserva(long id) throws SQLException, Exception {
		String sql = "SELECT * FROM RESERVAS WHERE ID  =" + id ;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
		{
			return null;
		}
		
		Long idCliente = Long.parseLong(rs.getString("IDCLIENTE"));
		
		prepStmt.close();
		
		return buscarCliente(idCliente);
	}
	
	//RFC5
	
	public void obtenerUsosPorCategoria(List<RFC5> lista) throws SQLException, Exception
	{
		DAOVinculo daoVinculo = new DAOVinculo();
		daoVinculo.setConn(conn);
		
		DAOCategoriaServicio daoCatServicio = new DAOCategoriaServicio();
		daoCatServicio.setConn(conn);
		
		String sql = "SELECT VINCULOS.ID, SUM(RESERVAS.DURACION) AS DIASTOTAL, SUM(RESERVAS.PRECIO) AS DINEROTOTAL "+
				"FROM CLIENTES, RESERVAS, VINCULOS "+
				"WHERE CLIENTES.ID = RESERVAS.IDCLIENTE AND CLIENTES.IDVINCULO = VINCULOS.ID "+
				"GROUP BY VINCULOS.ID "+
				"ORDER BY VINCULOS.ID ASC";		
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) 
		{
			long id = Long.parseLong(rs.getString("ID"));
			String categoria = daoVinculo.buscarVinculo(id).getNombre();
			int diasTotal = Integer.parseInt(rs.getString("DIASTOTAL"));
			double dineroTotal = Double.parseDouble(rs.getString("DINEROTOTAL"));
			
			String sqlC = "SELECT ID, IDCATEGORIA FROM(SELECT DISTINCT VINCULOS.ID, SERVICIOS.IDCATEGORIA "+
					"FROM CLIENTES, RESERVAS, ESPACIOS, SERVICIOS, VINCULOS "+
					"WHERE CLIENTES.ID = RESERVAS.IDCLIENTE AND RESERVAS.IDESPACIO = ESPACIOS.ID AND ESPACIOS.ID = SERVICIOS.IDESPACIO AND VINCULOS.ID = CLIENTES.IDVINCULO "+
					"ORDER BY VINCULOS.ID ASC) WHERE ID = " + id;
			
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
			
			RFC5 resultante =  new RFC5("Cliente", categoria, diasTotal, dineroTotal, servicios);
			lista.add(resultante);
		}
		
		prepStmt.close();
		
	}
	
	//RFC6
	
	public RFC6 obtenerUsoPorUsuario(long id) throws SQLException, Exception
	{				
		DAOCategoriaServicio daoCatServicio = new DAOCategoriaServicio();
		daoCatServicio.setConn(conn);
		
		String sql = "SELECT CLIENTES.ID, SUM(RESERVAS.DURACION) AS DIASTOTAL, SUM(RESERVAS.PRECIO) AS DINEROTOTAL "+
				"FROM CLIENTES, RESERVAS "+
				"WHERE CLIENTES.ID = RESERVAS.IDCLIENTE AND CLIENTES.ID = " + id +
				" GROUP BY CLIENTES.ID "+
				"ORDER BY CLIENTES.ID ASC";		
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();	
		
		if(!rs.next()) 
		{		
			buscarCliente(id);
			RFC6 resultante =  new RFC6(id, "Cliente", 0, 0, new ArrayList<String>());		
			return resultante;			
		}
		else
		{
			int diasTotal = Integer.parseInt(rs.getString("DIASTOTAL"));
			double dineroTotal = Double.parseDouble(rs.getString("DINEROTOTAL"));
			
			String sqlC = "SELECT DISTINCT CLIENTES.ID, SERVICIOS.IDCATEGORIA "+
					"FROM CLIENTES, RESERVAS, ESPACIOS, SERVICIOS "+
					"WHERE CLIENTES.ID = RESERVAS.IDCLIENTE AND RESERVAS.IDESPACIO = ESPACIOS.ID AND ESPACIOS.ID = SERVICIOS.IDESPACIO AND CLIENTES.ID = "+ id +
					" ORDER BY CLIENTES.ID ASC";
			
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
			
			RFC6 resultante =  new RFC6(id, "Cliente", diasTotal, dineroTotal, servicios);		
			
			prepStmt.close();
			prepStmtC.close();
			
			return resultante;
		}				
		
	}
	
	//RFC10 y RFC11
	
	public List<RFC10> consultarConsumo(ListaRFC10 listaRFC10, int requerimiento) throws SQLException, Exception
	{			
		List<RFC10> resultado = new ArrayList<RFC10>();	
		
		String sql = 	"SELECT * "+
						"FROM CLIENTES,  (SELECT CLIENTES.ID, SUM(RESERVAS.PRECIO) AS GASTOS, COUNT(RESERVAS.ID) AS RESERVAS, "+
			                    "AVG(RESERVAS.DURACION) AS DURACIONPROMEDIO, AVG(ESPACIOS.TAMAÑO) AS TAMAÑOPROMEDIO, "+
			                    "STATS_MODE(OPERADORES.IDCATEGORIA) OPERADORFRECUENTE, "+
			                    "STATS_MODE(HABITACIONES.IDCATEGORIA) HABITACIONFRECUENTE, "+
			                    "STATS_MODE(SERVICIOS.IDCATEGORIA) AS SERVICIOFRECUENTE "+
			                "FROM CLIENTES, RESERVAS, ESPACIOS, SERVICIOS, HABITACIONES, OPERADORES "+
			                "WHERE CLIENTES.ID IN    (SELECT DISTINCT CLIENTES.ID "+
			                                        "FROM CLIENTES, RESERVAS "+
			                                        "WHERE CLIENTES.ID = RESERVAS.IDCLIENTE "+ 
			                                            "AND RESERVAS.IDESPACIO = "+ listaRFC10.getIdEspacio() + " "+
			                                            "AND RESERVAS.FECHARESERVA <= TO_DATE('"+ listaRFC10.getFechaExterior() + "','DD-MM-YYYY') "+ 
			                                            "AND RESERVAS.FECHARESERVA >= TO_DATE('"+ listaRFC10.getFechaInferior() + "','DD-MM-YYYY')) "+
			                    "AND RESERVAS.IDCLIENTE = CLIENTES.ID AND RESERVAS.IDESPACIO = ESPACIOS.ID  "+ 
			                    "AND ESPACIOS.ID = SERVICIOS.IDESPACIO AND ESPACIOS.ID = HABITACIONES.IDESPACIO "+ 
			                    "AND OPERADORES.ID = ESPACIOS.IDOPERADOR "+
			                "GROUP BY CLIENTES.ID) DATOSAGRUPADOS "+
						"WHERE CLIENTES.ID = DATOSAGRUPADOS.ID "+
						"ORDER BY "+ listaRFC10.getOrdenamiento();	
		
		if(listaRFC10.getIdOperador() != 0)
		{
			sql = 	"SELECT * "+
					"FROM CLIENTES,  (SELECT CLIENTES.ID, SUM(RESERVAS.PRECIO) AS GASTOS, COUNT(RESERVAS.ID) AS RESERVAS, "+
		                    "AVG(RESERVAS.DURACION) AS DURACIONPROMEDIO, AVG(ESPACIOS.TAMAÑO) AS TAMAÑOPROMEDIO, "+
		                    "STATS_MODE(OPERADORES.IDCATEGORIA) OPERADORFRECUENTE, "+
		                    "STATS_MODE(HABITACIONES.IDCATEGORIA) HABITACIONFRECUENTE, "+
		                    "STATS_MODE(SERVICIOS.IDCATEGORIA) AS SERVICIOFRECUENTE "+
		                "FROM CLIENTES, RESERVAS, ESPACIOS, SERVICIOS, HABITACIONES, OPERADORES "+
		                "WHERE CLIENTES.ID IN    (SELECT DISTINCT CLIENTES.ID "+
		                                        "FROM CLIENTES, RESERVAS "+
		                                        "WHERE CLIENTES.ID = RESERVAS.IDCLIENTE "+ 
		                                            "AND RESERVAS.IDESPACIO = "+ listaRFC10.getIdEspacio() + " "+
		                                            "AND RESERVAS.FECHARESERVA <= TO_DATE('"+ listaRFC10.getFechaExterior() + "','DD-MM-YYYY') "+ 
		                                            "AND RESERVAS.FECHARESERVA >= TO_DATE('"+ listaRFC10.getFechaInferior() + "','DD-MM-YYYY')) "+
		                    "AND RESERVAS.IDCLIENTE = CLIENTES.ID AND RESERVAS.IDESPACIO = ESPACIOS.ID  "+ 
		                    "AND ESPACIOS.ID = SERVICIOS.IDESPACIO AND ESPACIOS.ID = HABITACIONES.IDESPACIO "+ 
		                    "AND OPERADORES.ID = ESPACIOS.IDOPERADOR AND OPERADORES.ID = "+ listaRFC10.getIdOperador() + " "+
		                "GROUP BY CLIENTES.ID) DATOSAGRUPADOS "+
					"WHERE CLIENTES.ID = DATOSAGRUPADOS.ID "+
					"ORDER BY "+ listaRFC10.getOrdenamiento();	
		}
		
		if(requerimiento == 11 && listaRFC10.getIdOperador() != 0)
		{
			sql = 	"SELECT * "+
					"FROM CLIENTES,  (SELECT CLIENTES.ID, SUM(RESERVAS.PRECIO) AS GASTOS, COUNT(RESERVAS.ID) AS RESERVAS, "+
		                    "AVG(RESERVAS.DURACION) AS DURACIONPROMEDIO, AVG(ESPACIOS.TAMAÑO) AS TAMAÑOPROMEDIO, "+
		                    "STATS_MODE(OPERADORES.IDCATEGORIA) OPERADORFRECUENTE, "+
		                    "STATS_MODE(HABITACIONES.IDCATEGORIA) HABITACIONFRECUENTE, "+
		                    "STATS_MODE(SERVICIOS.IDCATEGORIA) AS SERVICIOFRECUENTE "+
		                "FROM CLIENTES, RESERVAS, ESPACIOS, SERVICIOS, HABITACIONES, OPERADORES "+
		                "WHERE CLIENTES.ID NOT IN    (SELECT DISTINCT CLIENTES.ID "+
		                                        "FROM CLIENTES, RESERVAS "+
		                                        "WHERE CLIENTES.ID = RESERVAS.IDCLIENTE "+ 
		                                            "AND RESERVAS.IDESPACIO = "+ listaRFC10.getIdEspacio() + " "+
		                                            "AND RESERVAS.FECHARESERVA <= TO_DATE('"+ listaRFC10.getFechaExterior() + "','DD-MM-YYYY') "+ 
		                                            "AND RESERVAS.FECHARESERVA >= TO_DATE('"+ listaRFC10.getFechaInferior() + "','DD-MM-YYYY')) "+
		                    "AND RESERVAS.IDCLIENTE = CLIENTES.ID AND RESERVAS.IDESPACIO = ESPACIOS.ID  "+ 
		                    "AND ESPACIOS.ID = SERVICIOS.IDESPACIO AND ESPACIOS.ID = HABITACIONES.IDESPACIO "+ 
		                    "AND OPERADORES.ID = ESPACIOS.IDOPERADOR AND OPERADORES.ID = "+ listaRFC10.getIdOperador() + " "+
		                "GROUP BY CLIENTES.ID) DATOSAGRUPADOS "+
					"WHERE CLIENTES.ID = DATOSAGRUPADOS.ID "+
					"ORDER BY "+ listaRFC10.getOrdenamiento();	
		}
		else if(requerimiento == 11 && listaRFC10.getIdOperador() == 0)
		{
			sql = 	"SELECT * "+
					"FROM CLIENTES,  (SELECT CLIENTES.ID, SUM(RESERVAS.PRECIO) AS GASTOS, COUNT(RESERVAS.ID) AS RESERVAS, "+
		                    "AVG(RESERVAS.DURACION) AS DURACIONPROMEDIO, AVG(ESPACIOS.TAMAÑO) AS TAMAÑOPROMEDIO, "+
		                    "STATS_MODE(OPERADORES.IDCATEGORIA) OPERADORFRECUENTE, "+
		                    "STATS_MODE(HABITACIONES.IDCATEGORIA) HABITACIONFRECUENTE, "+
		                    "STATS_MODE(SERVICIOS.IDCATEGORIA) AS SERVICIOFRECUENTE "+
		                "FROM CLIENTES, RESERVAS, ESPACIOS, SERVICIOS, HABITACIONES, OPERADORES "+
		                "WHERE CLIENTES.ID NOT IN    (SELECT DISTINCT CLIENTES.ID "+
		                                        "FROM CLIENTES, RESERVAS "+
		                                        "WHERE CLIENTES.ID = RESERVAS.IDCLIENTE "+ 
		                                            "AND RESERVAS.IDESPACIO = "+ listaRFC10.getIdEspacio() + " "+
		                                            "AND RESERVAS.FECHARESERVA <= TO_DATE('"+ listaRFC10.getFechaExterior() + "','DD-MM-YYYY') "+ 
		                                            "AND RESERVAS.FECHARESERVA >= TO_DATE('"+ listaRFC10.getFechaInferior() + "','DD-MM-YYYY')) "+
		                    "AND RESERVAS.IDCLIENTE = CLIENTES.ID AND RESERVAS.IDESPACIO = ESPACIOS.ID  "+ 
		                    "AND ESPACIOS.ID = SERVICIOS.IDESPACIO AND ESPACIOS.ID = HABITACIONES.IDESPACIO "+ 
		                    "AND OPERADORES.ID = ESPACIOS.IDOPERADOR "+
		                "GROUP BY CLIENTES.ID) DATOSAGRUPADOS "+
					"WHERE CLIENTES.ID = DATOSAGRUPADOS.ID "+
					"ORDER BY "+ listaRFC10.getOrdenamiento();	
		}
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		
		long tiempo = System.currentTimeMillis();
		
		ResultSet rs = prepStmt.executeQuery();	
		
		tiempo = System.currentTimeMillis() - tiempo;
		
		while (rs.next()) 
		{
			long idCliente = Long.parseLong(rs.getString("ID"));
			String datoSolicitado = rs.getString(listaRFC10.getEstadistica());
			
			resultado.add(new RFC10(idCliente, datoSolicitado));
		}
		
		prepStmt.close();
		
		System.out.println("Esta consulta duró " + tiempo + " milisegundos");
		
		return resultado;
	}
	
	//RFC13
	
	public List<RFC13> obtenerBuenosClientes() throws SQLException, Exception
	{			
		List<RFC13> resultado = new ArrayList<RFC13>();
		
		String sql = "SELECT IDCLIENTE, 1 AS RAZON "+
				"FROM    (SELECT IDCLIENTE, COUNT(MESES) AS CONTEO "+
				        "FROM    (SELECT DISTINCT IDCLIENTE, (EXTRACT(MONTH FROM FECHARESERVA)+ EXTRACT(YEAR FROM FECHARESERVA)*12) AS MESES "+
				                "FROM RESERVAS) "+
				        "GROUP BY IDCLIENTE) "+
				"WHERE CONTEO = (SELECT EXTRACT(MONTH FROM SYSDATE) - EXTRACT(MONTH FROM MIN(FECHARESERVA)) + (EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM MIN(FECHARESERVA)))*12 "+
				                "FROM RESERVAS) "+
				"UNION "+
				"SELECT TABLACONTEOTOTAL.IDCLIENTE, 2 AS RAZON "+
				"FROM    (SELECT IDCLIENTE, COUNT(ID) AS CONTEOTOTAL "+
				        "FROM RESERVAS "+
				        "GROUP BY IDCLIENTE) TABLACONTEOTOTAL, "+
				        "(SELECT IDCLIENTE, COUNT(ID) AS CONTEOCOSTOSOS "+
				        "FROM RESERVAS "+
				        "WHERE IDESPACIO IN (SELECT ID "+
				                            "FROM ESPACIOS "+
				                            "WHERE PRECIO >= 450000) "+
				        "GROUP BY IDCLIENTE) TABLACONTEOCOSTOSOS "+
				"WHERE TABLACONTEOTOTAL.CONTEOTOTAL = TABLACONTEOCOSTOSOS.CONTEOCOSTOSOS "+
				        "AND TABLACONTEOTOTAL.IDCLIENTE = TABLACONTEOCOSTOSOS.IDCLIENTE "+
				"UNION " +
				"SELECT TABLACONTEOTOTAL.IDCLIENTE, 3 AS RAZON "+
				"FROM    (SELECT IDCLIENTE, COUNT(ID) AS CONTEOTOTAL "+
				        "FROM RESERVAS "+
				        "GROUP BY IDCLIENTE) TABLACONTEOTOTAL, "+
				        "(SELECT IDCLIENTE, COUNT(ID) AS CONTEOCOSTOSOS "+
				        "FROM RESERVAS "+
				        "WHERE IDESPACIO IN (SELECT DISTINCT ESPACIOS.ID "+
				                            "FROM ESPACIOS, HABITACIONES "+
				                            "WHERE ESPACIOS.ID = HABITACIONES.IDESPACIO "+
				                                "AND HABITACIONES.IDCATEGORIA = 3) "+
				        "GROUP BY IDCLIENTE) TABLACONTEOCOSTOSOS "+
				"WHERE TABLACONTEOTOTAL.CONTEOTOTAL = TABLACONTEOCOSTOSOS.CONTEOCOSTOSOS "+
				        "AND TABLACONTEOTOTAL.IDCLIENTE = TABLACONTEOCOSTOSOS.IDCLIENTE";		
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		
		long tiempo = System.currentTimeMillis();
		
		ResultSet rs = prepStmt.executeQuery();	
		
		tiempo = System.currentTimeMillis() - tiempo;
		
		while (rs.next()) 
		{
			long idCliente = Long.parseLong(rs.getString("IDCLIENTE"));
			Cliente cliente = buscarCliente(idCliente);
			long razon = Long.parseLong(rs.getString("RAZON"));
			String razonS = "";
			if (razon == 1)
			{
				razonS = "Cliente que hace reservas al menos una vez cada mes";
			}
			else if (razon == 2)
			{
				razonS = "Cliente que siempre reserva en alojamientos costosos";
			}
			else
			{
				razonS = "Cliente que siempre reserva suites";
			}
			
			resultado.add(new RFC13(cliente,razonS));
		}
		
		prepStmt.close();
		
		System.out.println("Esta consulta duró " + tiempo + " milisegundos");
		
		return resultado;
	}
}
