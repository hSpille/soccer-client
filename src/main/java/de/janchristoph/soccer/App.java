package de.janchristoph.soccer;


public class App {

	public static void main(String[] args) {
		AbstractAgent a1 = new BasicAgent("TeamHS");
		a1.start();
//		AbstractAgent a2 = new BasicAgent("TeamHS");
//		a2.start();
		AbstractAgent b1 = new BasicAgent("TeamJC");
		b1.start();
//		AbstractAgent b2 = new BasicAgent("TeamJC");
//		b2.start();
	}

}
