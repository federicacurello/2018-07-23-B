package it.polito.tdp.newufosightings.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Evento {
	private TipoEvento tipo;
	private LocalDateTime data;
	private State stato;

	public enum TipoEvento{
		DECREMENTA,
		INCREMENTA,
		EMERGENZA,
		FINE,
	}

	public Evento(TipoEvento tipo, LocalDateTime localDateTime, State stato) {
		this.tipo = tipo;
		this.data = localDateTime;
		this.stato=stato;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public State getStato() {
		return stato;
	}

	public void setStato(State stato) {
		this.stato = stato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (stato == null) {
			if (other.stato != null)
				return false;
		} else if (!stato.equals(other.stato))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	

	
	

}
