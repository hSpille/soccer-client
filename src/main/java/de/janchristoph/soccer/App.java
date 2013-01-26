package de.janchristoph.soccer;

import de.janchristoph.soccer.connection.Client;
import de.janchristoph.soccer.connection.NewDataRunnable;

public class App {

	public static void main(String[] args) {
		Client client = new Client();
		client.onNewData(new NewDataRunnable() {
			@Override
			public void run() {
				System.out.println(getDataset());
			}
		});
		client.init("test");
	}

}
