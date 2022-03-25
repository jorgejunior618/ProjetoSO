package threads.trem;

public class Trem extends Thread{
	public int id;
	public int numPacotes;
	public int tempoTransporte;

	public Trem(int id, int np, int tt) {
		this.id = id;
		this.numPacotes = np;
		this.tempoTransporte = tt;
	}
	
	private void transportar() {
		long inicio = System.currentTimeMillis();
		while(System.currentTimeMillis() - inicio  < (long) tempoTransporte * 5000) {
		}
		System.out.println(String.format("Trem terminou o transporte"));
	}

	private void descarregarPacote() {
		Main.cargaTrem = 0;
	}

	public void run() {
		while(true) {
			transportar();
			try {
				Main.full.release();
				Main.mutex.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			descarregarPacote();
			if(i=0; i<M; I++){
				Main.full.acquire();
			}
			Main.mutex.acquire();
		}
	}
}
