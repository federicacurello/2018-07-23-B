/**
 * Sample Skeleton for 'NewUfoSightings.fxml' Controller Class
 */

package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewUfoSightingsController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtxG"
	private TextField txtxG; // Value injected by FXMLLoader

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="txtT1"
	private TextField txtT1; // Value injected by FXMLLoader

	@FXML // fx:id="txtT2"
	private TextField txtT2; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimula"
	private Button btnSimula; // Value injected by FXMLLoader
	private int anno;
	private int gg;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		txtResult.clear();
		try {
			if(txtAnno.getText()==null || txtxG.getText()==null) {
				txtResult.setText("Inserire entrambi i valori");
				return;
			}
			anno= Integer.parseInt(txtAnno.getText());
			if(anno<1906 || anno>2014) {
				txtResult.setText("Inserire un anno tra il 1906 e il 2014");
				return;
			}
			gg= Integer.parseInt(txtxG.getText());
			if(gg<1 || gg>180) {
				txtResult.setText("Inserire un numero di giorni compreso tra 1 e 180");
				return;
			}
			
			txtResult.appendText(model.creaGrafo(anno, gg));
			
		}catch(NumberFormatException e) {
			txtResult.setText("Errore di formattazione, inserire i valori di anno e xG");
			return;
		}

	}

	@FXML
	void doSimula(ActionEvent event) {
		txtResult.clear();
		try {
			if(txtT1.getText()==null || txtT2.getText()==null) {
				txtResult.setText("Inserire entrambi i valori");
				return;
			}
			int giorno1= Integer.parseInt(txtT1.getText());
			if(giorno1>365) {
				txtResult.setText("Inserire un numero di giorni minore di 365");
				return;
			}
			int giorno2= Integer.parseInt(txtT2.getText());
			if(giorno2>365) {
				txtResult.setText("Inserire un numero di giorni minore di 365");
				return;
			}
			
			txtResult.appendText(model.simula(Integer.parseInt(txtAnno.getText()), Integer.parseInt(txtxG.getText()), giorno1, giorno2));
			
		}catch(NumberFormatException e) {
			txtResult.setText("Errore di formattazione, inserire i valori di T1 e T2");
			return;
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtxG != null : "fx:id=\"txtxG\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT2 != null : "fx:id=\"txtT2\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;

	}
}
