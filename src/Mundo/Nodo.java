package Mundo;

public class Nodo {

	private Nodo izquierdo, derecho;
	private String data;
	private Boolean valorBooleano;

	public Nodo(String simbolo) {
		izquierdo = null;
		derecho = null;
		setData(simbolo);
	}

	public Nodo(String simbolo, Nodo r, Nodo l) {
		izquierdo = l;
		derecho = r;
		setData(simbolo);
	}

	public void setBool(Boolean booleano) {
		valorBooleano = booleano;
	}

	public Boolean getBool() {
		return valorBooleano;
	}

	public void setDerecho(Nodo r) {
		derecho = r;
	}

	public Nodo getDerecho() {
		return derecho;
	}

	public void setIzquierdo(Nodo l) {
		izquierdo = l;
	}

	public Nodo getIzquierdo() {
		return izquierdo;
	}

	public String getData() {
		return data;
	}

	public void setData(String simbolo) {
		this.data = simbolo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nodo other = (Nodo) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

}