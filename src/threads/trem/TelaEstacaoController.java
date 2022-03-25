package threads.trem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class TelaEstacaoController implements Initializable {
	
	@FXML
	private Label qtdEmpacotadores;
	
	@FXML
	private ImageView tremEstacao;

	@FXML
	private ImageView empacotador1;
	
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
	public void realizarEntregaController(ActionEvent event) {
		boolean apertou = true;
		if (!apertou) {
			animaTremSaida(20);
		} else {
			animaTremChegada(20);
		}
		apertou = !apertou;
	}

	@FXML
	public void comecarTrabalhoEmpacotadorController(ActionEvent event) {
		Image estadoEmpacotador;
		
		estadoEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/empacotadortrabalhando.png"));
		empacotador1.setImage(estadoEmpacotador);
	}
	
	

	@FXML
	public void terminarTrabalhoEmpacotadorController(ActionEvent event) throws InterruptedException {
		Image estadoEmpacotador;
		estadoEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/empacotadorpronto.png"));
		empacotador1.setImage(estadoEmpacotador);
		
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(empacotador1);
		translate.setDuration(Duration.millis(450));

		translate.setFromX(52);
		translate.setToX(330);
		
		translate.play();
		Thread.sleep(550);
		
		
		translate.setFromX(330);
		translate.setToX(52);
		
		translate.play();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Main.empacotadores[0] = new Empacotador(1, "jorge", 2, this);
		
		Main.empacotadores[0].start();
	}
	
}
