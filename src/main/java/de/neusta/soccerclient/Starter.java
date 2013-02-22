package de.neusta.soccerclient;

import de.neusta.soccerclient.provided.IAgent;


public class Starter {

	public static void main(String[] args) throws InterruptedException {
		String server = "192.168.43.71";
		IAgent smith = new Agent(server,"TeamA");
//		Agent hurz = new Agent(server,"TeamA");
		while(smith.isConnected()){
			
			Thread.sleep(1000);
		}
	
	}

}
