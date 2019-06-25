package modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class ConjuntoExpresiones {

	private ArrayList<Expresion> expresiones;
	private Expresion expresionConjunto;
	private ArrayList<String> expresionesCadena;
	private String conclusion;
	private String conjuntoCadena;

	public ConjuntoExpresiones(ArrayList<String> expresionesCadena, String conclusion) {
		this.expresionesCadena = expresionesCadena;
		this.conclusion = conclusion;
	}

	public boolean verificarValidez() {
		conjuntoCadena = "";
		Boolean esValida = false;
		for (int i = 0; i < expresionesCadena.size(); i++) {

			String expresionCadena = expresionesCadena.get(i);
//			expresionCadena = "(" + expresionCadena + ")";
			ParseError posibleError = new ParseError(expresionCadena); // Asegurate de que no estas parseando mal la
																		// cadena
			if (!posibleError.isError()) {

				if (i != 0) {
					conjuntoCadena = "(" + conjuntoCadena;
				}

//				expresionCadena = "(" + expresionCadena + ")";

				if (i == 0) {
					conjuntoCadena += expresionCadena;
				} else if (i <= expresionesCadena.size() - 2) {
					conjuntoCadena += "^" + expresionCadena + ")";
				} else if (i == expresionesCadena.size() - 1) {
					conjuntoCadena += ">" + expresionCadena + ")";
				}

			}
		}

		conjuntoCadena = "(" + conjuntoCadena + ")";
		ParseError posibleError = new ParseError(conjuntoCadena); // Asegurate de que no estas parseando mal la
		// cadena
		if (!posibleError.isError()) {

			expresionConjunto = new Expresion(conjuntoCadena);
			// PRUEBA
			ArrayList<Nodo> hojas = expresionConjunto.devolverHojasSinRepetir();
			System.out.println("hojas = ");
			for (Nodo nodo : hojas) {
				System.out.println(nodo.getData());
			}
			System.out.println(expresionConjunto.generarColumnaDeVerdad());
			// PRUEBA/
			boolean tautologia = true;
			for (Boolean valor : expresionConjunto.generarColumnaDeVerdad()) {
				if (valor != true) {
					tautologia = false;
				}
			}
			if (tautologia) {
				esValida = true;
			}
		}
		return esValida;
	}

	/**
	 * @return the expresiones
	 */
	public ArrayList<Expresion> getExpresiones() {
		return expresiones;
	}

	/**
	 * @param expresiones the expresiones to set
	 */
	public void setExpresiones(ArrayList<Expresion> expresiones) {
		this.expresiones = expresiones;
	}

	/**
	 * @return the expresionConjunto
	 */
	public Expresion getExpresionConjunto() {
		return expresionConjunto;
	}

	/**
	 * @param expresionConjunto the expresionConjunto to set
	 */
	public void setExpresionConjunto(Expresion expresionConjunto) {
		this.expresionConjunto = expresionConjunto;
	}

	/**
	 * @return the expresionesCadena
	 */
	public ArrayList<String> getExpresionesCadena() {
		return expresionesCadena;
	}

	/**
	 * @param expresionesCadena the expresionesCadena to set
	 */
	public void setExpresionesCadena(ArrayList<String> expresionesCadena) {
		this.expresionesCadena = expresionesCadena;
	}

	/**
	 * @return the conclusion
	 */
	public String getConclusion() {
		return conclusion;
	}

	/**
	 * @param conclusion the conclusion to set
	 */
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	/**
	 * @return the conjuntoCadena
	 */
	public String getConjuntoCadena() {
		return conjuntoCadena;
	}

	/**
	 * @param conjuntoCadena the conjuntoCadena to set
	 */
	public void setConjuntoCadena(String conjuntoCadena) {
		this.conjuntoCadena = conjuntoCadena;
	}
}
