package threads.trem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class TelaEstacaoController implements Initializable {
	int quantidadeEmpacotadores = 0;
	boolean apertou = false;
	
	@FXML
	private Label qtdEmpacotadores;
	
	@FXML
	private ImageView tremEstacao;
	
	private void animaTremSaida(int duracaoViagem) {
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(tremEstacao);
		translate.setFromX(10);

		translate.setDuration(Duration.millis(duracaoViagem * 100));
		
		translate.setByX(900);
		translate.play();
	}

	private void animaTremChegada(int duracaoViagem) {
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(tremEstacao);
		translate.setFromX(-345);
		translate.setDuration(Duration.millis(duracaoViagem * 100));
		
		translate.setByX(355);
		translate.play();
	}
	
	@FXML
	private void addEmpacotador(ActionEvent event) {
		quantidadeEmpacotadores ++;
		qtdEmpacotadores.setText(Integer.toString(quantidadeEmpacotadores));
		if (!apertou) {
			animaTremSaida(20);
		} else {
			animaTremChegada(20);
		}
		apertou = !apertou;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
}
