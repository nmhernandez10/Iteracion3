package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRFC12 {
	
	@JsonProperty("a�o")
	private int a�o;
	
	@JsonProperty(value = "elementos")
	private List<RFC12> elementos;

	public ListaRFC12(@JsonProperty("a�o") int a�o, @JsonProperty(value = "elementos") List<RFC12> elementos) {
		this.elementos = elementos;
		this.a�o = a�o;
	}

	public int getA�o() {
		return a�o;
	}

	public void setA�o(int a�o) {
		this.a�o = a�o;
	}

	public List<RFC12> getElementos() {
		return elementos;
	}

	public void setElementos(List<RFC12> elementos) {
		this.elementos = elementos;
	}
}