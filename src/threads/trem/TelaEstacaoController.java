package threads.trem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class TelaEstacaoController implements Initializable {
	private boolean apertou = false;
	private Image imagemEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/empacotadortrabalhando.png"));
	
	private ImageView[] empacotadores = new ImageView[10];

	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private Label qtdEmpacotadores;
	
	@FXML
	private ImageView tremEstacao;
	

	@FXML
	private AnchorPane progressoPane;
	
	/* ----------- MÉTODOS DE ANUIMAÇÃO: EMPACOTADOR ----------- */

	@FXML
	public void addEmpacotador() {
		empacotadores[Main.qtdEmpacotadores] = new ImageView(imagemEmpacotador);
		
		empacotadores[Main.qtdEmpacotadores].setLayoutX(52 + (46 * (Main.qtdEmpacotadores % 5)));
		empacotadores[Main.qtdEmpacotadores].setLayoutY(113 + ((int) Main.qtdEmpacotadores / 5) * 20);
		
		mainPane.getChildren().add(empacotadores[Main.qtdEmpacotadores]);
		
		String nome = String.format("Empacotador %d", Main.qtdEmpacotadores+1);
		Main.empacotadores[Main.qtdEmpacotadores] = new Empacotador(
			Main.qtdEmpacotadores+1,
			nome,
			Main.tempoEmpacotamentoInicial, this
		);
		
		Main.empacotadores[Main.qtdEmpacotadores].start();
		Main.qtdEmpacotadores += 1;
	}
	
	public void comecarTrabalhoEmpacotador(int id) {
		Image estadoEmpacotador;
		estadoEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/empacotadortrabalhando.png"));
		empacotadores[id].setImage(estadoEmpacotador);
	}
	
	public void ficarProntoEmpacotador(int id) {
		Image estadoEmpacotador;
		
		estadoEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/empacotadorpronto.png"));
		empacotadores[id].setImage(estadoEmpacotador);
	}

	public void entregarPacoteEmpacotador(int id) throws InterruptedException {
		double posicaoInicial = empacotadores[id].getX();
		
		TranslateTransition translate = new TranslateTransition();
		
		translate.setNode(empacotadores[id]);
		translate.setDuration(Duration.millis(450));

		translate.setFromX(posicaoInicial);
		translate.setToX(330 - 46 * (id % 5));
		
		translate.play();
	}

	public void voltarAoTrabalhoEmpacotador(int id) throws InterruptedException {
		Image estadoEmpacotador;
		estadoEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/empacotadortrabalhando.png"));
		double posicaoInicial = empacotadores[id].getX();

		empacotadores[id].setImage(estadoEmpacotador);
		TranslateTransition translate = new TranslateTransition();
		
		translate.setNode(empacotadores[id]);
		translate.setDuration(Duration.millis(450));

		translate.setFromX(330 - 46 * (id % 5));
		translate.setToX(posicaoInicial);
		
		translate.play();
	}

	/* ----------- MÉTODOS DE ANUIMAÇÃO: TREM ----------- */

	@FXML
	public void realizarEntrega() {
		if (apertou) {
			animaTremChegada(20);
			
		} else {
			animaTremSaida(20);			
		}
		apertou = !apertou;
	}
	
	private void animaTremSaida(int duracaoViagem) {
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(tremEstacao);
		translate.setFromX(10);

		translate.setDuration(Duration.millis(duracaoViagem * 100));
		
		translate.setToX(910);
		translate.play();
	}

	private void animaTremChegada(int duracaoViagem) {
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(tremEstacao);
		translate.setFromX(-345);
		translate.setDuration(Duration.millis(duracaoViagem * 100));
		
		translate.setToX(10);
		translate.play();
	}
	
	
	@FXML
	private void tremEntregarPacotes() throws InterruptedException {
		realizarEntrega();
//		progressoPane.setVisible(true);
		if (!apertou) return;
		int i;
		Main.depositoCheio.acquire();
		Main.mutex.acquire();
		
		for(i = 0; i < Main.cargaMaxima; i++) {
			Main.empty.release();
			Main.cargaDeposito -= 1;
		}
		Main.mutex.release();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		int i;
		
		Main.tremDeCarga.start();
		
		progressoPane.setVisible(false);
		for (i = 0; i < Main.qtdEmpacotadores; i++) {
			empacotadores[i] = new ImageView(imagemEmpacotador);
			
			empacotadores[i].setLayoutX(52 + (46 * (i % 5)));
			empacotadores[i].setLayoutY(113 + ((int) i / 5) * 20);
				
			mainPane.getChildren().add(empacotadores[i]);
			
			String nome = String.format("Empacotador %d", i+1);
			Main.empacotadores[i] = new Empacotador(i+1, nome, Main.tempoEmpacotamentoInicial, this);
			
			Main.empacotadores[i].start();
		}
	}
	
}
