package threads.trem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TelaEstacaoController implements Initializable {
	private Image imagemEmpacotador = new Image(getClass().getResourceAsStream("/threads/trem/assets/packer_work.png"));
	
	private ImageView[] empacotadores = new ImageView[10];

	@FXML
	private BorderPane mainPane;

	@FXML
	private MenuItem menuMusica;

	@FXML
	private TextField qtdPacotes;

	@FXML
	private TextField qtdMoedas;
	
	@FXML
	private ImageView tremEstacao;
	
	Stage stage;
	
	public void mudaTextoQtdPacotes() {
		String novoTexto = String.format("%d", Main.cargaDeposito);
		qtdPacotes.setText(novoTexto);
	}
	/* ----------- MÉTODOS DE CONFIGURÇÃO ----------- */

	@FXML
	private void sair(ActionEvent event) {
		stage = (Stage) mainPane.getScene().getWindow();
		Main.fecharJogo(stage, true);
	}

	@FXML
	private void musicaPausarIniciar(ActionEvent event) {
		if (Main.musica.tocando) {
			Main.musica.pararMusica();
			menuMusica.setText("Ligar música");
		} else {
			Main.musica.tocarMusica();
			menuMusica.setText("Desligar música");
		}
	}

	@FXML
	public void addEmpacotador() {
		if(Main.qtdEmpacotadores >= 10) {
			return;
		}
		
		AnchorPane root;
        try {
        	root = (AnchorPane)FXMLLoader.load(getClass().getResource("TelaContratarEmpacotador.fxml"));
        	
        	Scene scene = new Scene(root, 500, 426);
        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Stage telaAddEmpacotadorStage = new Stage();
            telaAddEmpacotadorStage.setTitle("Projeto: Estacao");
            telaAddEmpacotadorStage.setScene(scene);
            telaAddEmpacotadorStage.setResizable(false);
            

            telaAddEmpacotadorStage.setOnCloseRequest(closeEvent -> {
            	closeEvent.consume();
            	Main.contratoAceito = false;
				Main.fecharJogo(telaAddEmpacotadorStage, false);
			});
            
            telaAddEmpacotadorStage.showAndWait();
            
            if (Main.contratoAceito) {            	
        		empacotadores[Main.qtdEmpacotadores] = new ImageView(imagemEmpacotador);
        		
        		empacotadores[Main.qtdEmpacotadores].setFitWidth(30);
        		empacotadores[Main.qtdEmpacotadores].setFitHeight(52);
        		
        		empacotadores[Main.qtdEmpacotadores].setLayoutX(75 + (45 * (Main.qtdEmpacotadores % 5)));
        		empacotadores[Main.qtdEmpacotadores].setLayoutY(50 + ((int) Main.qtdEmpacotadores / 5) * 55);
        		
        		mainPane.getChildren().add(empacotadores[Main.qtdEmpacotadores]);
        		
        		Main.empacotadores[Main.qtdEmpacotadores] = new Empacotador(
        			Main.identificadorEmpacotador,
        			Main.nomeEmpacotador,
        			Main.tempoEmpacotamento,
        			this
        		);
        
        		Main.empacotadores[Main.qtdEmpacotadores].start();
        		Main.qtdEmpacotadores += 1;
            } else {
            	System.out.println(" ----------- CONTRATO Negado");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        alteraTextoMoedas();
	}

	@FXML
	public void realizarMelhorias() {
		AnchorPane root;
        try {
        	root = (AnchorPane)FXMLLoader.load(getClass().getResource("TelaMelhorias.fxml"));
        	
        	Scene scene = new Scene(root, 550, 478);
        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Stage telaMelhoriasStage = new Stage();
            telaMelhoriasStage.setTitle("Projeto: Estacao");
            telaMelhoriasStage.setScene(scene);
            telaMelhoriasStage.setResizable(false);

            telaMelhoriasStage.setOnCloseRequest(closeEvent -> {
            	closeEvent.consume();
            	Main.melhoria = TipoMelhoria.NENHUMA;
				Main.fecharJogo(telaMelhoriasStage, false);
			});
            
            telaMelhoriasStage.showAndWait();
            switch (Main.melhoria) {
	            case ESTACAO:
					int quantidadeAlterada = Main.cargaMaximaDepositoAlterada - Main.cargaMaximaDeposito;
	            	for (int i = 0; i < quantidadeAlterada; i++) {
	            		Main.empty.release();
	            	}
	            	Main.cargaMaximaDeposito += quantidadeAlterada;
	            	break;
	            case TREM:
	            	Main.tremDeCarga.nome = Main.nomeTrem;
	            	Main.tremDeCarga.tempoTransporte = Main.tempoViagemInicial;
	            	break;
				case EMPACOTADOR:
	        		Main.empacotadores[Main.idEmpacotadorAlterado].tempoEmpacotamento = Main.tempoEmpacotamento;
					break;
				case NENHUMA:
					break;
			}
            mostrarPropriedadesEstacao();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        alteraTextoMoedas();
	}
	
	public void alteraTextoMoedas() {
		qtdMoedas.setText(Integer.toString(Main.qtmoedas));
	}
	/* ----------- MÉTODOS DE ANIMAÇÃO: EMPACOTADOR ----------- */

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
	
	private void mostrarPropriedadesEstacao() {
		System.out.println("Carga Deposito: " + Integer.toString(Main.cargaMaximaDeposito));
		System.out.println("\nNome empacotador: " + Main.nomeEmpacotador);
		System.out.println("Tempo empacotamento: " + Integer.toString(Main.tempoEmpacotamento));
		System.out.println("\nNome trem: " + Main.nomeTrem);
		System.out.println("Carga Trem: " + Integer.toString(Main.cargaMaximaVagao));
		System.out.println("Tempo Trem: " + Integer.toString(Main.tempoViagemInicial) + "\n");
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		mostrarPropriedadesEstacao();
		
		qtdMoedas.setText(Integer.toString(Main.qtmoedas));

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
			Main.identificadorEmpacotador,
			Main.nomeEmpacotador,
			Main.tempoEmpacotamento,
			this
		);
		
		Main.tremDeCarga.start();
		Main.empacotadores[0].start();
		menuMusica.setText("Desligar música");
//		Main.musica.tocarMusica();
	}
	
}
