package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

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
	Board b;

	
	public Communicator() {
		
	}
	
	/*
	 * getter and setter block
	 */
	
	public void addGUI(GUI g) {
		this.g = g;
	}
	
	public void setController(Controller con) {
		this.c = con;
	}
	public void setPort(String port) {
		this.port = Integer.parseInt(port); 
	}
	public void setIP(String ip) {
		try {
			this.ip = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*The first thing called after the gui, communicator, and controller are set up
	 * It is what starts the connection process to the other player
	 * 
	 */
	public void startConnection(JFrame Frame) {
		c.launchPrompt(Frame);
	}
	
	/*
	 * This method is responsible for setting up the connection as the host.
	 * The IP address will be set to the local IP. Port will be whatever the user enters
	 * unless the user enters nothing than it will be set to 1234
	 */
	
	public void hostConnect() throws UnknownHostException, IOException {
		System.out.println("---hostConnect Start---");
		System.out.println("---Communicator, Line 46 ---");
		
		System.out.println("Port: " + port);
		if (port == 0) { port = 1234; }
		
		System.out.println("Host IP: " + InetAddress.getLocalHost());
		host = new ServerSocket(port, 1, InetAddress.getLocalHost());
		soc = host.accept();
		System.out.println("---hostConnect End---");
		hostCommunicator();
		
	}
	
	
	/*
	 * The method that is called when the app is set to host. It is what listens to the port and get the data from it.
	 */
	
	private void hostCommunicator() {
		System.out.println("---hostCommunicator Start---");
		System.out.println("---Communicator, Line 72 ---");
		while (true) {
			try {
				is = new DataInputStream(soc.getInputStream());
				String string = is.readUTF();
				g.updateBoard(pListDecode(string));
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
	
	/*
	 * Same responsibilities as hostConnect but this one is for when the app is set to client. 
	 */
	public void clientConnect() throws UnknownHostException, IOException {
		System.out.println("---clientConnect Start---");
		System.out.println("---Communicator, Line 98 ---");
		if (port == 0 || ip == null) {
			port = 1234;
			soc = new Socket(InetAddress.getLocalHost(), port);
		}
		

		soc = new Socket(c.getIP(), port);
		clientCommunicator(); 
		System.out.println("---clientConnect End---");
	}
	
	
	/*
	 * The client equivalent of hostCommunicator
	 */
	
	private void clientCommunicator() {
		System.out.println("---clientCommunicator Start---");
		System.out.println("---Communicator, Line 115 ---");
		while (true) {
			try {
				is = new DataInputStream(soc.getInputStream());
				String string = is.readUTF();
				g.updateBoard(pListDecode(string));
			} catch (Exception e1) {
				try {
					e1.printStackTrace();
					Thread.sleep(3000);
					System.exit(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * The method that is responsible for actually sending the message to the other player
	 * It is only called when the submit button is pressed. Before the message is sent it calls
	 * pListEncode
	 */

	public void sendMessage() {
		System.out.println("---sendMessage Start---");
		System.out.println("---Communicator, Line 141 ---");
		Piece[] pl = g.getPieceList();
		String encodedMessage = pListEncode(pl);
		
		DataOutputStream os;
		try {
			os = new DataOutputStream(soc.getOutputStream());
			os.writeUTF(encodedMessage); 
			System.out.println("---sendMessage End---");
		} catch (Exception e1) {
			try {
				Thread.sleep(3000);
				System.exit(0);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
		}
	}	
	
	/*
	 * The method that takes the current piece array and converts it into a string so that it can
	 * be passed to the other player. It is called right before the message is sent in sendMessage.
	 * It passes the info for each piece in the format "id/x/y/side/type/hasMoved;
	 */
	
	private String pListEncode(Piece[] pL) {
		System.out.println("---pListEncode start---");
		System.out.println("---Communicator, Line 168 ---");
		message = "";
		for (int x = 0; x < 32; x++) {
			message = message.concat(pL[x].pL_Entry());
			message = message + ";";
		}
		g.updateTurn();
		System.out.println("---pListEncode end---");
		return message;
		
	}
	
	
	/*
	 * pListDecode is called when user reserves a message from the other player
	 * it takes the sent string and uses it to make a new array of pieces. It first breaks
	 * the string into tokens based on the delimiter ";" once it has those tokens it calls the 
	 * method setP
	 */
	private Piece[] pListDecode(String s) {
		System.out.println("---pListDecode Start---");
		System.out.println("---Communicator, Line 189 ---");
		System.out.println("Encoded String: " + s);
		Piece newPL[] = new Piece[32];
		String[] pieceData = s.split(";");
		
		
		for (int x = 0; x < 32; x++) {
			newPL[x] = setP(pieceData[x]);
		}
		g.updateTurn();
		System.out.println("---pListDecode End---");
		return newPL;
	}
	
	/*
	 * setP is called by pListDecode and is passed a single string at a time. It takes that
	 * string and breaks it up using the delimiter "/" it then takes this information and creates 
	 * new piece based off of it.
	 */
	private Piece setP(String s) {
		System.out.println("---setP Start---");
		System.out.println("---Communicator, Line 210 ---");
		String[] token = s.split("/");
		KingMove km = new KingMove();
		QueenMove qm = new QueenMove();
		KnightMove nm = new KnightMove();
		RookMove rm = new RookMove();
		BishopMove bm = new BishopMove();
		PawnMove pm = new PawnMove();
		Piece p;
		int x = Integer.parseInt(token[1]);
		int y = Integer.parseInt(token[2]);
		int id = Integer.parseInt(token[0]);
		char side = token[4].charAt(0);
		char type = token[3].charAt(0);
		boolean hasMoved;
		
		System.out.println("Token[3]: " + token[3]);	
		if (token[5] == "t") {
			hasMoved = true;
		} else {
			hasMoved = false;
		}

		
		if (type == 'K') {
			
			p = new King(y, x, side, km, id, hasMoved);
			return p;
		}
	
		if (type == 'q') {
			p = new Queen(y, x, side, qm, id, hasMoved);
			return p;
		}
	
		if (type == 'k') {
			p = new Knight(y, x, side, nm, id, hasMoved);
			return p;
		}
	
		if (type == 'r') {
			p = new Rook(y, x, side, rm, id, hasMoved);
			return p;
		}
	
		if (type == 'b') {
			p = new Bishop(y, x, side, bm, id, hasMoved);
			return p;
			
		} else {
			p = new Pawn(y, x, side, pm, id, hasMoved);
			return p;
		}

	}
}	
