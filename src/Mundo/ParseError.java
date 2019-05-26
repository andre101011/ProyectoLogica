package Mundo;

class ParseError extends Exception {
	private String cadena;
	private final boolean error;
	String errorString;

	public ParseError(String cadena) {
		this.cadena = cadena.toUpperCase();
		error = isError();
		if (isError())
			errorString = defineElError();
	}

	public String getErrorString() {
		return errorString;
	}

	String defineElError() {
		cadena = cadena.replaceAll(" ", "");

		for (char c : cadena.toCharArray())
			// -------------------------------------------------------------------------------------------------------------------------
			if ((!((int) c >= (int) 'A' && (int) c <= (int) 'Z')) && c != '!' && c != '^' && c != 'v' && c != '('
					&& c != ')' && c != '>' && c != '#') { // Si el caracter no es una letra entre A y Z, o un
															// ^,v,!,(,). Entonces hay un
				// problema
				return (c + " no es un operador valido ");
			}

		char[] r = cadena.toCharArray();
		int grado = 0;

		for (char c : r) // Itera por todo el arreglo
		{
			switch (c) // Y cuenta para asegurarse que para cada ( hay un )
			{
			case '(':
				grado++;
				break;
			case ')':
				grado--;
				break;
			default:
			}
		}

		if (grado != 0) {
			if (grado > 0)
				return "Error de parentesis: Falta el parentesis derecho!";
			if (grado < 0)
				return "Error de parentesis: Falta el parentesis izquierdo!";
		}

		cadena = cadena.trim();

		if (cadena.length() <= 1) {
			return ("Ingrese la expresion al menos dentro de un par de parentesis");
		}
//-------------------------------------------------------------------------------
		for (int i = 0; i < r.length; i++) { // Si hay caracteres invalidos a la izquierda o a la derecha de un atomo
			if ((int) r[i] >= (int) 'A' && (int) r[i] <= (int) 'Z') {
				if (r[i - 1] != '!' && r[i - 1] != '(' && r[i - 1] != 'v' && r[i - 1] != '^' && r[i - 1] != '>'
						&& r[i - 1] != '#') {
					return ("Caracter invalido: Es invalido para " + r[i] + " tener un '" + r[i - 1]
							+ "'a su izquierda.");

				}

				if (r[i + 1] != ')' && r[i + 1] != '^' && r[i + 1] != 'v' && r[i + 1] != '>' && r[i + 1] != '#') {
					return ("Caracter invalido: Es invalido para " + r[i] + "  tener un '" + r[i + 1]
							+ "'a su derecha.");
				}
			} else if (r[i] == 'v' || r[i] == '^' || r[i] == '>' || r[i] == '#') {
				if ((!(r[i + 1] >= (int) 'A' && (int) r[i + 1] <= (int) 'Z')) && r[i + 1] != '(') {
					return ("Caracter invalido: Un no-atomo debe comenzar con '('.");
				}
			}
		}
		return "Se cayó el sistema, contacte al desarrollador.";
	}

	// -----------------------------------
	public boolean isError() {
		cadena = cadena.replaceAll(" ", "");
		for (char c : cadena.toCharArray())
			if ((!((int) c >= (int) 'A' && (int) c <= (int) 'Z')) && c != '!' && c != '^' && c != 'v' && c != '('
					&& c != ')' && c != '>' && c != '#') {
				// System.out.println("Illegal Character '" + c + "'");
				cadena.replace(c, '?'); // Si no es una letra
			}
		if (cadena.contains("?"))
			return true; // Si nuestra expresion contiene algun caracter invalido

		char[] arregloDeChars = cadena.toCharArray();
		int grado = 0;

		for (char caracter : arregloDeChars) {
			switch (caracter) {
			case '(':
				grado++;
				break;
			case ')':
				grado--;
				break;
			default:
			}
		}

		if (grado != 0)
			return true; // Si algun parentesis se abre y no cierra o viceversa

		cadena = cadena.trim();

		if (cadena.length() <= 1) {
			return true;
		}

		for (int i = 0; i < arregloDeChars.length; i++) { // Si hay caracteres invalidos a la derecha o izquierda
															// del atomo
			if ((int) arregloDeChars[i] >= (int) 'A' && (int) arregloDeChars[i] <= (int) 'Z') {
				if (arregloDeChars[i - 1] != '!' && arregloDeChars[i - 1] != '(' && arregloDeChars[i - 1] != 'v'
						&& arregloDeChars[i - 1] != '^' && arregloDeChars[i - 1] != '>' && arregloDeChars[i - 1] != '#')
					return true; // Verifica el char a la izquierda de i
				if (arregloDeChars[i + 1] != ')' && arregloDeChars[i + 1] != '^' && arregloDeChars[i + 1] != 'v'
						&& arregloDeChars[i + 1] != '>' && arregloDeChars[i + 1] != '#')
					return true; // Y a la derecha...

			} else if (arregloDeChars[i] == 'v' || arregloDeChars[i] == '^' || arregloDeChars[i] == '>'
					|| arregloDeChars[i] == '#')
				if ((!(arregloDeChars[i + 1] >= (int) 'A' && (int) arregloDeChars[i + 1] <= (int) 'Z'))
						&& arregloDeChars[i + 1] != '(')
					return true; // Si el char es un !, y el siquiente no es un atomo o un '('
		}

		return false;

	}
}