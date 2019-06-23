package Mundo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import application.TreeDisplay;
import sun.util.BuddhistCalendar;

/*
 * Authors: Andres Llinas & Daniel Bonilla
 */

public class Expresion {
	private String expresionCadena = "";
	Nodo root;

	public Expresion(String unaExpresionCadena) throws FileNotFoundException {
		// Crea un nuevo objeto expresion y su arbol, y muestra su arbol
		expresionCadena = unaExpresionCadena;
		root = new Nodo(expresionCadena);
		construirArbol(root);

		// Inicializa todos los atomos como false
		int letra = 65;
		for (int i = 0; i < 26; i++) {
			setAtomo("" + (char) letra, "false");
			letra++;
		}

	}

	private void construirArbol(Nodo nodo) { // Construye el arbol desde la expresion
		String nodoCadena = nodo.getData();
		int operadorPrincipal = encontrarOperadorMasImportante(nodoCadena);
		/*
		 * Encuentra el operador más externo de la expreson (el que esta rodeado del
		 * menor numero de parentesis
		 */
		if (operadorPrincipal == -1) { // Si la expresion ya se ha dividido lo suficiente
			for (char unChar : nodo.getData().toCharArray())
				if (unChar == '(' || unChar == ')')
					nodo.setData(nodo.getData().replace(unChar, ' ')); // Si hay un parentesis, lo quita
			nodo.setData(nodo.getData().trim()); // Borra el espacio en blanco
			return;
		}

		char splitChar = nodoCadena.charAt(operadorPrincipal); // Hace split en el caracter mas importante
		if (splitChar == 'v' || splitChar == '^' || splitChar == '>' || splitChar == '#') { // Set the left and right
																							// nodes equal to the
																							// expression that is the
			String izquierdo = nodoCadena.substring(0, operadorPrincipal);
			String derecho = nodoCadena.substring(operadorPrincipal + 1, nodoCadena.length());
			nodo.setData(splitChar + "");
			nodo.setIzquierdo(new Nodo(izquierdo));
			nodo.setDerecho(new Nodo(derecho));

			construirArbol(nodo.getDerecho());
			construirArbol(nodo.getIzquierdo());
		} else {
			String derecho = nodoCadena.substring(operadorPrincipal + 1, nodoCadena.length());
			nodo.setData(splitChar + "");
			nodo.setDerecho(new Nodo(derecho));
			construirArbol(nodo.getDerecho());
		}
	}

	private int encontrarOperadorMasImportante(String cadena) {
		char[] arregloChar = cadena.toCharArray();
		int[] arregloOperadores = new int[cadena.length()];
		int grado = 0;
		int indice = 0;

		for (char unChar : arregloChar) { // Itera a traves de cada caracter en el string
			if (unChar == 'v' || unChar == '^' || unChar == '!' || unChar == '>' || unChar == '#')
				// --------------------------------------------------------------------------------------------------------------------
				arregloOperadores[indice] = grado;
			else {
				switch (unChar) {
				case '(': // Cuando los operadores dentro de este '(' están más profundos en la expresion,
							// entonces incrementa el grado
					arregloOperadores[indice] = 0;
					grado++;
					break;
				case ')':
					arregloOperadores[indice] = 0;
					grado--;
				default:
					arregloOperadores[indice] = 0;
				}
			}
			indice++;
		}

		String gradoString = "";
		for (int unOperador : arregloOperadores)
			gradoString = gradoString + unOperador;
		for (int i = 1; i < cadena.length(); i++)
			if (gradoString.indexOf(intToChar(i)) != -1)
				return gradoString.indexOf(intToChar(i));
		return -1;
	}

	private static char intToChar(int entero) {
		return (char) (entero + 48);
	}

	void setAtomo(String atomo, String valor) { // Setea un atomo igual a su valor booleano correspondiente
		setAtomoH(root, atomo.charAt(0), Boolean.parseBoolean(valor));
	}

	private static void setAtomoH(Nodo nodo, char objetivo, Boolean valorBooleano) { // Encuentra el nodo, y lo setea
																						// igual a
		// valorBooleano
		if (nodo.getData().charAt(0) == objetivo)
			nodo.setBool(valorBooleano);
		if (nodo.getDerecho() != null)
			setAtomoH(nodo.getDerecho(), objetivo, valorBooleano);
		if (nodo.getIzquierdo() != null)
			setAtomoH(nodo.getIzquierdo(), objetivo, valorBooleano);
	}

	public boolean evaluar() { // Retorna la evaluacion booleana de una expresion
		return evaluarH(root);
	}

