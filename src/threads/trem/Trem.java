package threads.trem;

public class Trem extends Thread {
	public int id;
	public String nome;
	public int tempoTransporte;
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

		double progresso = 0.000000;
		//this.controller.atualizarProgressoTrem(progresso);
		while(tempoCorrido < (long) tempoTransporte * 500 && !Main.encerrarThreads) {
			tempoCorrido = System.currentTimeMillis() - inicio;
			
			int andamento = (int) tempoCorrido * 2 / (tempoTransporte * 1);
			if (andamento  / 1000.0 != progresso) {
				progresso = andamento / 1000.0;
				//this.controller.atualizarProgressoTrem(progresso);
			}
		}
	}
	
	private void volta(long inicio) {
		double progresso = 1.00000;
		long tempoCorrido = tempoTransporte * 500;
		
		while(tempoCorrido < (long) tempoTransporte * 1000 - 1500 && !Main.encerrarThreads) {
			tempoCorrido = System.currentTimeMillis() - inicio;
			
			int andamento = (tempoTransporte * 200) - (int) tempoCorrido * 2 / (tempoTransporte*1);
			if (andamento  / 1000.0 != progresso) {
				progresso = andamento / 1000.0;
				//this.controller.atualizarProgressoTrem(progresso);
			}
		}
		this.controller.chegaNaEstacaoTrem();
		while(System.currentTimeMillis() - inicio  < (long) tempoTransporte * 1000 && !Main.encerrarThreads) {
			tempoCorrido = System.currentTimeMillis() - inicio;
			
			int andamento =  (tempoTransporte * 200) -  (int) tempoCorrido * 2 /(tempoTransporte*1);
			if (andamento / 1000.0 != progresso) {
				progresso = andamento / 1000.0;
				//this.controller.atualizarProgressoTrem(progresso);
			}
		}
		avisaChegada();
		Main.qtmoedas+=1;
	}

	private void transportar() {
		long inicio = System.currentTimeMillis();
		this.controller.sairParaEntregaTrem();
		ida(inicio);

		volta(inicio);
	}

	private void encherCarga() {
		int i;
		
		for (i = 0; i < Main.cargaMaximaVagao; i++) {
			Main.cargaDeposito -= 1;
			Main.empty.release();
		}
		this.controller.mudaTextoQtdPacotes();
	}
	
	/* Métodos de registro de Log */
	
	private void avisaInicializacao() {
		String mensagem = "Novo trem %s está pronto para uso!";
		
		Log.printlog(this.nome, mensagem);
	}
	
	private void avisaPartida() {
		String mensagem = "Trem %s acaba de partir da estação!";
		
		Log.printlog(this.nome, mensagem);
	}
	
	private void avisaChegada() {
		String mensagem = "Trem %s acaba de chegar na estação com o pagamento!";
		
		Log.printlog(this.nome, mensagem);
	}

	public void run() {
		avisaInicializacao();
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
