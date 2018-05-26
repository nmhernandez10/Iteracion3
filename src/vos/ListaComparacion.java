package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaComparacion {
	@JsonProperty(value = "elementos")
	private List<Comparacion> elementos;

	public ListaComparacion(@JsonProperty(value = "elementos") List<Comparacion> elementos) {
		this.elementos = elementos;
	}

	public List<Comparacion> getElementos() {
		return elementos;
	}

	public void setElementos(List<Comparacion> elementos) {
		this.elementos = elementos;
	}
}
