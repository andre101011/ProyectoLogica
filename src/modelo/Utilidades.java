package modelo;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utilidades {

	public static void mostrarMensaje(String titulo, String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText(titulo);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
}
