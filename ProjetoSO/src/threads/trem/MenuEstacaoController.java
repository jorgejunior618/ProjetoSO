package threads.trem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class MenuEstacaoController implements Initializable {
	
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
	
	@FXML
	private void voltarParaMenu(ActionEvent event) {
		
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
		
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		selectionModel = tabPaneEstacao.getSelectionModel();
		
		// TODO Auto-generated method stub
		
	}
	
}