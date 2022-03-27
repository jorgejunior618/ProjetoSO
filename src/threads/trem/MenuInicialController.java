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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuInicialController implements Initializable {
	@FXML
	private RadioButton modoCriativo;

	@FXML
	private RadioButton modoDesafio;
	
	@FXML
	private Label descricaoModoJogo;
	
	@FXML
	private Button botaoIniciar;
	
	@FXML
	private Tab tabCriacao;

	@FXML
	private Tab tabSelecao;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private TextField qtdEmpacotadores; 

	@FXML
	private TextField tempoEmpacotamento; 

	@FXML
	private TextField tempoViagem; 

	@FXML
	private TextField cargaDeposito; 
	
	@FXML
	private void selecionarCriativo() {
		if (botaoIniciar.isDisabled()) {
			botaoIniciar.setDisable(false);
		}
		if (modoDesafio.isSelected()) {			
			modoDesafio.setSelected(false);
		}
		modoCriativo.setSelected(true);
		
		descricaoModoJogo.setText("Faça upgrades na sua estação a qualquer momento");
	}

	@FXML
	private void selecionarDesafio() {
		if (botaoIniciar.isDisabled()) {
			botaoIniciar.setDisable(false);
		}
		if (modoCriativo.isSelected()) {			
			modoCriativo.setSelected(false);
		}
		modoDesafio.setSelected(true);
		
		descricaoModoJogo.setText("Os upgrades só estarão disponíveis se puder pagar por eles");
	}
	
	@FXML
	private void iniciarJogo(ActionEvent event) {
		if(modoCriativo.isSelected()) {
			if (tabCriacao.isSelected()) {
				criarJogo(event);
			}
			Main.modoJogo = ModoJogo.CRIATIVO;
			tabCriacao.setDisable(false);
			
			SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
			selectionModel.select(tabCriacao);
			
			tabSelecao.setDisable(true);
		}
		if(modoDesafio.isSelected()) {
			Main.modoJogo = ModoJogo.DESAFIO;
			criarJogo(event);
		}
	}
	

	@FXML
	private void voltarModoJogo() {
		tabSelecao.setDisable(false);
		
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(tabSelecao);
		
		tabCriacao.setDisable(true);		
	}
	
	@FXML
	private void inicializarTestFieldsNumericos() {
		qtdEmpacotadores.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            qtdEmpacotadores.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		tempoEmpacotamento.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            qtdEmpacotadores.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		tempoViagem.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            qtdEmpacotadores.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		cargaDeposito.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            qtdEmpacotadores.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
	}
	
	@FXML
	private void criarJogo(ActionEvent event) {
		switch (Main.modoJogo) {
			case CRIATIVO:
				Main.cargaMaxima = Integer.parseInt(cargaDeposito.getText());
				Main.tremDeCarga = new Trem(
					1,
					Integer.parseInt(cargaDeposito.getText()),
					Integer.parseInt(tempoViagem.getText())
				);
				Main.empty = new Semaphore(Integer.parseInt(cargaDeposito.getText()));
				Main.qtdEmpacotadores = Integer.parseInt(qtdEmpacotadores.getText());
				Main.tempoEmpacotamentoInicial = Integer.parseInt(tempoEmpacotamento.getText());
				Main.tempoViagemInicial = Integer.parseInt(tempoViagem.getText());
				break;
			case DESAFIO:
				Main.cargaMaxima = 20;
				Main.qtdEmpacotadores = 1;
				Main.tempoEmpacotamentoInicial = 2;
				Main.tempoViagemInicial = 10;
				Main.tremDeCarga = new Trem(1, 20, 10);
				Main.empty = new Semaphore(20);
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + Main.modoJogo);
		}
		BorderPane root;
        try {
        	root = (BorderPane)FXMLLoader.load(getClass().getResource("TelaEstacao.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Estação de Trem");
            stage.setScene(new Scene(root, 900, 460));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		descricaoModoJogo.setWrapText(true);
		botaoIniciar.setDisable(true);
		tabCriacao.setDisable(true);
		
		inicializarTestFieldsNumericos();
	}
	
}