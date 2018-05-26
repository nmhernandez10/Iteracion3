package vos;

public class RFC13 
{
	Cliente cliente;
	
	String razon;

	public RFC13(Cliente cliente, String razon) 
	{
		this.cliente = cliente;
		this.razon = razon;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}
}
