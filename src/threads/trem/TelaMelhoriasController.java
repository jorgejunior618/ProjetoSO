package threads.trem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaMelhoriasController implements Initializable {
	int tempoEmpacotamento = 10;
	int valorCompra = 10;
	Stage stage;	

	@FXML
	private AnchorPane telaMelhorias;

	@FXML
	private TabPane tabPaneMelhorias;

	@FXML
	private Tab tabEstacao;

	@FXML
	private Tab tabTrem;

	@FXML
	private Tab tabEmpacotador;

	// ELEMENTOS DA TAB DA ESTAÇÃO
	@FXML
	private TextField capacidadeDeposito;

	@FXML
	private TextField custoDeposito;

	@FXML
	private Button botaoMelhorarDeposito;

	// ELEMENTOS DA TAB DO TREM
	@FXML
	private TextField nomeTrem;

	@FXML
	private TextField capacidadeCargaTrem;

	@FXML
	private TextField duracaoViagemTrem;

	@FXML
	private TextField custoTrem;

	@FXML
	private Button botaoMelhorarTrem;

	// ELEMENTOS DA TAB DOS EMPACOTADORES
	@FXML
	private TextField nomeEmpacotadoer;

	@FXML
	private TextField identificadorEmpacotador;

	@FXML
	private TextField tempoEmpacotamentoEmpacotador;

	@FXML
	private TextField custoEmpacotador;

	@FXML
	private TextField empacotadorAtual;

	@FXML
	private TextField totalEmpacotadores;

	@FXML
	private Button botaoMelhorarEmpacotador;


//	@FXML
//	private void contratar(ActionEvent event) {
//		if (Main.qtmoedas < valorCompra) {
//			System.out.println("Saldo Insuficiente");
//			return;
//		}
//		Main.tempoEmpacotamento = Integer.parseInt(velocidadeEmpacotador.getText());
//		Main.identificadorEmpacotador = idEmpacotador.getText();
//		Main.nomeEmpacotador = nomeEmpacotador.getText();
//		Main.contratoAceito = true;
//		sair();
//	}
//	
	private void sair() {
		stage = (Stage) telaMelhorias.getScene().getWindow();
		
		Main.fecharJogo(stage, false);
	}
//	
//	@FXML
//	private void inicializarTestFieldsNumericos() {
//		custo.textProperty().addListener(new ChangeListener<String>() {
//		    @Override
//		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
//		        String newValue) {
//		        if (!newValue.matches("\\d*")) {
//		        	custo.setText(newValue.replaceAll("[^\\d]", ""));
//		        }
//		    }
//		});
//		velocidadeEmpacotador.textProperty().addListener(new ChangeListener<String>() {
//		    @Override
//		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
//		        String newValue) {
//		        if (!newValue.matches("\\d*")) {
//		        	velocidadeEmpacotador.setText(newValue.replaceAll("[^\\d]", ""));
//		        }
//		    }
//		});
//	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		velocidadeEmpacotador.setText(Integer.toString(tempoEmpacotamento));
//		custo.setText(Integer.toString(valorCompra));
//		inicializarTestFieldsNumericos();
	}

}
