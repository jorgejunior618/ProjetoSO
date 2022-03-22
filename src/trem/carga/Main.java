package trem.carga;

import java.util.concurrent.Semaphore;

public class Main {
	private static Empacotador[] empacotadores = new Empacotador[10];
	private static Trem tremDeCarga = new Trem();
	
	public static Semaphore empty = new Semaphore(3);
	public static Semaphore full = new Semaphore(0);
	public static Semaphore mutex = new Semaphore(1);
	
	public static int cargaDeposito = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
