package br.com.station.prototype;

public class Packer extends Thread{
	private String id;
	private int packingTime;
	private int trainLimitWarning;
	
	public Packer(String name, String id, int packingTime, int trainLimitWarning) {
		super(name);
		this.id = id;
		this.packingTime = packingTime;
		this.trainLimitWarning = trainLimitWarning;
	}
	
	public void pack() {
		for (int i=0; i<this.packingTime; i++) { 
			long time = System.currentTimeMillis();
			while(System.currentTimeMillis() - time < this.packingTime){
				
			}
		}
		
	}
	
	public void storePackage() {
		Station.packageCount += 1;
		
		
		//debug
		System.out.println(String.format("Packer %s added package to station", this.getName()));
		System.out.println(String.format("In Storage: (%d/%d)", Station.packageCount, Station.storageCapacity));
		
	}
	
	public void run() {
		while(true) {
			
			pack();
			try {
				Station.empty.acquire();
				Station.mutex.acquire();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			storePackage();
			if (Station.packageCount == this.trainLimitWarning) {
				Station.full.release();
			}
			Station.mutex.release();
			

			
		}
	}

}
