package Mundo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextField;

import application.TreeDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;
import sun.security.x509.IssuingDistributionPointExtension;

/*
 * Authors: Andres Llinas & Daniel Bonilla
 */

public class ControladorPrincipal {

	@FXML
	private TextArea jTextArea;

	private int ultimoCaret;

	@FXML
	public void initialize() {
		jTextArea.setText("(A)^(B)\n((A)v((C)^(A)))");
	}

	@FXML
	public void graficar(ActionEvent ae) throws FileNotFoundException {

		String unaExpresionCadena = "(" + jTextArea.getText() + ")";

		ParseError posibleError = new ParseError(unaExpresionCadena); // Asegurate de que no estas parseando mal la
		try {

			// cadena
			if (posibleError.isError()) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Advertencia");
				alert.setHeaderText("Ups! Expresion invalida");
				alert.setContentText(unaExpresionCadena + " - " + posibleError.errorString);
				alert.showAndWait();

			} else {
				Expresion nuevaExpresion = new Expresion(unaExpresionCadena);

				TreeDisplay display = new TreeDisplay(nuevaExpresion.toString()); // Grafica sin normalizar.
				display.setRoot(nuevaExpresion.root);

			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Advertencia");
			alert.setHeaderText("Ups! Expresion invalida");
			alert.setContentText("¡Apagalo Otto! ¡Apagalo!");
			alert.showAndWait();
		}
	}

