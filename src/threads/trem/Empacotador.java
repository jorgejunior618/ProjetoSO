package threads.trem;

public class Empacotador extends Thread {
	public int id;
	public String nome;
	public int tempoEmpacotamento;

	public Empacotador(int id, String nome, int te) {
		super(nome);
		this.id = id;
		this.tempoEmpacotamento = te;
		this.nome = nome;
	}

	private void empacotar() {
		long inicio = System.currentTimeMillis();
		while(System.currentTimeMillis() - inicio  < (long) tempoEmpacotamento * 1000) {
		}
		System.out.println(String.format("%s Terminou", getName()));
	}

	private void inserirPacote() {
		Main.cargaDeposito += 1;
	}

	public void run() {
		while(true) {
			empacotar();
			try {
				Main.empty.acquire();
				Main.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			inserirPacote();
			Main.mutex.release();
			Main.full.release();

			System.out.println(String.format("Carga do Deposito: %d", Main.cargaDeposito));	
		}
	}
}
