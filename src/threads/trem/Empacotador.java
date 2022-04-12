package threads.trem;

public class Empacotador extends Thread {
	private static int ultimoId = 0;
	private int id;
	public String identificador;
	public String nome;
	public int tempoEmpacotamento;
	public TelaEstacaoController controller;

	public Empacotador(String id, String nome, int te, TelaEstacaoController cont) {
		super(nome);
		
		this.id = Empacotador.ultimoId++;
		this.identificador = id;
		this.tempoEmpacotamento = te;
		this.nome = nome;
		this.controller = cont;
	}

	/**
	 * Fun��o de temporizar o tempo de empacotamento dedfinido para o empacotador
	 */
	private void empacotar() {
		System.out.println(getName() + ": Come�ou a empacotar");
		long inicio = System.currentTimeMillis();
		
		this.controller.comecarTrabalhoEmpacotador(this.id);
		while(System.currentTimeMillis() - inicio  < (long) (tempoEmpacotamento * 1000) - 500 && !Main.encerrarThreads) {
		}
		this.controller.ficarProntoEmpacotador(this.id);
		System.out.println(getName() + ": Terminou de empacotar");
	}

	/**
	 * Fun��o para chamar a anima��o de ir deixar o pacote na posi��o do dep�sito
	 */
	private void deixarPacote() throws InterruptedException {
		System.out.println(getName() + ": Foi guardar pacote");
		this.controller.entregarPacoteEmpacotador(this.id);

		long inicio = System.currentTimeMillis();
		while(System.currentTimeMillis() - inicio  < 500) {
		}
	}

	/**
	 * Fun��o para incrementar o valor total de pacotes no dep�sito
	 */
	private void inserirPacote() {
		Main.cargaDeposito += 1;
		if (Main.cargaDeposito == Main.cargaMaximaVagao) {
			Main.full.release();
		} else if (Main.cargaDeposito > Main.cargaMaximaVagao && Main.full.availablePermits() == 0) {
			Main.full.release();
		}
		System.out.printf("Pacote Inserido, total de pacotes: %d\n", Main.cargaDeposito);

		this.controller.mudaTextoQtdPacotes();
	}
	
	/**
	 * Fun��o para chamar a anima��o de voltar a posi��o inicial
	 */
	private void voltarAoPosto() throws InterruptedException {
		long inicio = System.currentTimeMillis();

		this.controller.voltarAoTrabalhoEmpacotador(id);
		while(System.currentTimeMillis() - inicio  < 500) {
		}
	}

	public void run() {
		while(!Main.encerrarThreads) {
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
