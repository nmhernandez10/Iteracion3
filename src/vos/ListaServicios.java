package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Nicol�s Mateo Hern�ndez Rojas - nm.hernandez10@uniandes.edu.co
 * @author David Felipe Ni�o Romero - df.nino10@uniandes.edu.co Clase que
 *         representa a la lista de Servicios del modelo AlohAndes
 */

public class ListaServicios {
	@JsonProperty(value = "servicios")
	private List<Servicio> servicios;

	public ListaServicios(@JsonProperty(value = "servicios") List<Servicio> servicios) {
		this.servicios = servicios;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}
}
