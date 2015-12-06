package br.com.vortice.avon.pallete.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShowAlertUtil {
	
	public static void exibirMensagemErro(String mensagem){
		Alert alert = new Alert(AlertType.ERROR);
    	alert.setContentText(mensagem);
    	alert.showAndWait();
	}
	
	public static void exibirMensagemInfo(String mensagem){
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setContentText(mensagem);
    	alert.showAndWait();
	}

}
