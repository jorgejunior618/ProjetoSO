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

		// Campos para inicalizar a thread
		// Empacotadores
		Main.tempoEmpacotamentoInicial = 10;
		Main.nomePrimeiroEmpacotador = "Robervaldo";
		Main.identificadorPrimeiroEmpacotador = "Empacotador Padr�o";
		
		// Trem
		Main.tempoViagemInicial = 45;
		Main.nomeTrem = "Esta��o Inicial";
		Main.empty = new Semaphore(15);
		
		BorderPane root;
        try {
        	root = (BorderPane)FXMLLoader.load(getClass().getResource("TelaEstacao.fxml"));
        	
        	Scene scene = new Scene(root, 1024, 512);
        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Stage telaEstacaoStage = new Stage();
            telaEstacaoStage.setTitle("Projeto: Esta��o");
            telaEstacaoStage.setScene(scene);
            telaEstacaoStage.setResizable(false);

            telaEstacaoStage.setOnCloseRequest(closeEvent -> {
            	closeEvent.consume();
				Main.fecharJogo(telaEstacaoStage);
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
		
		AnchorPane root;
        try {
        	root = (AnchorPane)FXMLLoader.load(getClass().getResource("MenuEstacao.fxml"));
        	
        	Scene scene = new Scene(root, 500, 420);
        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Stage menuEstacaoStage = new Stage();
            menuEstacaoStage.setTitle("Projeto: Esta��o");
            menuEstacaoStage.setScene(scene);
            menuEstacaoStage.setResizable(false);
            
            menuEstacaoStage.setOnCloseRequest(closeEvent -> {
            	closeEvent.consume();
				Main.fecharJogo(menuEstacaoStage);
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
		
		Main.fecharJogo(stage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
}