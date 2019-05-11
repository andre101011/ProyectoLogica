package application;

import java.io.IOException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ControladorPrincipal {

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

	private int contadorParentesis;

	/*
	 * Este metodo valida que el texto de entrada tenga un formato correcto para una
	 * expresion de logica proposicional
	 */
	public boolean validarTextoEntrada() {
		if (jtfTextoEntrada.getText().equals("textoValido")) {

			// Si la expresion es valida, entra acá
			System.out.println("entra");
			return true;
		} else {

			// Si la expresion no es valida, entra acá

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Mensaje del sistema");
			alert.setHeaderText(null);
			alert.setContentText("Expresion incorrecta");

			alert.showAndWait();
			return false;
		}
	}

	/*
	 * Metodo que valida si el usuario esta dando click dentro de un parentesis y
	 * actualiza el caret se ejecuta con cada click al textField
	 */
	public void validarClickDentroDeParentesis() {
		try {

			ultimoCaret = 0 + jtfTextoEntrada.getCaretPosition();

			char anterior = jtfTextoEntrada.getText().charAt(ultimoCaret - 1);
			char posterior = jtfTextoEntrada.getText().charAt(ultimoCaret);

			System.out.println("anterior " + anterior);
			System.out.println("posterior " + posterior);

			if (anterior != '(' || posterior != ')' || ultimoCaret == 0
					|| ultimoCaret == jtfTextoEntrada.getText().length() - 1) {
				jtfTextoEntrada.setEditable(false);
			} else {
				jtfTextoEntrada.setEditable(true);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void agregarConjuncion(ActionEvent event) {

		jtfTextoEntrada.insertText(ultimoCaret, ("()∧()"));
	}

	public void agregarDisyuncion() {
		jtfTextoEntrada.insertText(ultimoCaret, ("()∨()"));
	}

	public void agregarImplicacion() {

		jtfTextoEntrada.insertText(ultimoCaret, ("()→()"));
	}

	public void agregarBicondicional() {
		jtfTextoEntrada.insertText(ultimoCaret, ("()↔()"));
	}

	public void agregarNegacion() {

		jtfTextoEntrada.insertText(ultimoCaret, ("¬()"));
	}

	public void borrar() {

		jtfTextoEntrada.setText("");
		ultimoCaret=0;
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
	 * @return the btnConjuncion
	 */
	public Button getBtnConjuncion() {
		return btnConjuncion;
	}

	/**
	 * @return the btnDisyuncion
	 */
	public Button getBtnDisyuncion() {
		return btnDisyuncion;
	}

	/**
	 * @return the btnImplicacion
	 */
	public Button getBtnImplicacion() {
		return btnImplicacion;
	}

	/**
	 * @return the btnBicondicional
	 */
	public Button getBtnBicondicional() {
		return btnBicondicional;
	}

	/**
	 * @return the btnNegacion
	 */
	public Button getBtnNegacion() {
		return btnNegacion;
	}

	/**
	 * @return the jtfTextoEntrada
	 */
	public TextField getJtfTextoEntrada() {
		return jtfTextoEntrada;
	}

}
