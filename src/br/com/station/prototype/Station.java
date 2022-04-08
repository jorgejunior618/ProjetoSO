package br.com.station.prototype;

//import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Station {
	public static int storageCapacity = 50;
	public static int packageCount = 0;
	
	public static Semaphore full = new Semaphore(0);
	public static Semaphore empty = new Semaphore(storageCapacity);
	public static Semaphore mutex = new Semaphore(1);

	public static void main(String[] args) {
		Train train = new Train("Fujiwara", 10, 200);
		Packer packer1 = new Packer("Inutil", "02A1", 150, train.trainCapacity);
		Packer packer2 = new Packer("Sem-Vergonha", "02BC", 180, train.trainCapacity);
		
		train.start();
		packer1.start();
		packer2.start();
		
		

	}

}
