package threads.trem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuInicialController implements Initializable {
	@FXML
	private AnchorPane telaMenuInicial;
	
	@FXML
	private Button botaoNovoJogo;
	
	@FXML
	private Button botaoCriativo;
	
	@FXML
	private Button botaoSair;

	Stage stage;
	
	@FXML
	private void iniciarJogo(ActionEvent event) {
		Main.modoJogo = ModoJogo.DESAFIO;
		
		Main.qtdEmpacotadores = 1;
		Main.cargaMaximaDeposito = 15;
		Main.cargaMaximaVagao = 5;
		Main.qtmoedas = 20;
		
		// Campos para inicalizar a thread
		// Empacotadores
		Main.tempoEmpacotamento = 10; // 10
		Main.nomeEmpacotador = "Robervaldo";
		Main.identificadorEmpacotador = "Empacotador Padrao";
		
		// Trem
		Main.tempoViagemInicial = 55; // 45
		Main.nomeTrem = "Estacao Inicial";
		Main.empty = new Semaphore(15);
		
		BorderPane root;
        try {
        	root = (BorderPane)FXMLLoader.load(getClass().getResource("TelaEstacao.fxml"));
        	
        	Scene scene = new Scene(root, 1024, 512);
        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Stage telaEstacaoStage = new Stage();
            telaEstacaoStage.setTitle("Projeto: Estacao");
            telaEstacaoStage.setScene(scene);
            telaEstacaoStage.setResizable(false);

            telaEstacaoStage.setOnCloseRequest(closeEvent -> {
            	closeEvent.consume();
				Main.fecharJogo(telaEstacaoStage, true);
			});
            
            telaEstacaoStage.show();
            sair(event);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void iniciarJogoCriativo(ActionEvent event) {
		Main.modoJogo = ModoJogo.CRIATIVO;
		Main.qtmoedas = 800;
		
		AnchorPane root;
        try {
        	root = (AnchorPane)FXMLLoader.load(getClass().getResource("MenuEstacao.fxml"));
        	
        	Scene scene = new Scene(root, 500, 420);
        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Stage menuEstacaoStage = new Stage();
            menuEstacaoStage.setTitle("Projeto: Estacao");
            menuEstacaoStage.setScene(scene);
            menuEstacaoStage.setResizable(false);
            
            menuEstacaoStage.setOnCloseRequest(closeEvent -> {
            	closeEvent.consume();
				Main.fecharJogo(menuEstacaoStage, false);
			});
            
            menuEstacaoStage.show();
            sair(event);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void sair(ActionEvent event) {
		stage = (Stage) telaMenuInicial.getScene().getWindow();
		
		Main.fecharJogo(stage, false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
}