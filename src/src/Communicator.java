package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communicator {
	static ServerSocket host;
	static Socket soc;
	DataInputStream is;
	DataOutputStream os;
	GUI g;
	Controller c;
	int port = 0;
	InetAddress ip;
	
	public Communicator() {
		
	}
	
	public void startConnection() {
		try {
			clientConnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void hostConnect() throws UnknownHostException, IOException {
		if (port == 0 || ip == null) {
			c.conSet(g.getFrame());
			port = Integer.parseInt(c.getPort());
		}
		
		host = new ServerSocket(port, 1, InetAddress.getByName(c.getIP()));
		soc = host.accept();
		
		hostCommunicator();
	}
	
	private void hostCommunicator() {
		
		while (true) {
			try {
				is = new DataInputStream(soc.getInputStream());
				String string = is.readUTF();
				System.out.println("Data Recieved from opponet: " + string);
			} catch (Exception e1) {
				
				try {
					Thread.sleep(3000);
					System.exit(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void clientConnect() throws UnknownHostException, IOException {
		if (port == 0 || ip == null) {
			c.conSet(g.getFrame());
			port = Integer.parseInt(c.getPort());
		}
		
		soc = new Socket(InetAddress.getByName(c.getIP()), port);
		clientCommunicator(); 
	}
	
	private void clientCommunicator() {
		while (true) {
			try {
				is = new DataInputStream(soc.getInputStream());
				String string = is.readUTF();
				System.out.println("Data Recieved from opponet: " + string);
			} catch (Exception e1) {
				try {
					Thread.sleep(3000);
					System.exit(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addGUI(GUI g) {
		this.g = g;
	}
	
	public void setController(Controller con) {
		this.c = con;
	}
	
	
}
