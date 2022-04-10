package threads.trem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class TelaEstacaoController implements Initializable {
	private Image imagemEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/packer_work.png"));
	
	private ImageView[] empacotadores = new ImageView[10];

	@FXML
	private BorderPane mainPane;

	@FXML
	private TextField qtdPacotes;

	@FXML
	private TextField qtdMoedas;
	
	@FXML
	private ImageView tremEstacao;
	
	public void mudaTextoQtdPacotes() {
		String novoTexto = String.format("%d", Main.cargaDeposito);
		qtdPacotes.setText(novoTexto);
	}
	
	/* ----------- MÉTODOS DE ANIMAÇÃO: EMPACOTADOR ----------- */

	@FXML
	public void addEmpacotador() {
		if(Main.qtdEmpacotadores >= 10) {
			return;
		}
		
		empacotadores[Main.qtdEmpacotadores] = new ImageView(imagemEmpacotador);
		
		empacotadores[Main.qtdEmpacotadores].setFitWidth(30);
		empacotadores[Main.qtdEmpacotadores].setFitHeight(52);
		
		empacotadores[Main.qtdEmpacotadores].setLayoutX(75 + (45 * (Main.qtdEmpacotadores % 5)));
		empacotadores[Main.qtdEmpacotadores].setLayoutY(50 + ((int) Main.qtdEmpacotadores / 5) * 55);
		
		mainPane.getChildren().add(empacotadores[Main.qtdEmpacotadores]);
		
		String nome = String.format("Empacotador %d", Main.qtdEmpacotadores+1);
		Main.empacotadores[Main.qtdEmpacotadores] = new Empacotador(
			Main.identificadorPrimeiroEmpacotador,
			nome,
			Main.tempoEmpacotamentoInicial,
			this
		);

		Main.empacotadores[Main.qtdEmpacotadores].start();
		Main.qtdEmpacotadores += 1;
	}
	
	public void comecarTrabalhoEmpacotador(int id) {
		Image estadoEmpacotador;
		estadoEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/packer_work.png"));
		empacotadores[id].setImage(estadoEmpacotador);
	}
	
	public void ficarProntoEmpacotador(int id) {
		Image estadoEmpacotador;
		estadoEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/packer_ready.png"));
		empacotadores[id].setImage(estadoEmpacotador);
	}

	public void entregarPacoteEmpacotador(int id) throws InterruptedException {
		double posicaoInicialX = empacotadores[id].getX();
		double posicaoInicialY = empacotadores[id].getY();
		
		TranslateTransition translate = new TranslateTransition();
		
		translate.setNode(empacotadores[id]);
		translate.setDuration(Duration.millis(450));

		translate.setFromX(posicaoInicialX);
		translate.setFromY(posicaoInicialY);
		
		translate.setToX(130 - 90 - (45 * (id % 5)));
		translate.setToY(215 - 45 - ((int) id / 5) * 55);
		
		translate.play();
	}

	public void voltarAoTrabalhoEmpacotador(int id) throws InterruptedException {
		Image estadoEmpacotador;
		estadoEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/packer_ready.png"));
		double posicaoInicialX = empacotadores[id].getX();
		double posicaoInicialY = empacotadores[id].getY();
		
		empacotadores[id].setImage(estadoEmpacotador);
		TranslateTransition translate = new TranslateTransition();
		
		translate.setNode(empacotadores[id]);
		translate.setDuration(Duration.millis(450));

		translate.setFromX(130 - 90 - (45 * (id % 5)));
		translate.setFromY(215 - 45 - ((int) id / 5) * 55);
		
		translate.setToX(posicaoInicialX);
		translate.setToY(posicaoInicialY);
		
		translate.play();
	}

	/* ----------- MÉTODOS DE ANIMAÇÃO: TREM ----------- */


	public void sairParaEntregaTrem() {
		TranslateTransition translate = new TranslateTransition();
		
		translate.setNode(tremEstacao);
		translate.setFromX(0);

		translate.setDuration(Duration.millis(2000));
		
		translate.setToX(1024);
		translate.play();
	}

	public void chegaNaEstacaoTrem() {
		
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(tremEstacao);
//		translate.setFromX(-345);
		translate.setFromX(1024);
		translate.setDuration(Duration.millis(1500));
		
		translate.setToX(0);
		translate.play();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Main.tremDeCarga = new Trem(
			1,
			Main.tempoViagemInicial,
			Main.nomeTrem,
			this
		);

		empacotadores[0] = new ImageView(imagemEmpacotador);

		empacotadores[0].setLayoutX(75);
		empacotadores[0].setLayoutY(50);
		empacotadores[0].setFitWidth(30);
		empacotadores[0].setFitHeight(52);
			
		mainPane.getChildren().add(empacotadores[0]);
		
		Main.empacotadores[0] = new Empacotador(
			Main.identificadorPrimeiroEmpacotador,
			Main.nomePrimeiroEmpacotador,
			Main.tempoEmpacotamentoInicial,
			this
		);
		
		Main.tremDeCarga.start();
		Main.empacotadores[0].start();
	}
	
}
