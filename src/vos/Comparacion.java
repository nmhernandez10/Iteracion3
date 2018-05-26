package vos;

public class Comparacion {
	private long idCliente;
	
	private long numReservas;

	public Comparacion(long idCliente, long numReservas) {

		this.idCliente = idCliente;
		this.numReservas = numReservas;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public long getNumReservas() {
		return numReservas;
	}

	public void setNumReservas(long numReservas) {
		this.numReservas = numReservas;
	}
	
	

}
