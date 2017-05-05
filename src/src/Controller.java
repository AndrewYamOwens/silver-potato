package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Controller extends JFrame implements ActionListener {
	
	Board board;
	Square squares[][];
//	Square pieceClick;
	boolean pieceClick = false;
	Square selectedSquare;
	GUI gui;
	Communicator com;
	Piece P;
	String ip;
	String port;
	JFrame Frame;
	
	Controller() {
		
	}
	
	public void setGUI(GUI g) {
		this.gui = g;
	}
	
	public void setCommunicator(Communicator com) {
		this.com = com;
	}
	
	public void buttonClick(Square[][] squares, ActionEvent e, char curTurn, Piece[] pieceList) {
		JButton b = (JButton) e.getSource();
		boolean vM = false;
		if (pieceClick == false && b.getIcon() != null) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) { 
					if (squares[i][j].getButton() == e.getSource()) {
						selectedSquare = squares[i][j];
						System.out.println("Selected Square = (" + j + "," + i + ")");
						P = selectedSquare.getPiece();
						System.out.println("selectedPiece type:" + selectedSquare.getPieceId());
					}
				}
			
			}
			if (P.getColor() == curTurn) {
				pieceClick = true;
			}
		
		} else {
			if (pieceClick == true && e.getSource() != selectedSquare) {
			//to get here the user has to have already selected a piece and then try to move it
				board = gui.getBoard();
				board.printPL();
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) { 
						if (squares[i][j].getButton() == e.getSource()) {
							System.out.println("HasMoved: " + P.getHasMoved());
							P.setdestS(squares[i][j]);
							P.setdestX(j);
							P.setdestY(i);
							vM = P.checkMove(pieceList);
							System.out.println("Move check:" + vM);
							System.out.println("Piece type:" + P.getId());
							if(vM == true) {
//								board.updateTurn();
								System.out.println("Current Turn:" + curTurn);
								if (squares[i][j].getOccupied() == true) {
									for (int x = 0; x < 32; x++) {
										if (squares[i][j].getX() == pieceList[x].getX() && squares[i][j].getY() == pieceList[x].getY() 
												&& pieceList[x].getId() != squares[i][j].getPieceId()) {
											P = squares[i][j].getPiece();
											P.setCaptured(true);
											
										}
									}
								}
								
								System.out.println("Selected Square:" + selectedSquare.getPieceId());
								squares[i][j].setPiece(selectedSquare.getPiece());
								
								board.printPL();
								
								for (int B = 0; B < 8; B++) {
									for (int A = 0; A < 8; A++) { 
										if (squares[B][A].getButton() == selectedSquare.getButton()) {
											squares[B][A].setPiece(null);
											squares[B][A].updateIcon();
										}
									}	
					
					
								}
							}
						}
					}	
				}
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					squares[x][y].updateIcon();
				}
		
			}	
			pieceClick = false;
		
			}
		}	
	}
	//No error checking yet. Will be set up when the communicator is finished.
	public void clientConSet(JFrame Frame) {
		
		ip = (String)JOptionPane.showInputDialog(Frame, "Please Enter Host IP");
		System.out.println("Entered IP: " + ip);
		port = (String)JOptionPane.showInputDialog(Frame, "Please Enter Desired Port");
		System.out.println("Entered Port: " + port);
		
	}
	
	public void hostConSet(JFrame Frame) {
		
		String mes = "Your IP:";
		
		
		try {
			ip = InetAddress.getLocalHost().toString();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mes = mes.concat(ip);
		System.out.println(mes);
		JOptionPane.showMessageDialog(Frame, mes );
		
		port = (String)JOptionPane.showInputDialog(Frame, "Please Enter Desired Port");
		System.out.println("Entered Port: " + port);
		
		try {
			com.hostConnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void launchPrompt(JFrame Frame) {
		Object[] options = {"Host", "Client"};
		String s = (String) JOptionPane.showInputDialog(Frame, "Select connection type", "Connection Type"
				, JOptionPane.PLAIN_MESSAGE, null, options, "Host");
		
		if (s == null) { launchPrompt(Frame); }
		
		if (s == "Host") {
			hostConSet(Frame);
		}
		
		if (s == "Client") {
			clientConSet(Frame);
		}
		if (s != null && s != "Host" && s != "Client") {
			JOptionPane.showMessageDialog(Frame, "---ERROR: User managed to break launchPrompt---");
		}
		
	}
		
	
	
	public String getIP() {
//ip = "10.0.0.60";
		return ip;
	}
	
	public String getPort() {
//port = "1234";
		return port;
	}
	 

	public void submit(char t) {
//		gui.updateTurn(t);
		com.sendMessage();
	}
	public void actionPerformed(ActionEvent e) {
		
	}
}
