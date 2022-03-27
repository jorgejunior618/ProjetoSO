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
		System.out.println(String.format("%s Começando trabalho", getName()));
		long inicio = System.currentTimeMillis();
		
		this.controller.comecarTrabalhoEmpacotador(this.id - 1);
		while(System.currentTimeMillis() - inicio  < (long) (tempoEmpacotamento * 1000) - 500) {
		}
		System.out.println(String.format("%s Terminou de empacotar", getName()));
		this.controller.ficarProntoEmpacotador(this.id - 1);
	}

	private void deixarPacote() throws InterruptedException {
		this.controller.entregarPacoteEmpacotador(this.id - 1);

		long inicio = System.currentTimeMillis();
		while(System.currentTimeMillis() - inicio  < 500) {
		}
	}

	private void inserirPacote() {
		Main.cargaDeposito += 1;
		System.out.println(String.format("Carga do Deposito: %d", Main.cargaDeposito));
		if (Main.cargaDeposito == Main.cargaMaxima) {
			Main.depositoCheio.release();
			System.out.println("Depósito Cheio");
		}
	}

	private void voltarAoPosto() throws InterruptedException {
		long inicio = System.currentTimeMillis();

		this.controller.voltarAoTrabalhoEmpacotador(id - 1);
		while(System.currentTimeMillis() - inicio  < 500) {
		}
	}

	public void run() {
		while(true) {
			try {
				empacotar();
				Main.empty.acquire();
				Main.mutex.acquire();
				deixarPacote();
				inserirPacote();
				Main.mutex.release();
				voltarAoPosto();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
