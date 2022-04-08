package br.com.station.prototype;

public class Train extends Thread{
	public int trainCapacity;
	public int travelTime;
	
	public Train(String name, int trainCapacity, int travelTime) {
		super(name);
		this.trainCapacity = trainCapacity;
		this.travelTime = travelTime;
	}
	
	public void loadPackage() {
		Station.packageCount -= this.trainCapacity;
		
		//debug
		System.out.println(String.format("Batch was moved from station to train %s", this.getName()));
		System.out.println(String.format("In Storage: (%d/%d)", Station.packageCount, Station.storageCapacity));
		
		
	}
	
	public void travel() {
		//debug
		System.out.println(String.format("Train %s is leaving station A to B...", this.getName()));
		
		for (int i=0; i<travelTime; i++) { 
			long time = System.currentTimeMillis();
			while(System.currentTimeMillis() - time < travelTime){
				
			}
		}
	}
	
	public void homecoming() {
		//debug
		System.out.println(String.format("Train %s is returning to station A...", this.getName()));
		
		for (int i=0; i<travelTime; i++) { 
			long time = System.currentTimeMillis();
			while(System.currentTimeMillis() - time < travelTime){
				
			}
		}
	}
	
	public void run() {
		while (true) {
			try {
				Station.full.acquire();
				Station.mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			loadPackage();
			for(int i = 0; i < this.trainCapacity; i++) {
				Station.empty.release();
				Station.mutex.release();
			}
			travel();
			homecoming();
			
			//debug
			System.out.println(String.format("Train %s has returned! :D", this.getName()));
			
		}
	}
}
