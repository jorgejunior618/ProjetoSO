package threads.trem;

public class Empacotador extends Thread {
	public int id;
	public String nome;
	public int tempoEmpacotamento;
	public TelaEstacaoController controller;

	public Empacotador(int id, String nome, int te, TelaEstacaoController cont) {
		super(nome);
		
		this.id = id;
		this.tempoEmpacotamento = te;
		this.nome = nome;
		this.controller = cont;
	}

	private void empacotar() {
		this.controller.comecarTrabalhoEmpacotadorController(null);
		
		long inicio = System.currentTimeMillis();
		while(System.currentTimeMillis() - inicio  < (long) (tempoEmpacotamento * 1000) - 400) {
		}
	}

	private void guardar() throws InterruptedException {
		this.controller.terminarTrabalhoEmpacotadorController(null);

		long inicio = System.currentTimeMillis();
		while(System.currentTimeMillis() - inicio  < 400) {
		}
		System.out.println(String.format("%s Terminou", getName()));
	}

	private void inserirPacote() {
		Main.cargaDeposito += 1;
		if (Main.cargaDeposito == Main.cargaMaxima) {
			Main.depositoCheio.release();
		}
	}

	public void run() {
		while(true) {
			
			
			empacotar();
			try {
				guardar();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				Main.empty.acquire();
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			inserirPacote();
			Main.mutex.release();
			
			System.out.println(String.format("Carga do Deposito: %d", Main.cargaDeposito));	
		}
	}
}
