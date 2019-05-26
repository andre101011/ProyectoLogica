package Mundo;

import java.io.FileNotFoundException;

import application.TreeDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControladorPrincipal {

	/*
	 * Validar que solo use pueda poner una letra letra dentro de cada() Validar
	 * flechas que se salen de los parentesis Poner botones de tautologia y
	 * contradiccion
	 */

	@FXML
	private Button btnConjuncion;

	@FXML
	private Button btnDisyuncion;

	@FXML
	private Button btnImplicacion;

	@FXML
	private Button btnBicondicional;

	@FXML
	private Button btnNegacion;

	@FXML
	private Button btnBorrar;

	@FXML
	private TextField jtfTextoEntrada;

	private int ultimoCaret;

	@FXML
	public void graficar(ActionEvent ae) throws FileNotFoundException {
		String unaExpresionCadena = jtfTextoEntrada.getText();

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

			TreeDisplay display = new TreeDisplay(nuevaExpresion.toString()); // Grafica sin normalizar.
			display.setRoot(nuevaExpresion.root);

		}
	}

//	public void agregarConjuncion(ActionEvent event) {
//
//		jtfTextoEntrada.insertText(ultimoCaret, ("()^()"));
//
//	}
//
//	public void agregarDisyuncion() {
//		jtfTextoEntrada.insertText(ultimoCaret, ("()∨()"));
//	}
//
//	public void agregarImplicacion() {
//
//		jtfTextoEntrada.insertText(ultimoCaret, ("()→()"));
//	}
//
//	public void agregarBicondicional() {
//		jtfTextoEntrada.insertText(ultimoCaret, ("()↔()"));
//	}
//
//	public void agregarNegacion() {
//
//		jtfTextoEntrada.insertText(ultimoCaret, ("¬()"));
//	}

	public void borrar() {

		jtfTextoEntrada.setText("");
		ultimoCaret = 0;
		jtfTextoEntrada.setEditable(false);
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
	public TextField getJtfTextoEntrada() {
		return jtfTextoEntrada;
	}
}
