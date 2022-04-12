package threads.trem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaContratarEmpacotadorController implements Initializable {
	int tempoEmpacotamento = 10;
	int valorCompra = 20;
	@FXML
	private AnchorPane telaAddEmpacotador;
	
	@FXML
	private TextField nomeEmpacotador;
	
	@FXML
	private TextField idEmpacotador;

	@FXML
	private TextField velocidadeEmpacotador;

	@FXML
	private TextField custo;

	Stage stage;

	@FXML
	private void negarContrato(ActionEvent event) {
		Main.contratoAceito = false;
		sair();
	}

	@FXML
	private void aumentarTempo(ActionEvent event) {
		if (tempoEmpacotamento == 10) return;
		tempoEmpacotamento += 1;
		valorCompra -= 10;
		velocidadeEmpacotador.setText(Integer.toString(tempoEmpacotamento));
		custo.setText(Integer.toString(valorCompra));
	}

	@FXML
	private void diminuirTempo(ActionEvent event) {
		if (tempoEmpacotamento == 2) return;
		tempoEmpacotamento -= 1;
		valorCompra += 10;
		velocidadeEmpacotador.setText(Integer.toString(tempoEmpacotamento));
		custo.setText(Integer.toString(valorCompra));
	}
	
	@FXML
	private void contratar(ActionEvent event) {
    
		if (Main.modoJogo == ModoJogo.DESAFIO  &&  Main.qtmoedas < valorCompra) {
			avisaSaldoInsuficiente();
			return;
		}
		Main.tempoEmpacotamento = Integer.parseInt(velocidadeEmpacotador.getText());
		Main.identificadorEmpacotador = idEmpacotador.getText();
		Main.nomeEmpacotador = nomeEmpacotador.getText();
		Main.contratoAceito = true;
		if(Main.modoJogo == ModoJogo.DESAFIO) {
			Main.alterarQtMoedas(-valorCompra);
		}
		sair();
	}
	
	private void sair() {
		stage = (Stage) telaAddEmpacotador.getScene().getWindow();
		
		Main.fecharJogo(stage, false);
	}
	
	@FXML
	private void inicializarTestFieldsNumericos() {
		custo.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	custo.setText(newValue.replaceAll("[^\\d]", ""));
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
	
	/* Métodos de registro de Log */
	
	private void avisaSaldoInsuficiente() {
		String mensagem = "Impossível contratar empacotador com saldo atual!";
		
		Log.printlog(Log.codigoErro, mensagem);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		velocidadeEmpacotador.setText(Integer.toString(tempoEmpacotamento));
		custo.setText(Integer.toString(valorCompra));
		inicializarTestFieldsNumericos();
	}

}
