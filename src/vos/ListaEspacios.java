package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Nicol�s Mateo Hern�ndez Rojas - nm.hernandez10@uniandes.edu.co
 * @author David Felipe Ni�o Romero - df.nino10@uniandes.edu.co Clase que
 *         representa a la lista de Espacios del modelo AlohAndes
 */

public class ListaEspacios {
	@JsonProperty(value = "espacios")
	private List<Espacio> espacios;

	public ListaEspacios(@JsonProperty(value = "espacios") List<Espacio> espacios) {
		this.espacios = espacios;
	}

	public List<Espacio> getEspacios() {
		return espacios;
	}

	public void setEspacios(List<Espacio> espacios) {
		this.espacios = espacios;
	}
}
