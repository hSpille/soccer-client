package de.janchristoph.soccer;

import de.janchristoph.soccer.connection.Client;
import de.janchristoph.soccer.connection.NewDataRunnable;
import de.janchristoph.soccer.model.Ball;
import de.janchristoph.soccer.protocolparser.SeeParser;

public class App {

	private static final String PROTOCOL_SEE_START = "(see";

	public static void main(String[] args) {
		final Client client = new Client();
		client.onNewData(new NewDataRunnable() {
			@Override
			public void run() {
				if (getDataset().startsWith(PROTOCOL_SEE_START)) {
					System.out.println();
					System.out.println(getDataset());

					SeeParser parser = new SeeParser(getDataset());
					Ball ball = parser.parseBall();
					if (ball == null) {
						System.out.println("Ich kann den Ball nicht sehen, ich drehe mich nach rechts, um ihn zu finden.");
						client.turn(45);
						return;
					}

					if (ball.getDirection().doubleValue() > 5) {
						System.out.println("Der Ball ist rechts von mir, ich drehe mich nach rechts.");
						client.turn(ball.getDirection().intValue());
					} else if (ball.getDirection().doubleValue() < -5) {
						System.out.println("Der Ball ist links von mir, ich drehe mich nach links.");
						client.turn(-ball.getDirection().intValue());
					}

					if (ball.getDistance().doubleValue() > 1) {
						System.out.println("Der Ball liegt nicht direkt vor mir, ich laufe vorwärts.");
						client.dash(100);
					} else {
						System.out.println("Der Ball liegt vor mir, ich schieße mit Power = 50.");
						client.kick(50, 0);
					}
				}
			}
		});
		client.init("test");
		client.move(-10, 0);
	}

}
