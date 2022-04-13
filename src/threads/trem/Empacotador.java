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
	 * Função de temporizar o tempo de empacotamento dedfinido para o empacotador
	 */
	private void empacotar() {
		avisaEmpacotando();
		
		long inicio = System.currentTimeMillis();
		
		this.controller.comecarTrabalhoEmpacotador(this.id);
		while(System.currentTimeMillis() - inicio  < (long) (tempoEmpacotamento * 1000) - 500 && !Main.encerrarThreads) {
		}
		this.controller.ficarProntoEmpacotador(this.id);
	}

	/**
	 * Função para chamar a animação de ir deixar o pacote na posição do depósito
	 */
	private void deixarPacote() throws InterruptedException {
		this.controller.entregarPacoteEmpacotador(this.id);

		long inicio = System.currentTimeMillis();
		while(System.currentTimeMillis() - inicio  < 500) {
		}
	}

	/**
	 * Função para incrementar o valor total de pacotes no depósito
	 */
	private void inserirPacote() {
		Main.cargaDeposito += 1;
		if (Main.cargaDeposito == Main.cargaMaximaVagao) {
			Main.full.release();
		} else if (Main.cargaDeposito > Main.cargaMaximaVagao && Main.full.availablePermits() == 0) {
			Main.full.release();
		}
    
		avisaGuardandoPacote();
		
    this.controller.mudaTextoQtdPacotes();
	}
	
	/**
	 * Função para chamar a animação de voltar a posição inicial
	 */
	private void voltarAoPosto() throws InterruptedException {
		long inicio = System.currentTimeMillis();

		this.controller.voltarAoTrabalhoEmpacotador(id);
		while(System.currentTimeMillis() - inicio  < 500) {
		}
	}
	
	/* Métodos de registro de Log */
	
	private void avisaInicializacao() {
		String mensagem = "O novo empacotador %s se juntou a equipe!";
		
		Log.printlog(this.nome, mensagem);
	}
	
	private void avisaEmpacotando() {
		String mensagem = "Empacotador %s a empacotar...";
		
		Log.printlog(this.nome, mensagem);
	}
	
	private void avisaGuardandoPacote() {
		String mensagem = "Empacotador %s acaba de guardar um pacote no depósito!";
		
		Log.printlog(this.nome, mensagem);
	}
	
//	private void avisaDescanso() {
//		String mensagem = "Empacotador %s está descansando...";
//		
//		Log.printlog(this.nome, mensagem);
//	}
	

	public void run() {
		avisaInicializacao();
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
