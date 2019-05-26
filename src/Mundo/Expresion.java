package Mundo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import application.TreeDisplay;

public class Expresion {
	private String expresionCadena = "";
	Nodo root;

	public Expresion(String unaExpresionCadena) throws FileNotFoundException {
		// Crea un nuevo objeto expresion y su arbol, y muestra su arbol
		expresionCadena = unaExpresionCadena;
		root = new Nodo(expresionCadena);
		construirArbol(root);

		Scanner archivoEscaneado = new Scanner(new File("src/Mundo/ejemploEvaluacion.txt"));

		while (archivoEscaneado.hasNext()) {
			String linea = archivoEscaneado.nextLine();
			if (linea.charAt(0) != '(') {
				String[] lineaDividida = linea.split(" ");
				setAtomo(lineaDividida[0], lineaDividida[1]);
			}
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
//-----------------------------------------------------------------------------------------------------------------------------------------------
		if (splitChar == 'v' || splitChar == '^' || splitChar == '>' || splitChar == '#') { // Set the left and right
																							// nodes equal to the
																							// expression that is the
			// left and right of the MSC
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
			return condicional(evaluarH(nodo.getIzquierdo()), evaluarH(nodo.getDerecho()));
		}
//-------------------------------------------------------------------------------------------------------------------------------------------
		return false;
	}

//------------------------------------------------------------------------------------------------------------------------------------
	public boolean condicional(boolean atomo1, boolean atomo2) {
		return (atomo1 && !atomo2) ? false : true;
	}

	public boolean bicondicional(boolean atomo1, boolean atomo2) {
		return (atomo1 == atomo2);
	}

//	public static void main(String[] args) {
//		// prueba condicional
//		System.out.println("0  0 " + condicional(false, false));
//		System.out.println("0  1 " + condicional(false, true));
//		System.out.println("1  0 " + condicional(true, false));
//		System.out.println("1  1 " + condicional(true, true));
//
//		// prueba bicondicional
//		System.out.println("\n bicondicional:");
//		System.out.println("0  0 " + bicondicional(false, false));
//		System.out.println("0  1 " + bicondicional(false, true));
//		System.out.println("1  0 " + bicondicional(true, false));
//		System.out.println("1  1 " + bicondicional(true, true));
//	}

	public Expresion copia() {
		try {
			return new Expresion(expresionCadena); // Retorna una nueva expresion hecha a partir de la expresion antigua
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado! Verifique directorios.");
			return null;

		}
	}

	Nodo normalizador() { // Normaliza un arbol
		while (isNormal(root) == false)
			root = normalizadorH(root);
		return root;
	}

	Nodo normalizadorH(Nodo nodo) { // funcion recursiva para normalizador
		switch (nodo.getData().charAt(0)) {
		case '!':
			if (nodo.getDerecho().getData().charAt(0) == '!') // Si tiene un ! sobre un !
				return normalizadorH(nodo.getDerecho().getDerecho()); // Entonces los quita del arbol
			else if (nodo.getDerecho().getData().charAt(0) == 'v') // Si tienes un ! sobre un ^
			{
				Nodo nodoConjuncion = new Nodo("^"); // Crea nodos nuevos
				Nodo izquierdo = new Nodo("!");
				Nodo derecho = new Nodo("!");

				nodoConjuncion.setIzquierdo(izquierdo); // Setea las dos negaciones como hijas del nodo ^.
				nodoConjuncion.setDerecho(derecho);

				izquierdo.setDerecho(normalizadorH(nodo.getDerecho().getIzquierdo()));
				// Y copia la expresion a partir de ahi en adelante
				derecho.setDerecho(normalizadorH(nodo.getDerecho().getDerecho()));
				return nodoConjuncion;
			} else
				return normalizadorH(nodo.getDerecho());
		case '^':
			if (nodo.getDerecho().getData().charAt(0) == 'v') // Si tiene un ^ sobre un v
			{
				Nodo nodoDisyuncion = new Nodo("v"); // Crea nodos nuevos
				Nodo izquierdo = new Nodo("^");
				Nodo derecho = new Nodo("^");

				nodoDisyuncion.setIzquierdo(izquierdo); // Hace los nodos v hijos del nodo ^
				nodoDisyuncion.setDerecho(derecho);

				izquierdo.setIzquierdo(nodo.getIzquierdo()); // Setea el nodo izquierdo de cada uno de estos hijos igual
																// al nodo
				// que se encuentra a la izquierda del nodo ^
				derecho.setIzquierdo(nodo.getIzquierdo());

				izquierdo.setDerecho(normalizadorH(nodo.getDerecho().getIzquierdo()));
				// Y el nodo derecho debe ser lo que esté a la derecha e izquierda del nodo v
				// anterior.
				derecho.setDerecho(normalizadorH(nodo.getDerecho().getDerecho()));

				return nodoDisyuncion;
			} else {
				nodo.setIzquierdo(normalizadorH(nodo.getIzquierdo()));
				nodo.setDerecho(normalizadorH(nodo.getDerecho()));
				return nodo;
			}
		case 'v':
			nodo.setIzquierdo(normalizadorH(nodo.getIzquierdo()));
			nodo.setDerecho(normalizadorH(nodo.getDerecho()));
			return nodo;
		default:
			return nodo;
		}
	}

	Boolean isNormal(Nodo nodo) { // Verifica un arbol para ver si esta en forma normal
		if (nodo.getIzquierdo() == null && nodo.getDerecho() == null)
			return true; // Si llegó hasta un atomo, retorna true

		switch (nodo.getData().charAt(0)) { // Si este nodo-simbolo es un...
		case '!':
			if (nodo.getDerecho().getData().charAt(0) == '!')
				return false; // Y el texto en el nodo también es un !, entonces no es normal
			else if (nodo.getDerecho().getData().charAt(0) == 'v')
				return false; // Si el texto en el nodo es un v, entonces no es normal
			else
				return isNormal(nodo.getDerecho()); // De otra manera, siga verificando
		case '^':
			if (nodo.getDerecho().getData().charAt(0) == 'v')
				return false; // Si tiene un ^ sobre (over) un v, entonces la expresion no es normal
		default:
			return (isNormal(nodo.getDerecho()) && isNormal(nodo.getIzquierdo())); // Sigue verificando...
		}
	}

	public void graficarNormalizado() { // muestra el arbol normalizado.
		this.normalizador();
		TreeDisplay displayNormalizado = new TreeDisplay("Normalizado " + this.toString());
		displayNormalizado.setRoot(this.root);
	}

	public String toString() { // returns the print form of an expression.
		return expresionCadena;
	}
}