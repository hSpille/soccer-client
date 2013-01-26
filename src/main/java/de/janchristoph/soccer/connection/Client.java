package de.janchristoph.soccer.connection;


public class Client {
	private ConnectionManager cm = new ConnectionManager();
	private NewDataRunnable newDataRunnable;
	private Thread listenerThread;

	public Client() {
		startListening();
	}

	private void startListening() {
		listenerThread = new Thread(new ClientListener(cm, this));
		listenerThread.start();
	}

	public void init(String teamname) {
		cm.send("(init " + teamname + ")");
	}

	public void reconnect(String teamname, Integer unum) {
		cm.send("(reconnect " + teamname + " " + unum + ")");
	}

	public void bye() {
		cm.send("(bye)");
	}

	public void catch_(Integer direction) {
		cm.send("(catch " + direction + ")");
	}

	public void changeView(Integer width, Integer quality) {
		cm.send("(change_view " + width + " " + quality + ")");
	}

	public void dash(Integer power) {
		cm.send("(dash " + power + ")");
	}

	public void kick(Integer power, Integer direction) {
		cm.send("(kick " + power + " " + direction + ")");
	}

	public void move(Integer x, Integer y) {
		cm.send("(move " + x + " " + y + ")");
	}

	public void say(String message) {
		cm.send("(say " + message + ")");
	}

	public void senseBody() {
		cm.send("(sense_body)");
	}

	public void score() {
		cm.send("(score)");
	}

	public void turn(Integer moment) {
		cm.send("(turn " + moment + ")");
	}

	public void turnNeck(Integer angle) {
		cm.send("(turn_neck " + angle + ")");
	}

	public void onNewData(NewDataRunnable newDataRunnable) {
		this.newDataRunnable = newDataRunnable;
	}

	public void processNewData(String line) {
		newDataRunnable.setDataset(line);
		newDataRunnable.run();
	}
}