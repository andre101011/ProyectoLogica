package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JTextArea;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControladorManual {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea campoTexto;

	@FXML
	void initialize() {
		campoTexto.setText(
				"Para ingresar formas atomicas simplemente digite la letra correspondiente al átomo que desee ingresar, este debe ser una letra Mayúscula entre la A y la Z.\r\n"
						+ "\r\n"
						+ "Para ingresar formas moleculares presione los botones de la interfaz correspondientes al operador deseado, estos se mostrarán en el campo de texto con su equivalente en simbolos de la siguiente forma:\r\n"
						+ "Conjunción :	^\r\n" + "Disyunción:	v\r\n" + "Condicional:	>\r\n"
						+ "Bicondicional:	#\r\n" + "Negación:	!	\r\n" + "\r\n" + "\r\n"
						+ "Para comprobar la validez de un argumento ingrese una expresion atomica o molecular  por cada linea de texto, o sea, separando las mismas con un salto de linea. La conclusión deberá ser ingresada en la ultima linea. Posteriormente dé click en el botón \"Verificar validez\", una ventana saldrá confirmando (o negando) la validez del argumento ingresado.");
	}
}
