package vos;

import java.util.List;

public class RFC12 
{
	private int semana;
	private Operador operadorMasSolicitado;
	private Operador operadorMenosSolicitado;
	private Espacio espacioMasOcupado;
	private Espacio espacioMenosOcupado;
	public RFC12(int semana, Operador operadorMasSolicitado, Operador operadorMenosSolicitado,
			Espacio espacioMasOcupado, Espacio espacioMenosOcupado) {
		this.semana = semana;
		this.operadorMasSolicitado = operadorMasSolicitado;
		this.operadorMenosSolicitado = operadorMenosSolicitado;
		this.espacioMasOcupado = espacioMasOcupado;
		this.espacioMenosOcupado = espacioMenosOcupado;
	}
	public int getSemana() {
		return semana;
	}
	public void setSemana(int semana) {
		this.semana = semana;
	}
	public Operador getOperadorMasSolicitado() {
		return operadorMasSolicitado;
	}
	public void setOperadorMasSolicitado(Operador operadoreMasSolicitado) {
		this.operadorMasSolicitado = operadoreMasSolicitado;
	}
	public Operador getOperadorMenosSolicitado() {
		return operadorMenosSolicitado;
	}
	public void setOperadorMenosSolicitado(Operador operadoreMenosSolicitado) {
		this.operadorMenosSolicitado = operadoreMenosSolicitado;
	}
	public Espacio getEspacioMasOcupado() {
		return espacioMasOcupado;
	}
	public void setEspacioMasOcupado(Espacio espacioMasOcupado) {
		this.espacioMasOcupado = espacioMasOcupado;
	}
	public Espacio getEspacioMenosOcupado() {
		return espacioMenosOcupado;
	}
	public void setEspacioMenosOcupado(Espacio espacioMenosOcupado) {
		this.espacioMenosOcupado = espacioMenosOcupado;
	}
}
