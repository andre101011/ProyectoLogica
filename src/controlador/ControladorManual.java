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
		campoTexto.setText("*Para ingresar formas atómicas simplemente digite la letra correspondiente al átomo que desee ingresar, este debe ser una letra Mayúscula entre la A y la Z.\r\n" + 
				"\r\n" + 
				"*Equivalencias de operadores:\r\n" + 
				"	Conjunción :	^\r\n" + 
				"	Disyunción:	v\r\n" + 
				"	Condicional:	>\r\n" + 
				"	Bicondicional:	#\r\n" + 
				"	Negación:	!	\r\n" + 
				"\r\n" + 
				"*Para comprobar la validez de un argumento:\r\n" + 
				"	-Ingrese una expresion atomica o molecular  en el campo de texto.\r\n" + 
				"	-Click el botón ingresar.\r\n" + 
				"	-Repita esto para todas las expresiones que desee ingresar (Recuerde que la conclusion debe ser la ultima).\r\n" + 
				"	-Posteriormente dé click en el botón \"Verificar validez\", una ventana saldrá confirmando (o negando) la validez del argumento ingresado.\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"*Para graficar el arbol binario de una expresion:\r\n" + 
				"	-Ingrese una expresion atomica o molecular  en el campo de texto\r\n" + 
				"	-Click el botón graficar\r\n" + 
				"\r\n" + 
				"*Para borrar las expresiones ya creadas de la lista: \r\n" + 
				"	-Click en el botón borrar todo\r\n" + 
				"\r\n" + 
				"*Para borrar el contenido del campo de texto:\r\n" + 
				"	-Click en el botón borrar ubicado a la izquierda del campo de texto\r\n" + 
				"\r\n" + 
				"*Para borrar unicamente una expresion de la lista:\r\n" + 
				"	-Click en el botón borrar fila");	}
}
