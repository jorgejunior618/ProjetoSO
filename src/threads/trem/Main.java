package threads.trem;
	
import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Empacotador[] empacotadores = new Empacotador[10];
	public static Trem tremDeCarga = new Trem();

	public static int cargaMaxima;
	public static Semaphore empty = new Semaphore(10);
	public static Semaphore depositoCheio = new Semaphore(0);

	public static Semaphore mutex = new Semaphore(1);

	public static int cargaDeposito = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("TelaEstacao.fxml"));
			Scene scene = new Scene(root,900,460);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
