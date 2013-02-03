package de.janchristoph.soccer.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ConnectionManager {
	private static final int PORT = 6000;
	private String server = "localhost";
	private DatagramSocket socket;

	
	public ConnectionManager (){
		this.server = "localhost";
		createSocket();
	}
	public ConnectionManager(String server) {
		this.server = server;
		createSocket();
	}
	private void createSocket() {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void send(String msg) {
		byte[] data = msg.getBytes();
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length, new InetSocketAddress(server, PORT));
			socket.send(packet);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String recv() {
		byte[] receiveData = new byte[1024];
		DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = packet.getData();
		
		return new String(data);
	}
}
