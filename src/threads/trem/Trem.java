package threads.trem;

public class Trem extends Thread{
	public int id;
	public int tempoTransporte;
	public TelaEstacaoController controller;

	public Trem(int id, int tt, TelaEstacaoController controller) {
		this.id = id;
		this.tempoTransporte = tt;
		this.controller = controller;
	}
	
	private void transportar() {
		this.controller.sairParaEntregaTrem();
		this.controller.iniciarTrajetoMiniTrem();
		System.out.println("Saindo para entrega");
		
		long inicio = System.currentTimeMillis();
		long tempoCorrido = 0;

		double progresso = 0.000000; 
		this.controller.atualizarProgressoTrem(progresso);
		while(tempoCorrido < (long) tempoTransporte * 500) {
			tempoCorrido = System.currentTimeMillis() - inicio;
			
			int andamento = (int) tempoCorrido / (tempoTransporte*1);
			if (andamento  / 1000.0 != progresso) {
				progresso = andamento / 1000.0;
				this.controller.atualizarProgressoTrem(progresso);
			}
		}
		this.controller.retornarTrajetoMiniTrem();
		while(tempoCorrido < (long) tempoTransporte * 1000 - 2500) {
			tempoCorrido = System.currentTimeMillis() - inicio;
			
			int andamento = (int) tempoCorrido / (tempoTransporte*1);
			if (andamento  / 1000.0 != progresso) {
				progresso = andamento / 1000.0;
				this.controller.atualizarProgressoTrem(progresso);
			}
		}
		this.controller.chegaNaEstacaoTrem();
		while(System.currentTimeMillis() - inicio  < (long) tempoTransporte * 1000) {
			tempoCorrido = System.currentTimeMillis() - inicio;
			
			int andamento = (int) tempoCorrido /(tempoTransporte*1);
			if (andamento / 1000.0 != progresso) {
				progresso = andamento / 1000.0;
				this.controller.atualizarProgressoTrem(progresso);
			}
		}
		
		System.out.println(String.format("O Trem voltou à estação"));
	}

	private void encherCarga() {
		System.out.println("Movendo Pacotes do depósito para Carga do trem");
		int i;
		
		for (i = 0; i < Main.cargaMaxima; i++) {
			Main.cargaDeposito -= 1;
			Main.empty.release();
		}
		this.controller.mudaTextoQtdPacotes();
	}

	public void run() {
		while(true) {
			try {
				Main.depositoCheio.acquire();
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
