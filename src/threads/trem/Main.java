package threads.trem;
	
import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Empacotador[] empacotadores = new Empacotador[10];
	public static Trem tremDeCarga;
	
	public static int qtdEmpacotadores;
	public static int tempoEmpacotamentoInicial;
	public static int tempoViagemInicial;
	
	public static ModoJogo modoJogo;

	public static int cargaMaxima;
	public static Semaphore empty;
	public static Semaphore depositoCheio = new Semaphore(0);

	public static Semaphore mutex = new Semaphore(1);

	public static int cargaDeposito = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MenuInicial.fxml"));
			Scene scene = new Scene(root, 300, 300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
//			primaryStage.setResizable(false);
			primaryStage.show();
			System.out.println("Depois do show");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
