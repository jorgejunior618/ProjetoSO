package threads.trem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaMelhoriasController implements Initializable {
	int empacotadorSelecionado;
	int capacidadeDepositoInt;
	int custoDepositoInt;
	
	int duracaoViagemTremInt;
	int capacidadeCargaTremInt;
	int custoTremInt;
	
	int tempoEmpacotamentoInt;
	int custoEmpacotadorInt;
	
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

	// ELEMENTOS DA TAB DA ESTA��O
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
	private TextField nomeEmpacotador;

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
	
	
	// METODOS DE MELHORIAS	######################################################################################
	// ESTACAO				######################################################################################

	@FXML
	private void aumentarCargaDeposito() {
		if (capacidadeDepositoInt >= 99) {
			return;
		}
		capacidadeDepositoInt += 5;
		custoDepositoInt += 10;
		
		capacidadeDeposito.setText(Integer.toString(capacidadeDepositoInt));
		custoDeposito.setText(Integer.toString(custoDepositoInt));
	}

	@FXML
	private void diminuirCargaDeposito() {
		if (capacidadeDepositoInt <= Main.cargaMaximaDeposito) {
			return;
		}
		capacidadeDepositoInt -= 5;
		custoDepositoInt -= 10;
		
		capacidadeDeposito.setText(Integer.toString(capacidadeDepositoInt));
		custoDeposito.setText(Integer.toString(custoDepositoInt));
	}

	@FXML
	private void melhorarEstacao() {
		if (Main.qtmoedas < custoDepositoInt) {
			System.out.println("Saldo Insuficiente");
			return;
		}
		
		Main.melhoria = TipoMelhoria.ESTACAO;
		Main.alterarQtMoedas(-custoDepositoInt);
		Main.cargaMaximaDepositoAlterada = capacidadeDepositoInt;
		sair();
	}

	// TREM					######################################################################################

	@FXML
	private void aumentarCargaTrem() {
		if (capacidadeCargaTremInt >= Main.cargaMaximaDeposito) {
			return;
		}
		capacidadeCargaTremInt += 5;
		custoTremInt += 10;
		
		capacidadeCargaTrem.setText(Integer.toString(capacidadeCargaTremInt));
		custoTrem.setText(Integer.toString(custoTremInt));
	}

	@FXML
	private void diminuirCargaTrem() {
		if (capacidadeCargaTremInt <= Main.cargaMaximaVagao) {
			return;
		}
		capacidadeCargaTremInt -= 5;
		custoTremInt -= 10;
		
		capacidadeCargaTrem.setText(Integer.toString(capacidadeCargaTremInt));
		custoTrem.setText(Integer.toString(custoTremInt));
	}

	@FXML
	private void aumentarTempoViagemTrem() {
		if (duracaoViagemTremInt >= Main.tremDeCarga.tempoTransporte) {
			return;
		}
		duracaoViagemTremInt += 5;
		custoTremInt -= 20;
		
		duracaoViagemTrem.setText(Integer.toString(duracaoViagemTremInt));
		custoTrem.setText(Integer.toString(custoTremInt));
	}

	@FXML
	private void diminuirTempoViagemTrem() {
		if (duracaoViagemTremInt <= 15) {
			return;
		}
		duracaoViagemTremInt -= 5;
		custoTremInt += 20;
		
		duracaoViagemTrem.setText(Integer.toString(duracaoViagemTremInt));
		custoTrem.setText(Integer.toString(custoTremInt));
	}

	@FXML
	private void melhorarTrem() {
		if (Main.qtmoedas < custoTremInt) {
			System.out.println("Saldo Insuficiente");
			return;
		}
		
		Main.melhoria = TipoMelhoria.TREM;
		Main.alterarQtMoedas(-custoTremInt);
		Main.nomeTrem = nomeTrem.getText();
		Main.tempoViagemInicial = duracaoViagemTremInt;
		Main.cargaMaximaVagao = capacidadeCargaTremInt;
		sair();
	}
	
	// ESTACAO				######################################################################################

	@FXML
	private void aumentarTempoEmpacotamento() {
		if (tempoEmpacotamentoInt >= Main.empacotadores[empacotadorSelecionado].tempoEmpacotamento) {
			return;
		}
		tempoEmpacotamentoInt += 1;
		custoEmpacotadorInt -= 10;
		
		tempoEmpacotamentoEmpacotador.setText(Integer.toString(tempoEmpacotamentoInt));
		custoEmpacotador.setText(Integer.toString(custoEmpacotadorInt));
	}

	@FXML
	private void diminuirTempoEmpacotamento() {
		if (tempoEmpacotamentoInt <= 2) {
			return;
		}
		tempoEmpacotamentoInt -= 1;
		custoEmpacotadorInt += 10;
		
		tempoEmpacotamentoEmpacotador.setText(Integer.toString(tempoEmpacotamentoInt));
		custoEmpacotador.setText(Integer.toString(custoEmpacotadorInt));
	}

	@FXML
	private void melhorarEmpacotador() {
		if (Main.qtmoedas < custoEmpacotadorInt) {
			System.out.println("Saldo Insuficiente");
			return;
		}
		
		Main.melhoria = TipoMelhoria.EMPACOTADOR;
		Main.alterarQtMoedas(-custoEmpacotadorInt);
		Main.tempoEmpacotamento = tempoEmpacotamentoInt;
		Main.idEmpacotadorAlterado = empacotadorSelecionado;
		sair();
	}
	
	@FXML
	private void demitirEmpacotador() {
		Main.melhoria = TipoMelhoria.NENHUMA;
		sair();
	}
	
	@FXML
	private void proximoEmpacotador() {
		int selecaoAtual = empacotadorSelecionado;
		do  {
			empacotadorSelecionado += 1;
			if (empacotadorSelecionado > 9) {
				empacotadorSelecionado = 0;
			}
			if (empacotadorSelecionado == selecaoAtual) {
				break;
			}
		} while (Main.empacotadores[empacotadorSelecionado] == null);
		tempoEmpacotamentoInt = Main.empacotadores[empacotadorSelecionado].tempoEmpacotamento;
		custoEmpacotadorInt = 0;
		
		nomeEmpacotador.setText(Main.empacotadores[empacotadorSelecionado].nome);
		identificadorEmpacotador.setText(Main.empacotadores[empacotadorSelecionado].identificador);
		tempoEmpacotamentoEmpacotador.setText(Integer.toString(tempoEmpacotamentoInt));
		empacotadorAtual.setText(Integer.toString(empacotadorSelecionado));
		custoEmpacotador.setText("0");
	}
	
	@FXML
	private void empacotadorAnterior() {
		int selecaoAtual = empacotadorSelecionado;
		while (Main.empacotadores[empacotadorSelecionado] == null) {
			empacotadorSelecionado -= 1;
			if (empacotadorSelecionado < 0) {
				empacotadorSelecionado = 9;
			}
			if (empacotadorSelecionado == selecaoAtual) {
				break;
			}
		}
		tempoEmpacotamentoInt = Main.empacotadores[empacotadorSelecionado].tempoEmpacotamento;
		custoEmpacotadorInt = 0;
		
		nomeEmpacotador.setText(Main.empacotadores[empacotadorSelecionado].nome);
		identificadorEmpacotador.setText(Main.empacotadores[empacotadorSelecionado].identificador);
		tempoEmpacotamentoEmpacotador.setText(Integer.toString(tempoEmpacotamentoInt));
		custoEmpacotador.setText("0");
	}

	private void sair() {
		stage = (Stage) telaMelhorias.getScene().getWindow();
		
		Main.fecharJogo(stage, false);
	}
	
	@FXML
	private void inicializarTestFields() {
		capacidadeDeposito.setText(Integer.toString(capacidadeDepositoInt));
		custoDeposito.setText("0");

		nomeTrem.setText(Main.tremDeCarga.nome);
		capacidadeCargaTrem.setText(Integer.toString(capacidadeCargaTremInt));
		duracaoViagemTrem.setText(Integer.toString(duracaoViagemTremInt));
		custoTrem.setText("0");
		
		nomeEmpacotador.setText(Main.empacotadores[empacotadorSelecionado].nome);
		identificadorEmpacotador.setText(Main.empacotadores[empacotadorSelecionado].identificador);
		tempoEmpacotamentoEmpacotador.setText(Integer.toString(tempoEmpacotamentoInt));
		custoEmpacotador.setText("0");
		empacotadorAtual.setText(Integer.toString(empacotadorSelecionado));
		totalEmpacotadores.setText("10");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		empacotadorSelecionado = 0;
		for (int i = 0; i < 10; i++) {
			if (Main.empacotadores[i] != null) {
				empacotadorSelecionado = i;
				break;
			}
		}
		capacidadeDepositoInt = Main.cargaMaximaDeposito;
		custoDepositoInt = 0;
		duracaoViagemTremInt = Main.tremDeCarga.tempoTransporte;
		capacidadeCargaTremInt= Main.cargaMaximaVagao;
		custoTremInt = 0;
		tempoEmpacotamentoInt = Main.empacotadores[0].tempoEmpacotamento;
		custoEmpacotadorInt = 0;
		
		inicializarTestFields();
	}

}
