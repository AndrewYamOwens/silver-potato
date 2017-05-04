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
	String message;
	Piece p = new Piece();
	
	public Communicator() {
		
	}
	
	public void startConnection() {
		try {
			hostConnect();
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
				pListDecode(string);
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
				pListDecode(string);
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

	public void sendMessage() {
		Piece[] pl = g.getPieceList();
		String encodedMessage = pListEncode(pl);
		
		DataOutputStream os;
		try {
			os = new DataOutputStream(soc.getOutputStream());
			os.writeUTF(encodedMessage);
		} catch (Exception e1) {
			try {
				Thread.sleep(3000);
				System.exit(0);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
		}
	}	

	
	private String pListEncode(Piece[] pL) {
		message = "";
		for (int x = 0; x < 16; x++) {
			message = message.concat(pL[x].pL_Entry());
			message = message + ";";
		}
		
		return message;
		
	}
	
	private Piece[] pListDecode(String s) {
		System.out.println("Encoded String: " + s);
		Piece newPL[] = new Piece[16];
		String[] pieceData = s.split(";");
		
		for (int x = 0; x < 16; x++) {
			p = new Piece(pieceData[x]);
			newPL[x] = p;
		}
		return newPL;
	}
}	
