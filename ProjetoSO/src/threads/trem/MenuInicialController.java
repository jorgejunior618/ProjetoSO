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
	
	@FXML
	private void iniciarJogo(ActionEvent event) {
		BorderPane root;
		
		Main.cargaMaximaDeposito = 15;
		Main.empty = new Semaphore(15);

		Main.cargaMaximaVagao = 10;
		Main.qtdEmpacotadores = 1;
		Main.tempoEmpacotamentoInicial = 3;
		Main.tempoViagemInicial = 15;
		
        try {
        	root = (BorderPane)FXMLLoader.load(getClass().getResource("TelaEstacao.fxml"));
        	
        	Scene scene = new Scene(root, 1024, 512);
        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Projeto: Estação");
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.show();
            
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void iniciarJogoCriativo(ActionEvent event) {
		AnchorPane root;
        try {
        	root = (AnchorPane)FXMLLoader.load(getClass().getResource("MenuEstacao.fxml"));
        	
        	Scene scene = new Scene(root, 500, 420);
        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Projeto: Estação");
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.show();
            
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	Stage stage;
	
	@FXML
	private void sair(ActionEvent event) {
		stage = (Stage) telaMenuInicial.getScene().getWindow();
		stage.close();
	}
	
//	@FXML
//	private void iniciarJogo(ActionEvent event) {
//		if(modoCriativo.isSelected()) {
//			if (tabCriacao.isSelected()) {
//				criarJogo(event);
//			}
//			Main.modoJogo = ModoJogo.CRIATIVO;
//			tabCriacao.setDisable(false);
//			
//			SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
//			selectionModel.select(tabCriacao);
//			
//			tabSelecao.setDisable(true);
//		}
//		if(modoDesafio.isSelected()) {
//			Main.modoJogo = ModoJogo.DESAFIO;
//			criarJogo(event);
//		}
//	}
//	
//	@FXML
//	private void inicializarTestFieldsNumericos() {
//		qtdEmpacotadores.textProperty().addListener(new ChangeListener<String>() {
//		    @Override
//		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
//		        String newValue) {
//		        if (!newValue.matches("\\d*")) {
//		            qtdEmpacotadores.setText(newValue.replaceAll("[^\\d]", ""));
//		        }
//		    }
//		});
//		tempoEmpacotamento.textProperty().addListener(new ChangeListener<String>() {
//		    @Override
//		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
//		        String newValue) {
//		        if (!newValue.matches("\\d*")) {
//		            qtdEmpacotadores.setText(newValue.replaceAll("[^\\d]", ""));
//		        }
//		    }
//		});
//		tempoViagem.textProperty().addListener(new ChangeListener<String>() {
//		    @Override
//		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
//		        String newValue) {
//		        if (!newValue.matches("\\d*")) {
//		            qtdEmpacotadores.setText(newValue.replaceAll("[^\\d]", ""));
//		        }
//		    }
//		});
//		cargaDeposito.textProperty().addListener(new ChangeListener<String>() {
//		    @Override
//		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
//		        String newValue) {
//		        if (!newValue.matches("\\d*")) {
//		            qtdEmpacotadores.setText(newValue.replaceAll("[^\\d]", ""));
//		        }
//		    }
//		});
//	}
//	
//	@FXML
//	private void criarJogo(ActionEvent event) {
//		switch (Main.modoJogo) {
//			case CRIATIVO:
//				Main.cargaMaximaDeposito = Integer.parseInt(cargaDeposito.getText());
//				Main.empty = new Semaphore(Integer.parseInt(cargaDeposito.getText()));
//
//				Main.cargaMaximaVagao = 5; // RECEBER VALOR
//				Main.qtdEmpacotadores = Integer.parseInt(qtdEmpacotadores.getText());
//				Main.tempoEmpacotamentoInicial = Integer.parseInt(tempoEmpacotamento.getText());
//				Main.tempoViagemInicial = Integer.parseInt(tempoViagem.getText());
//				break;
//			case DESAFIO:
//				Main.cargaMaximaDeposito = 10;
//				Main.empty = new Semaphore(10);
//
//				Main.cargaMaximaVagao = 5;
//				Main.qtdEmpacotadores = 1;
//				Main.tempoEmpacotamentoInicial = 5;
//				Main.tempoViagemInicial = 30;
//				break;
//
//			default:
//				throw new IllegalArgumentException("Unexpected value: " + Main.modoJogo);
//		}
//		BorderPane root;
//        try {
//        	root = (BorderPane)FXMLLoader.load(getClass().getResource("TelaEstacao.fxml"));
//        	
//        	Scene scene = new Scene(root, 900, 460);
//        	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//
//            Stage stage = new Stage();
//            stage.setTitle("Estação de Trem");
//            stage.setScene(scene);
//            stage.setResizable(false);
//            
//            stage.show();
//            
//            ((Node) event.getSource()).getScene().getWindow().hide();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//	}
//	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		descricaoModoJogo.setWrapText(true);
//		botaoIniciar.setDisable(true);
//		tabCriacao.setDisable(true);
//		
//		inicializarTestFieldsNumericos();
	}
	
}