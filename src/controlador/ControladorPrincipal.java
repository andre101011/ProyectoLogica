package controlador;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import modelo.ConjuntoExpresiones;
import modelo.Expresion;
import modelo.ParseError;
import modelo.Utilidades;
import vista.TreeDisplay;

/*
 * Authors: Andres Llinas & Daniel Bonilla
 */

public class ControladorPrincipal {

	@FXML // fx:id="listaExpresiones"
	private ListView<String> listaExpresiones; // Value injected by FXMLLoader

	private int ultimoCaret;

	@FXML // fx:id="btnBorrarLinea"
	private Button btnBorrarLinea; // Value injected by FXMLLoader

	@FXML // fx:id="btnGraficar"
	private Button btnGraficar; // Value injected by FXMLLoader

	@FXML // fx:id="btnBorrarTodo"
	private Button btnBorrarTodo; // Value injected by FXMLLoader

	@FXML // fx:id="conjuncion"
	private Button conjuncion; // Value injected by FXMLLoader

	@FXML // fx:id="btnIngresar"
	private Button btnIngresar; // Value injected by FXMLLoader

	@FXML // fx:id="btnBorrarCampo"
	private Button btnBorrarCampo; // Value injected by FXMLLoader

	@FXML // fx:id="campoTexto"
	private TextField campoTexto; // Value injected by FXMLLoader

	@FXML
	public void initialize() {
		campoTexto.setText("");
	}

	@FXML
	public void ingresar() {
		String unaExpresionCadena = "(" + campoTexto.getText() + ")";
		ParseError posibleError = new ParseError(unaExpresionCadena); // Asegurate de que no estas parseando mal la

		// cadena
		if (!posibleError.isError()) {
			listaExpresiones.getItems().add(unaExpresionCadena);
			campoTexto.clear();
		}

	}

	@FXML
	public void graficar(ActionEvent ae) throws FileNotFoundException {

		String unaExpresionCadena = "(" + campoTexto.getText() + ")";
		ParseError posibleError = new ParseError(unaExpresionCadena); // Asegurate de que no estas parseando mal la

		// cadena
		if (!posibleError.isError()) {

			Expresion nuevaExpresion = new Expresion(unaExpresionCadena);
			TreeDisplay display = new TreeDisplay(nuevaExpresion.toString());
			display.setRoot(nuevaExpresion.getRoot());
		}
	}

	@FXML
	public void verificarValidez(ActionEvent ae) throws FileNotFoundException {
		System.out.println("entra");

		ArrayList<String> lista = new ArrayList<>();
		lista.addAll(listaExpresiones.getItems());
		ArrayList<String> expresionesCadena = new ArrayList<String>();
		expresionesCadena.addAll(lista);

		String conclusion = expresionesCadena.get(expresionesCadena.size() - 1);

		ConjuntoExpresiones conjuntoExpresiones = new ConjuntoExpresiones(expresionesCadena, conclusion);
		if (conjuntoExpresiones.verificarValidez()) {
			Utilidades.mostrarMensaje("Validez del argumento", conjuntoExpresiones.getConjuntoCadena()
					+ "\nes Tautologia, por lo tanto es consecuencia logica, y es valida ");
		} else {
			Utilidades.mostrarMensaje("Validez del argumento", conjuntoExpresiones.getConjuntoCadena()
					+ "\nNo es Tautologia, por lo tanto es no consecuencia logica, ni es valida ");
		}

	}

	public void agregarConjuncion(ActionEvent event) {
		if (estaDentroDeParentesis() || campoTexto.getText().isEmpty()) {
			campoTexto.insertText(ultimoCaret, ("()^()"));
		}
	}

	public void agregarDisyuncion() {
		if (estaDentroDeParentesis() || campoTexto.getText().isEmpty()) {
			campoTexto.insertText(ultimoCaret, ("()v()"));
		}
	}

	public void agregarCondicional() {
		if (estaDentroDeParentesis() || campoTexto.getText().isEmpty()) {
			campoTexto.insertText(ultimoCaret, ("()>()"));
		}
	}

	public void agregarBicondicional() {
		if (estaDentroDeParentesis() || campoTexto.getText().isEmpty()) {
			campoTexto.insertText(ultimoCaret, ("()#()"));
		}
	}

	public void agregarNegacion() {
		if (estaDentroDeParentesis() || campoTexto.getText().isEmpty()) {
			campoTexto.insertText(ultimoCaret, ("!()"));
		}
	}

	public void cerrar() {
		System.exit(0);
	}

	@FXML
	public void borrarCampo() {

		campoTexto.setText("");
		ultimoCaret = 0;
	}

	@FXML
	public void borrarLinea() {
		listaExpresiones.getItems().remove(listaExpresiones.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void borrarTodo() {
		listaExpresiones.getItems().clear();
	}

	public void borrar() {
		System.out.println("entra a borrar");
		String texto = campoTexto.getText();
		String cadena = "";
		int caretParaBorrar = ultimoCaret;
		int tamanio = campoTexto.getText().length();
		System.out.println(ultimoCaret);
		if (tamanio > 0) {
			char anterior = campoTexto.getText().charAt(ultimoCaret - 1);

			if (!Character.isLetter(anterior)) {
				return;
			}

			cadena += (texto.substring(0, caretParaBorrar - 1));
		}
		if (caretParaBorrar <= texto.length()) {
			cadena += texto.substring(caretParaBorrar, texto.length());
		}
		campoTexto.setText(cadena);

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
		return campoTexto;
	}

	@FXML
	public void actualizarCaret() {
		ultimoCaret = campoTexto.getCaretPosition();

	}

	@FXML
	// Valida las teclas no caracteres, como pueden ser Ctrl, ALT, Enter
	public void validarTeclasNoCaracteres(KeyEvent key) {

//		actualizarCaret();
//
//		if (key.getCode().equals(KeyCode.LEFT) || key.getCode().equals(KeyCode.RIGHT)) {
//			key.consume();
//		}
//		int tamanioTexto = campoTexto.getText().length();
//
//		// Valida que si hay un operador o parentesis antes del cursor, no se pueda
//		// borrar
//		if (tamanioTexto > 0 && ultimoCaret > 0) {
//			char charAnterior = campoTexto.getText().charAt(ultimoCaret - 1);
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
//			char charPosterior = campoTexto.getText().charAt(ultimoCaret);
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
//		// Valida que no se puedan ingresar caracteres distintos a letras mayÃºsculas
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
			charAnterior = campoTexto.getText().charAt(ultimoCaret - 1);
			charPosterior = campoTexto.getText().charAt(ultimoCaret);
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
		campoTexto.deselect();
	}

	@FXML
	public void mostrarManual() {
		try {
			Stage stage = new Stage();
			stage.setTitle("Manual");
			Parent root = FXMLLoader.load(getClass().getResource("/vista/Manual.fxml"));
			Scene scene = new Scene(root, 560, 480);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
