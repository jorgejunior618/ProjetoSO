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
	public static boolean encerrarThreads;
	
	public static int qtdEmpacotadores;
	public static int cargaMaximaDeposito;	// M
	public static int cargaMaximaVagao;		// N
	
	// Campos para inicalizar a thread
	// Empacotadores
	public static boolean contratoAceito;
	public static int idEmpacotadorAlterado;
	public static int tempoEmpacotamento;
	public static String nomeEmpacotador;
	public static String identificadorEmpacotador;
	
	// Trem
	public static int tempoViagemInicial;
	public static String nomeTrem;
	
	// Estacao
	public static int cargaMaximaDepositoAlterada;
	
	public static Semaphore empty;
	public static Semaphore full = new Semaphore(0);

	public static Semaphore mutex = new Semaphore(1);

	public static int cargaDeposito = 0;
		
	public static int qtmoedas = 999;			//Variavel que guarda as moedas atuais.
	public static ModoJogo modoJogo;
	public static TipoMelhoria melhoria = TipoMelhoria.NENHUMA;
	public static Sound musica;
	
	public static void fecharJogo(Stage stage, boolean encerrarTreads) {		
		if (encerrarTreads) {
			long inicio = System.currentTimeMillis();
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
				
				if(System.currentTimeMillis() - inicio > 2000) {
					Main.tremDeCarga.interrupt();
					
					for (int i = 0; i < 10; i++) {
						if (Main.empacotadores[i] != null && Main.empacotadores[i].isAlive()) {
							Main.empacotadores[i].interrupt();
						}
					}
					break;
				}
				
				
			}
			System.out.println("Threads encerradas");
		}
		
		stage.close();
	}
	
	@Override
	public void start(Stage primaryStage) {
		encerrarThreads = false;
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MenuInicial.fxml"));
			Scene scene = new Scene(root, 500, 420);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(event -> {
				event.consume();
				fecharJogo(primaryStage, false);
			});
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String filepath = "musica.wav";
		
		musica = new Sound(filepath);
		
		Log.setAtivado(true);
		
		launch(args);
	}
}
