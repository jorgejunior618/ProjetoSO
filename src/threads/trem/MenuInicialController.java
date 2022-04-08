package threads.trem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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
		Main.identificadorPrimeiroEmpacotador = "Empacotador Padrão";
		
		// Trem
		Main.tempoViagemInicial = 45;
		Main.nomeTrem = "Estação Inicial";
		Main.empty = new Semaphore(15);
		
		BorderPane root;
        try {
        	root = (BorderPane)FXMLLoader.load(getClass().getResource("TelaEstacao.fxml"));
        	
        	Scene scene = new Scene(root, 1024, 512);
        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Projeto: Estação");
            stage.setScene(scene);
            stage.setResizable(false);

            stage.setOnCloseRequest(closeEvent -> {
            	closeEvent.consume();
				Main.fecharJogo(stage);
			});
            
            stage.show();
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

            Stage stage = new Stage();
            stage.setTitle("Projeto: Estação");
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.setOnCloseRequest(closeEvent -> {
            	closeEvent.consume();
				Main.fecharJogo(stage);
			});
            
            stage.show();
            sair(event);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void sair(ActionEvent event) {
		stage = (Stage) telaMenuInicial.getScene().getWindow();
		
		boolean empacotadoresAtivos = true;
		boolean tremAtivo = true;
		Main.encerrarThreads = true;
		
		System.out.println("Encerrando threads");
		while (empacotadoresAtivos || tremAtivo) {
			if(tremAtivo) {
				tremAtivo = (Main.tremDeCarga != null && Main.tremDeCarga.isAlive());
			}
			if(empacotadoresAtivos) {
				for (int i = 0; i < 10; i++) {
					empacotadoresAtivos = (Main.empacotadores[i] != null && Main.empacotadores[i].isAlive());
					if (empacotadoresAtivos) break;
				}
			}
		}
		System.out.println("Threads encerradas");
		
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
}