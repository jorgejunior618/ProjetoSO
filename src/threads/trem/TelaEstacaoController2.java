package threads.trem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TelaEstacaoController implements Initializable {
	private Image imagemEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/empacotadortrabalhando.png"));
	
	private ImageView[] empacotadores = new ImageView[10];

	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private TextField qtdPacotes;
	
	@FXML
	private ImageView tremEstacao;

	@FXML
	private AnchorPane progressoPane;
	
	@FXML
	private ProgressBar progressoCaminhoTrem;
	
	public void mudaTextoQtdPacotes() {
		String novoTexto = String.format("%d", Main.cargaDeposito);
		qtdPacotes.setText(novoTexto);
	}
	
	/* ----------- MÉTODOS DE ANUIMAÇÃO: EMPACOTADOR ----------- */

	@FXML
	public void addEmpacotador() {
		if(Main.qtmoedas>=1){
			if(Main.qtdEmpacotadores >= 10) {
				return;
			}
			empacotadores[Main.qtdEmpacotadores] = new ImageView(imagemEmpacotador);
		
			empacotadores[Main.qtdEmpacotadores].setLayoutX(52 + (46 * (Main.qtdEmpacotadores % 5)));
			empacotadores[Main.qtdEmpacotadores].setLayoutY(113 + ((int) Main.qtdEmpacotadores / 5) * 20);
		
			mainPane.getChildren().add(empacotadores[Main.qtdEmpacotadores]);
		
			String nome = String.format("Empacotador %d", Main.qtdEmpacotadores+1);
			Main.empacotadores[Main.qtdEmpacotadores] = new Empacotador(
				Main.qtdEmpacotadores,
				nome,
				Main.tempoEmpacotamentoInicial, this
				);
		
			Main.empacotadores[Main.qtdEmpacotadores].start();
			Main.qtmoedas-=1;
			Main.qtdEmpacotadores += 1;
		}
		else {
			System.out.println("Nao existem moedas o suficiente para contratar outro empacotador.");
			return;
		}
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

	/* ----------- MÉTODOS DE ANIMAÇÃO: TREM ----------- */


	public void sairParaEntregaTrem() {
//		progressoPane.setVisible(true);
		TranslateTransition translate = new TranslateTransition();
		
		translate.setNode(tremEstacao);
		translate.setFromX(10);

		translate.setDuration(Duration.millis(2000));
		
		translate.setToX(910);
		translate.play();
	}

	public void chegaNaEstacaoTrem() {
		
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(tremEstacao);
		translate.setFromX(-345);
		translate.setDuration(Duration.millis(1500));
		
		translate.setToX(10);
		translate.play();
	}
	
	public void atualizarProgressoTrem(double progresso) {
		progressoCaminhoTrem.setProgress(progresso);
//		if (progresso > 0.98) {
//			progressoPane.setVisible(false);
//		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		int i;

		Main.tremDeCarga = new Trem(
			1,
			Main.cargaMaximaDeposito,
			this
		);
		Main.tremDeCarga.start();
		
//		progressoPane.setVisible(false);
//		progressoCaminhoTrem.setProgress(0.5);
		progressoCaminhoTrem.getStyleClass().add("blue-bar");
		for (i = 0; i < Main.qtdEmpacotadores; i++) {
			empacotadores[i] = new ImageView(imagemEmpacotador);
			
			empacotadores[i].setLayoutX(52 + (46 * (i % 5)));
			empacotadores[i].setLayoutY(113 + ((int) i / 5) * 20);
				
			mainPane.getChildren().add(empacotadores[i]);
			
			String nome = String.format("Empacotador %d", i+1);
			Main.empacotadores[i] = new Empacotador(i, nome, Main.tempoEmpacotamentoInicial, this);
			
			Main.empacotadores[i].start();
		}
	}
	
}
