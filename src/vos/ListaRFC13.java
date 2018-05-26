package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRFC13 {
	@JsonProperty(value = "elementos")
	private List<RFC13> elementos;

	public ListaRFC13(@JsonProperty(value = "elementos") List<RFC13> elementos) {
		this.elementos = elementos;
	}

	public List<RFC13> getElementos() {
		return elementos;
	}

	public void setElementos(List<RFC13> elementos) {
		this.elementos = elementos;
	}
}