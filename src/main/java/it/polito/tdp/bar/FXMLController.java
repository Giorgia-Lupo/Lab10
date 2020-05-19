package it.polito.tdp.bar;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.bar.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class FXMLController {

	Model model;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSimula;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSimula(ActionEvent event) {
    	model.risolvi();
    	
    	txtResult.appendText("Numero di clienti: "+model.getClienti()+"\n");
    	txtResult.appendText("Numero di clienti soddisfatti: "+model.getSoddisfatti()+"\n");
    	txtResult.appendText("Numero di clienti insoddisfatti: "+model.getInsoddisfatti()+"\n");
    	
    }

    @FXML
    void initialize() {
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    public void setModel(Model m) {
    	this.model=m;
    }
}