	private boolean evaluarH(Nodo nodo) { // Funcion recursiva del metodo evaluar()
		if (nodo.getIzquierdo() == null && nodo.getDerecho() == null)
			return nodo.getBool(); // es Hoja
		else if (nodo.getData().equals("^")) // AND
		{
			return evaluarH(nodo.getIzquierdo()) && evaluarH(nodo.getDerecho());
		} else if (nodo.getData().equals("v")) // OR
		{
			return evaluarH(nodo.getIzquierdo()) || evaluarH(nodo.getDerecho());
		} else if (nodo.getData().equals("!")) // NOT
		{
			return !evaluarH(nodo.getDerecho());
		} else if (nodo.getData().equals(">")) // CONDICIONAL
		{
			return condicional(evaluarH(nodo.getIzquierdo()), evaluarH(nodo.getDerecho()));
		} else if (nodo.getData().equals("#")) // BICONDICIONAL
		{
			return bicondicional(evaluarH(nodo.getIzquierdo()), evaluarH(nodo.getDerecho()));
		}
		return false;
	}

	public static void main(String[] args) {
//		try {
//			Expresion miExp = new Expresion("((P)^(A))");
//			ArrayList<Boolean> columna = miExp.generarColumnaDeVerdad(3);
//			System.out.println(columna);
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public static void removerDuplicados(ArrayList<Nodo> lista) {
		for (int i = 0; i < lista.size(); i++) {
			for (int j = i + 1; j < lista.size(); j++) {
				if (lista.get(i).equals(lista.get(j))) {
					lista.remove(j);
					j--;
				}
			}
		}
	}

	public ArrayList<Boolean> generarColumnaDeVerdad() {
		ArrayList<Boolean> columnaVerdad = new ArrayList<>();
		ArrayList<Nodo> hojas = new ArrayList<>();
		devolverHojas(root, hojas);
		ArrayList<Nodo> hojasSinRepetir = devolverHojasSinRepetir();

		int numFilas = (int) Math.pow(2, hojasSinRepetir.size());
		int k = numFilas / 2;
		ArrayList<Integer> cadaCuantoCambianLosAtomos = new ArrayList<>();
		for (int i = 0; i < hojasSinRepetir.size(); i++) {
			cadaCuantoCambianLosAtomos.add(k);
			k /= 2;
		}

		for (int j = 0; j < numFilas; j++) {// Itera a traves de las filas que tendría la tabla de verdad
			for (int i = 0; i < hojasSinRepetir.size(); i++) {// Itera en la cantidad de atomos de la expresion
				if (j % cadaCuantoCambianLosAtomos.get(i) == 0) {
					hojasSinRepetir.get(i).setBool(!(hojasSinRepetir.get(i).getBool()));
				}
				for (Nodo nodo : hojas) {
					if (nodo.equals(hojas.get(i))) {
						nodo.setBool(hojasSinRepetir.get(i).getBool());
					}
				}
			}
			columnaVerdad.add(this.evaluar());
		}
		return columnaVerdad;
	}

	public Nodo buscarNodo(String data, Nodo nodo) {
		if (nodo != null) {
			if (nodo.getData().equals(data)) {
				return nodo;
			} else {
				Nodo nodoEncontrado = buscarNodo(data, nodo.getIzquierdo());
				if (nodoEncontrado == null) {
					nodoEncontrado = buscarNodo(data, nodo.getDerecho());
				}
				return nodoEncontrado;
			}
		} else {
			return null;
		}
	}

	public void devolverHojas(Nodo nodo, ArrayList<Nodo> hojas) {

		if (nodo == null)
			return;

		if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
			hojas.add(nodo);
		}
		devolverHojas(nodo.getIzquierdo(), hojas);
		devolverHojas(nodo.getDerecho(), hojas);
	}

	public ArrayList<Nodo> devolverHojasSinRepetir() {
		ArrayList<Nodo> hojas = new ArrayList<>();
		devolverHojas(root, hojas);
		ArrayList<Nodo> hojasSinRepetir = new ArrayList<>(hojas);
		removerDuplicados(hojasSinRepetir);
		for (Nodo hoja : hojas) {
			if (!hojasSinRepetir.contains(hoja)) {
				hojasSinRepetir.add(hoja);
			}
		}
		return hojasSinRepetir;
	}

//	private int getNumHojas(Nodo nodo) {
//		if (nodo == null)
//			return 0;
//		if (nodo.getIzquierdo() == null && nodo.getDerecho() == null)
//			return 1;
//		else
//			return getNumHojas(nodo.getIzquierdo()) + getNumHojas(nodo.getDerecho());
//	}

	public boolean condicional(boolean atomo1, boolean atomo2) {
		return (atomo1 && !atomo2) ? false : true;
	}

	public boolean bicondicional(boolean atomo1, boolean atomo2) {
		return (atomo1 == atomo2);
	}

	public Expresion copia() {
		try {
			return new Expresion(expresionCadena); // Retorna una nueva expresion hecha a partir de la expresion antigua
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado! Verifique directorios.");
			return null;

		}
	}

	public String toString() { // Devuelve la expresion en forma de cadena de caracteres.
		return expresionCadena;
	}

	/**
	 * @return the root
	 */
	public Nodo getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(Nodo root) {
		this.root = root;
	}
}