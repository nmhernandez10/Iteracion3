package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRFC12 {
	
	@JsonProperty("año")
	private int año;
	
	@JsonProperty(value = "elementos")
	private List<RFC12> elementos;

	public ListaRFC12(@JsonProperty("año") int año, @JsonProperty(value = "elementos") List<RFC12> elementos) {
		this.elementos = elementos;
		this.año = año;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public List<RFC12> getElementos() {
		return elementos;
	}

	public void setElementos(List<RFC12> elementos) {
		this.elementos = elementos;
	}
}