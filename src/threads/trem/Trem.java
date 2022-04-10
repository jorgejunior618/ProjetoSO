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
		System.out.println("Saindo para entrega");
		
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
		
		System.out.println(String.format("O Trem voltou à estação"));
	}

	private void transportar() {
		long inicio = System.currentTimeMillis();
		this.controller.sairParaEntregaTrem();
		ida(inicio);

		volta(inicio);
	}

	private void encherCarga() {
		System.out.println("Movendo Pacotes do depósito para Carga do trem");
		int i;
		
		for (i = 0; i < Main.cargaMaximaVagao; i++) {
			Main.cargaDeposito -= 1;
			Main.empty.release();
		}
		this.controller.mudaTextoQtdPacotes();
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
