package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Communicator {
	static ServerSocket host;
	static Socket soc;
	DataInputStream is;
	DataOutputStream os;
	GUI g;
	Controller c;
	
	public Communicator() {
		
	}
	
	public void addGUI(GUI g) {
		this.g = g;
	}
	
	public void setController(Controller con) {
		this.c = con;
	}
}
