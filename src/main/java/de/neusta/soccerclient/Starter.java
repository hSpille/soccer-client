package de.neusta.soccerclient;


public class Starter {

	public static void main(String[] args) throws InterruptedException {
		Agent smith = new Agent("127.0.0.1","TeamA");
		Agent hurz = new Agent("127.0.0.1","TeamA");
		while(smith.isConnected()){
			
			Thread.sleep(1000);
		}
	
	}

}
