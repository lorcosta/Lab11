
package it.polito.tdp.rivers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.FlowInformation;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	Model model= new Model();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSimulate(ActionEvent event) {
    	this.txtResult.clear();
    	String kString=this.txtK.getText();
    	Double k;
    	try {
    		k=Double.parseDouble(kString);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Il fattore k inserito non corrisponde ad un numero positivo!");
    		throw new NumberFormatException("Errore nella parsificazione del numero k");
    	}
    	if(k<0) {
    		this.txtResult.setText("Il valore inserito di k deve essere maggiore di zero");
    	}
    	River chosen=this.boxRiver.getValue();
       	if(chosen==null) {
       		this.txtResult.appendText("ERRORE NELLA SCELTA DEL FIUME!");
       		return;
       	}
    	model.simula(chosen,k);
    	this.txtResult.appendText("I giorni in cui non si è potuto garantire il servizio sono: "+model.getGiorniInsufficienti()+"\n\n");
    	this.txtResult.appendText("L'occupazione media del bacino è di "+model.getOccupazioneMedia()+" metri cubi al giorno\n");
    }
    
   @FXML
   void showInformation(ActionEvent event) {
	   this.txtResult.clear();
   	this.txtStartDate.clear();
   	this.txtEndDate.clear();
   	this.txtNumMeasurements.clear();
   	this.txtFMed.clear();
   	River chosen=this.boxRiver.getValue();
   	if(chosen==null) {
   		this.txtResult.appendText("ERRORE NELLA SCELTA DEL FIUME!");
   		return;
   	}
   	FlowInformation f=model.getFlowInformation(chosen);
   	model.fillFlowOfRiver(chosen);
   	this.txtStartDate.setText(""+f.getFirstFlowDay());
   	this.txtEndDate.setText(""+f.getLastFlowDay());
   	this.txtNumMeasurements.setText(""+f.getTotMisurazioni());
   	this.txtFMed.setText(""+f.getFlowAvg());
   }
    void loadData() {
    	List<River> fiumi=model.getAllRivers();
    	boxRiver.getItems().addAll(fiumi);
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    public void setModel(Model model) {
    	this.model = model;
    	loadData();
    }
}
