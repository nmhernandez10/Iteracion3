package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRFC10 {
	
	@JsonProperty(value = "idEspacio")
	private long idEspacio;
	
	@JsonProperty(value = "idOperador")
	private long idOperador;
	
	@JsonProperty(value = "fechaInferior")
	private String fechaInferior;
	
	@JsonProperty(value = "fechaExterior")
	private String fechaExterior;
	
	@JsonProperty(value = "estadistica")
	private String estadistica;
	
	@JsonProperty(value = "ordenamiento")
	private String ordenamiento;
	
	@JsonProperty(value = "elementos")
	private List<RFC10> elementos;

	public ListaRFC10(@JsonProperty(value = "idEspacio") long idEspacio, @JsonProperty(value = "idOperador") long idOperador, @JsonProperty(value = "fechaInferior") String fechaInferior, @JsonProperty(value = "fechaExterior") String fechaExterior, @JsonProperty(value = "estadistica") String estadistica,
			@JsonProperty(value = "ordenamiento") String ordenamiento, @JsonProperty(value = "elementos") List<RFC10> elementos) {
		this.idEspacio = idEspacio;
		this.idOperador = idOperador;
		this.fechaInferior = fechaInferior;
		this.fechaExterior = fechaExterior;
		this.estadistica = estadistica;
		this.ordenamiento = ordenamiento;
		this.elementos = elementos;
	}

	public long getIdEspacio() {
		return idEspacio;
	}

	public void setIdEspacio(long idEspacio) {
		this.idEspacio = idEspacio;
	}

	public long getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(long idOperador) {
		this.idOperador = idOperador;
	}

	public String getFechaInferior() {
		return fechaInferior;
	}

	public void setFechaInferior(String fechaInferior) {
		this.fechaInferior = fechaInferior;
	}

	public String getFechaExterior() {
		return fechaExterior;
	}

	public void setFechaExterior(String fechaExterior) {
		this.fechaExterior = fechaExterior;
	}

	public String getEstadistica() {
		return estadistica;
	}

	public void setEstadistica(String estadistica) {
		this.estadistica = estadistica;
	}

	public List<RFC10> getElementos() {
		return elementos;
	}

	public void setElementos(List<RFC10> elementos) {
		this.elementos = elementos;
	}

	public String getOrdenamiento() {
		return ordenamiento;
	}

	public void setOrdenamiento(String ordenamiento) {
		this.ordenamiento = ordenamiento;
	}
}