	@FXML
	public void evaluar(ActionEvent ae) throws FileNotFoundException {
		String unaExpresionCadena = "(" + jTextArea.getText() + ")";

		ParseError posibleError = new ParseError(unaExpresionCadena); // Asegurate de que no estas parseando mal la
																		// cadena
		if (posibleError.isError()) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Advertencia");
			alert.setHeaderText("Ups! Expresion invalida");
			alert.setContentText(unaExpresionCadena + " - " + posibleError.errorString);
			alert.showAndWait();

		} else {

			ArrayList<ArrayList<Boolean>> arrayColumnas = new ArrayList<>();
			for (String linea : jTextArea.getText().split("\\n")) {
				arrayColumnas.add(new Expresion(linea).generarColumnaDeVerdad());
			}
			// Tratando de leer varias filas de expresiones
			Expresion nuevaExpresion = new Expresion(unaExpresionCadena);

			boolean resultado = nuevaExpresion.evaluar();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Resultado");
			alert.setHeaderText("Resultado de la evaluacion booleana de la expresion");
			alert.setContentText(unaExpresionCadena + " = " + resultado);
			alert.showAndWait();

		}
	}

	@FXML
	public void verificarValidez(ActionEvent ae) throws FileNotFoundException {
		System.out.println("entra");
		String texto = jTextArea.getText();

		List<String> lista = Arrays.asList(texto.split("\\r?\\n"));
		ArrayList<String> expresionesCadena = new ArrayList<String>();
		expresionesCadena.addAll(lista);

		String conclusion = expresionesCadena.get(expresionesCadena.size() - 1);

		String conjuntoCadena = "";

		for (int i = 0; i < expresionesCadena.size(); i++) {

			String expresionCadena = expresionesCadena.get(i);

			expresionCadena = "(" + expresionCadena + ")";
			System.out.println(expresionCadena);
			ParseError posibleError = new ParseError(expresionCadena); // Asegurate de que no estas parseando mal la
																		// cadena
			if (posibleError.isError()) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Advertencia");
				alert.setHeaderText("Ups! Expresion invalida");
				alert.setContentText(expresionCadena + " - " + posibleError.errorString);
				alert.showAndWait();
				break;

			} else {

				Expresion expresion = new Expresion(expresionCadena);
				ArrayList<Nodo> hojas = expresion.devolverHojasSinRepetir();
				System.out.println("hojas = ");
				for (Nodo nodo : hojas) {
					System.out.println(nodo.getData());
				}

				if (i < expresionesCadena.size() - 2) {
					conjuntoCadena += expresionCadena + "^";
				} else if (i != expresionesCadena.size() - 1) {
					conjuntoCadena += expresionCadena + ">";
				} else {
					conjuntoCadena += expresionCadena;
				}

			}
		}
		System.out.println(conjuntoCadena);
		conjuntoCadena = "(" + conjuntoCadena + ")";
		ParseError posibleError = new ParseError(conjuntoCadena); // Asegurate de que no estas parseando mal la
		// cadena
		if (posibleError.isError()) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Advertencia");
			alert.setHeaderText("Ups! Expresion invalida");
			alert.setContentText(conjuntoCadena + " - " + posibleError.errorString);
			alert.showAndWait();

		} else {

			Expresion expresionConjunto = new Expresion(conjuntoCadena);
			ArrayList<Nodo> hojas = expresionConjunto.devolverHojasSinRepetir();
			System.out.println("hojas = ");
			for (Nodo nodo : hojas) {
				System.out.println(nodo.getData());
			}
			System.out.println(expresionConjunto.generarColumnaDeVerdad());
		}

	}

	public void agregarConjuncion(ActionEvent event) {
		if (estaDentroDeParentesis() || jTextArea.getText().isEmpty()) {
			jTextArea.insertText(ultimoCaret, ("()^()"));
		}
	}

	public void agregarDisyuncion() {
		if (estaDentroDeParentesis() || jTextArea.getText().isEmpty()) {
			jTextArea.insertText(ultimoCaret, ("()v()"));
		}
	}

	public void agregarCondicional() {
		if (estaDentroDeParentesis() || jTextArea.getText().isEmpty()) {
			jTextArea.insertText(ultimoCaret, ("()>()"));
		}
	}

	public void agregarBicondicional() {
		if (estaDentroDeParentesis() || jTextArea.getText().isEmpty()) {
			jTextArea.insertText(ultimoCaret, ("()#()"));
		}
	}

	public void agregarNegacion() {
		if (estaDentroDeParentesis() || jTextArea.getText().isEmpty()) {
			jTextArea.insertText(ultimoCaret, ("!()"));
		}
	}

	public void cerrar() {
		System.exit(0);
	}

	public void borrarTodo() {

		jTextArea.setText("");
		ultimoCaret = 0;
	}

	public void borrar() {

		String texto = jTextArea.getText();
		String cadena = "";
		int caretParaBorrar = ultimoCaret;
		int tamanio = jTextArea.getText().length();
		System.out.println(ultimoCaret);
		if (tamanio > 0) {
			char anterior = jTextArea.getText().charAt(ultimoCaret - 1);

			if (!Character.isLetter(anterior)) {
				return;
			}

			cadena += (texto.substring(0, caretParaBorrar - 1));
		}
		if (caretParaBorrar <= texto.length()) {
			cadena += texto.substring(caretParaBorrar, texto.length());
		}
		jTextArea.setText(cadena);

	}

	/**
	 * @return the ultimoCaret
	 */
	public int getUltimoCaret() {
		return ultimoCaret;
	}

	/**
	 * @param ultimoCaret the ultimoCaret to set
	 */
	public void setUltimoCaret(int ultimoCaret) {
		this.ultimoCaret = ultimoCaret;
	}

	/**
	 * @return the jtfTextoEntrada
	 */
	public TextArea getJtfTextoEntrada() {
		return jTextArea;
	}

	@FXML
	public void actualizarCaret() {
		ultimoCaret = jTextArea.getCaretPosition();

	}

	@FXML
	// Valida las teclas no caracteres, como pueden ser Ctrl, ALT, Enter
	public void validarTeclasNoCaracteres(KeyEvent key) {

//		actualizarCaret();
//
//		if (key.getCode().equals(KeyCode.LEFT) || key.getCode().equals(KeyCode.RIGHT)) {
//			key.consume();
//		}
//		int tamanioTexto = jTextArea.getText().length();
//
//		// Valida que si hay un operador o parentesis antes del cursor, no se pueda
//		// borrar
//		if (tamanioTexto > 0 && ultimoCaret > 0) {
//			char charAnterior = jTextArea.getText().charAt(ultimoCaret - 1);
//
//			if (isOperador(charAnterior) && key.getCode().equals(KeyCode.BACK_SPACE)) {
//				key.consume();
//			}
////			if (charAnterior==')'&& ultimoCaret) {
////				
////			}
//		}
//
//		// Valida que si hay un operador inmediatamente despues del cursor, este no
//		// se pueda eliminar con la tecla suprimir
//		if (ultimoCaret < tamanioTexto) {
//			char charPosterior = jTextArea.getText().charAt(ultimoCaret);
//
//			if (isOperador(charPosterior) && key.getCode().equals(KeyCode.DELETE)) {
//				System.out.println("no borra");
//				key.consume();
//			}
//		}

	}

	@FXML
	public void validarTeclasCaracteres(KeyEvent key) {
//
//		// Valida que solo se puedan ingresar letras dentro de parentesis
//		if (!estaDentroDeParentesis()) {
//			key.consume();
//		}
//
//		// Valida que no se puedan ingresar caracteres distintos a letras mayúsculas
//		if (Character.isLowerCase(key.getCharacter().charAt(0)) || !Character.isLetter(key.getCharacter().charAt(0)))
//
//		{
//			key.consume();
//		}
//
//		System.out.println(key.getCharacter());
//
	}

	public boolean isOperador(char caracter) {
		if (caracter == '(' || caracter == ')' || caracter == 'v' || caracter == '^' || caracter == '>'
				|| caracter == '#') {
			return true;

		} else {
			return false;
		}
	}

	public boolean estaDentroDeParentesis() {
		actualizarCaret();
		char charAnterior = ' ';
		char charPosterior = ' ';
		try {
			charAnterior = jTextArea.getText().charAt(ultimoCaret - 1);
			charPosterior = jTextArea.getText().charAt(ultimoCaret);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Valida que solo se puedan ingresar los caracteres dentro de parentesis
		if (!(charAnterior == '(' && charPosterior == ')')) {
			return false;
		}
		return true;
	}

	public void deseleccionar() {
		jTextArea.deselect();
	}

}
