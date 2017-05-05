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
	
	
	
	public void addGUI(GUI g) {
		this.g = g;
	}
	
	public void setController(Controller con) {
		this.c = con;
	}
	
	public void startConnection(JFrame Frame) {
		c.launchPrompt(Frame);
	}
	
	public void hostConnect() throws UnknownHostException, IOException {
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		port = 1234;
		System.out.println("Port: " + port);
		System.out.println("Host IP: " + InetAddress.getLocalHost());
		host = new ServerSocket(port, 1, InetAddress.getLocalHost());
//		port = 1234;
//		host = new ServerSocket(port, 1, InetAddress.getByName(c.getIP()));
		soc = host.accept();
		
		hostCommunicator();
	}
	
	private void hostCommunicator() {
		
		while (true) {
			try {
				is = new DataInputStream(soc.getInputStream());
				String string = is.readUTF();
				//pListDecode(string);
				
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
	
	
	public void clientConnect() throws UnknownHostException, IOException {
		if (port == 0 || ip == null) {
	
			c.clientConSet(g.getFrame());
			port = Integer.parseInt(c.getPort());
		}
		
//		soc = new Socket(InetAddress.getLocalHost()), port);
//		port = 1234;		
		soc = new Socket(c.getIP(), port);
		clientCommunicator(); 
	}
	
	
	
	
	private void clientCommunicator() {
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
		for (int x = 0; x < 32; x++) {
			message = message.concat(pL[x].pL_Entry());
			message = message + ";";
		}
		g.updateTurn();
		return message;
		
	}
	
	private Piece[] pListDecode(String s) {
		System.out.println("Encoded String: " + s);
		Piece newPL[] = new Piece[32];
		String[] pieceData = s.split(";");
		
		
		for (int x = 0; x < 32; x++) {
			newPL[x] = setP(pieceData[x]);
		}
		g.updateTurn();
		return newPL;
	}
	
	private Piece setP(String s) {
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
