package Mundo;

public class Nodo {

	Nodo izquierdo, derecho;
	private String simbolo;
	private Boolean valorBooleano; // Boolean value for this node

	public Nodo(String s) {
		izquierdo = null;
		derecho = null;
		setSimbolo(s);
	}

	public Nodo(String s, Nodo r, Nodo l) {
		izquierdo = l;
		derecho = r;
		setSimbolo(s);
	}

	public void setBool(Boolean pop) {
		valorBooleano = pop;
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
		return getSimbolo();
	}

	public void setData(String s) {
		setSimbolo(s);
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

}