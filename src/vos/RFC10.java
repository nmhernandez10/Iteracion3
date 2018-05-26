package vos;

public class RFC10 
{
	private long idCLiente;
	
	private String datoSolicitado;

	public RFC10(long idCLiente, String datoSolicitado) {
		this.idCLiente = idCLiente;
		this.datoSolicitado = datoSolicitado;
	}

	public long getIdCLiente() {
		return idCLiente;
	}

	public void setIdCLiente(long idCLiente) {
		this.idCLiente = idCLiente;
	}

	public String getDatoSolicitado() {
		return datoSolicitado;
	}

	public void setDatoSolicitado(String datoSolicitado) {
		this.datoSolicitado = datoSolicitado;
	}
}
