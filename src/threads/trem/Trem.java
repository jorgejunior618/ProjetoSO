package threads.trem;

public class Trem extends Thread {
	public int id;
	public String nome;
	public int tempoTransporte;
	private int qtdPacotesTransporte = 0;
	public TelaEstacaoController controller;

	public Trem(int id, int tt, String nome, TelaEstacaoController controller) {
		super(nome);
		this.id = id;
		this.tempoTransporte = tt;
		this.nome = nome;
		this.controller = controller;
		
	}
	
	private void ida(long inicio) {
		avisaPartida();
		
		long tempoCorrido = 0;

		while(tempoCorrido < (long) tempoTransporte * 500 && !Main.encerrarThreads) {
			tempoCorrido = System.currentTimeMillis() - inicio;
		}
	}
	
	private void volta(long inicio) {
		avisaChegada();
		if(Main.modoJogo == ModoJogo.DESAFIO) {
			Main.alterarQtMoedas(qtdPacotesTransporte);	
		}
		this.controller.alteraTextoMoedas();
		this.qtdPacotesTransporte = 0;
		
		long tempoCorrido = tempoTransporte * 500;
		
		while(tempoCorrido < (long) tempoTransporte * 1000 - 1500 && !Main.encerrarThreads) {
			tempoCorrido = System.currentTimeMillis() - inicio;
		}
		this.controller.chegaNaEstacaoTrem();
		while(System.currentTimeMillis() - inicio  < (long) tempoTransporte * 1000 && !Main.encerrarThreads) {
			tempoCorrido = System.currentTimeMillis() - inicio;
		}
		System.out.println(String.format("O Trem voltou Ã  estaÃ§Ã£o."));
	}

	private void transportar() {
		long inicio = System.currentTimeMillis();
		this.controller.sairParaEntregaTrem();
		ida(inicio);

		volta(inicio);
	}

	private void encherCarga() {
		System.out.println("Movendo Pacotes do depÃ³sito para Carga do trem.");
		int i;
		for (i = 0; i < Main.cargaMaximaVagao; i++) {
			Main.cargaDeposito -= 1;
			this.qtdPacotesTransporte += 1;
			Main.empty.release();
		}
		this.controller.mudaTextoQtdPacotes();
	}
	
	/* Métodos de registro de Log */
	public void avisaPartida() {
		String mensagem = "Trem %s acaba de partir da estação!";
		
		Log.printlog(this.nome, mensagem);
	}
	
	public void avisaChegada() {
		String mensagem = "Trem %s acaba de chegar na estação!";
		
		Log.printlog(this.nome, mensagem);
	}

	public void run() {
		while(!Main.encerrarThreads) {
			try {
				Main.full.acquire();
				Main.mutex.acquire();
				encherCarga();
				Main.mutex.release();
				transportar();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
