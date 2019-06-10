package Mundo;

import java.io.FileNotFoundException;

import application.TreeDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/*
 * Authors: Andres Llinas & Daniel Bonilla
 */

public class ControladorPrincipal {

	@FXML
	private TextArea jTextArea;

	private int ultimoCaret;

	@FXML
	public void initialize() {
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
			Expresion nuevaExpresion = new Expresion(unaExpresionCadena);
			boolean resultado = nuevaExpresion.evaluar();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Resultado");
			alert.setHeaderText("Resultado de la evaluacion booleana de la expresion");
			alert.setContentText(unaExpresionCadena + " = " + resultado);
			alert.showAndWait();

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
			jTextArea.insertText(ultimoCaret, ("¬()"));
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
		actualizarCaret();

		if (key.getCode().equals(KeyCode.LEFT) || key.getCode().equals(KeyCode.RIGHT)) {
			key.consume();
		}
		int tamanioTexto = jTextArea.getText().length();

		// Valida que si hay un operador o parentesis antes del cursor, no se pueda
		// borrar
		if (tamanioTexto > 0 && ultimoCaret > 0) {
			char charAnterior = jTextArea.getText().charAt(ultimoCaret - 1);

			if (isOperador(charAnterior) && key.getCode().equals(KeyCode.BACK_SPACE)) {
				key.consume();
			}
		}

		// Valida que si hay un operador inmediatamente despues del cursor, este no
		// se pueda eliminar con la tecla suprimir
		if (ultimoCaret < tamanioTexto) {
			char charPosterior = jTextArea.getText().charAt(ultimoCaret);

			if (isOperador(charPosterior) && key.getCode().equals(KeyCode.DELETE)) {
				System.out.println("no borra");
				key.consume();
			}
		}

	}

	@FXML
	public void validarTeclasCaracteres(KeyEvent key) {

		// Valida que solo se puedan ingresar letras dentro de parentesis
		if (!estaDentroDeParentesis()) {
			if (key.getCharacter().charAt(0) == '!') {
				System.out.println("EXCLAMACION!");
			} else {
				key.consume();
			}
		}

		// Valida que no se puedan ingresar caracteres distintos a letras mayúsculas
		if (Character.isLowerCase(key.getCharacter().charAt(0)) || !Character.isLetter(key.getCharacter().charAt(0))) {
			key.consume();
		}

		System.out.println(key.getCharacter());

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

}
