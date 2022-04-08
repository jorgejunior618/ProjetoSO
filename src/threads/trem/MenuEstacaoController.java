package threads.trem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuEstacaoController implements Initializable {
	@FXML
	private AnchorPane telaMenuEstacao;
	
	@FXML
	private TabPane tabPaneEstacao;
	
	@FXML
	private Tab tabEstacao;
	
	@FXML
	private Tab tabTrem;
	
	@FXML
	private Tab tabEmpacotador;
	
	@FXML
	private TextField capacidadeDeposito;
	
	@FXML
	private TextField nomeTrem;
	
	@FXML
	private TextField capacidadeTrem;
	
	@FXML
	private TextField velocidadeTrem;
	
	@FXML
	private TextField nomeEmpacotador;
	
	@FXML
	private TextField idEmpacotador;
	
	@FXML
	private TextField velocidadeEmpacotador;
	
	@FXML
	private Button menuEstacao;
	
	@FXML
	private Button proximoEstacao;
	
	@FXML
	private Button voltarTrem;
	
	@FXML
	private Button proximoTrem;
	
	@FXML
	private Button voltarEmpacotador;
	
	@FXML
	private Button finalizar;
	
	Stage stage;
	
	@FXML
	private void voltarParaMenu(ActionEvent event) {
		AnchorPane root;
        try {
        	root = (AnchorPane)FXMLLoader.load(getClass().getResource("MenuInicial.fxml"));
        	
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
	
	SingleSelectionModel<Tab> selectionModel;
	
	@FXML
	private void irParaEstacao(ActionEvent event) {
		//SingleSelectionModel<Tab> selectionModel = tabPaneEstacao.getSelectionModel();
        selectionModel.select(tabEstacao);
	}
	
	@FXML
	private void irParaTrem(ActionEvent event) {
		//SingleSelectionModel<Tab> selectionModel = tabPaneEstacao.getSelectionModel();
        selectionModel.select(tabTrem);
 
	}
	
	@FXML
	private void irParaEmpacotador(ActionEvent event) {
		//SingleSelectionModel<Tab> selectionModel = tabPaneEstacao.getSelectionModel();
        selectionModel.select(tabEmpacotador);
	}
	
	@FXML
	private void finalizar(ActionEvent event) {
		Main.qtdEmpacotadores = 1;
		Main.cargaMaximaDeposito = Integer.parseInt(capacidadeDeposito.getText());
		Main.cargaMaximaVagao = Integer.parseInt(capacidadeTrem.getText());

		// Campos para inicalizar a thread
		// Empacotadores
		Main.tempoEmpacotamentoInicial = Integer.parseInt(velocidadeEmpacotador.getText());
		Main.nomePrimeiroEmpacotador = nomeEmpacotador.getText();
		Main.identificadorPrimeiroEmpacotador = idEmpacotador.getText();
		
		// Trem
		Main.tempoViagemInicial = Integer.parseInt(velocidadeTrem.getText());
		Main.nomeTrem = nomeTrem.getText();
		Main.empty = new Semaphore(Integer.parseInt(capacidadeDeposito.getText()));
		
//		Main.empacotadores[0] = new Empacotador(
//			"",
//			"",
//			0,
//			null
//		);
		
		AnchorPane root;
        try {
        	root = (AnchorPane)FXMLLoader.load(getClass().getResource("TelaEstacao.fxml"));
        	
        	Scene scene = new Scene(root, 900, 460);
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
		stage = (Stage) telaMenuEstacao.getScene().getWindow();
		
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
	
	@FXML
	private void inicializarTestFieldsNumericos() {
		capacidadeDeposito.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	capacidadeDeposito.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		capacidadeTrem.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	capacidadeTrem.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		velocidadeTrem.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	velocidadeTrem.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		velocidadeEmpacotador.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	velocidadeEmpacotador.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		selectionModel = tabPaneEstacao.getSelectionModel();
		
		inicializarTestFieldsNumericos();
		
		// TODO Auto-generated method stub
		
	}
	
}