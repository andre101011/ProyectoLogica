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
				"Para ingresar formas atomicas simplemente digite la letra correspondiente al �tomo que desee ingresar, este debe ser una letra May�scula entre la A y la Z.\r\n"
						+ "\r\n"
						+ "Para ingresar formas moleculares presione los botones de la interfaz correspondientes al operador deseado, estos se mostrar�n en el campo de texto con su equivalente en simbolos de la siguiente forma:\r\n"
						+ "Conjunci�n :	^\r\n" + "Disyunci�n:	v\r\n" + "Condicional:	>\r\n"
						+ "Bicondicional:	#\r\n" + "Negaci�n:	!	\r\n" + "\r\n" + "\r\n"
						+ "Para comprobar la validez de un argumento ingrese una expresion atomica o molecular  por cada linea de texto, o sea, separando las mismas con un salto de linea. La conclusi�n deber� ser ingresada en la ultima linea. Posteriormente d� click en el bot�n \"Verificar validez\", una ventana saldr� confirmando (o negando) la validez del argumento ingresado.");
	}
}
