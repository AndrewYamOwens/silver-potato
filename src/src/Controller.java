package src;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Controller extends JFrame {
	
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
	
	/*
	 * getter and setter block
	 */
	public void setGUI(GUI g) {
		this.gui = g;
	}
	
	public void setCommunicator(Communicator com) {
		this.com = com;
	}
	
	public String getIP() {
		return ip;
	}
	
	public String getPort() {
		return port;
	}
	
	/*launchPrompt is called in the communicator in method startConnection() 
	 * It is what is responsible for asking the user what connection type they want
	 * via popup boxes
	 */
	public void launchPrompt(JFrame Frame) {
		System.out.println("---launchPrompt Start--");
		System.out.println("---Controller, Line 56 ---");
		Object[] options = {"Host", "Client"};
		String s = (String) JOptionPane.showInputDialog(Frame, "Select connection type", "Connection Type"
				, JOptionPane.PLAIN_MESSAGE, null, options, "Host");
		
		if (s == null) { 
			JOptionPane.showMessageDialog(Frame, "Application Now Closing"); 
			System.exit(0);
		}
		
		if (s == "Host") {
			hostConSet(Frame);
		}
		
		if (s == "Client") {
			clientConSet(Frame);
		}
		if (s != null && s != "Host" && s != "Client") {
			JOptionPane.showMessageDialog(Frame, "---ERROR: User managed to break launchPrompt---");
		}
		System.out.println("---launchPrompt end--");
	}
	
	/*getConSetting is what is called when the user clicks on the connection drop down menu 
	 * and then clicks settings. It uses two popup boxes to display the host's IP and the port
	 * that is being used to communicate with
	 */
	public void getConSetting(JFrame Frame) {
		System.out.println("---getConSetting Start--");
		System.out.println("---Controller, Line 85 ---");
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
		
		mes = "Port Used: ";
		mes = mes.concat(port);
		JOptionPane.showMessageDialog(Frame, mes);
		
		System.out.println("---getConSetting End--");
			
	}
	
	/*buttonClick is the method that is called when the user clicks on one of the chess board squares.
	 * The first thing it does is check if a piece has been selected already using the pieceClick boolean.
	 * Important values in this, pieceClick = if a piece has already been selected
	 * selectedSquare = the square that the selected piece is in.
	 * P = a piece that is used to represent the selected piece.
	 * buttonClick is responsible for checking that the move the player is attempting is a valid one and
	 * if it is will update the piece list. It is also what calls the method that changes the selected pieces squares color.
	 */
	public void buttonClick(Square[][] squares, ActionEvent e, char curTurn, Piece[] pieceList) {
		System.out.println("---button Click Start---");
		System.out.println("---Controller, Line 119 ---");
		JButton b = (JButton) e.getSource();
		boolean vM = false;
		if (pieceClick == false && b.getIcon() != null) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) { 
					if (squares[i][j].getButton() == e.getSource()) {
						squares[i][j].toggleSelected();
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
				//loop through the squares 2d array
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) { 
						//if the current square in the loop is the one that triggered the actionEvent
						if (squares[i][j].getButton() == e.getSource()) {
							System.out.println("HasMoved: " + P.getHasMoved());
							P.setdestS(squares[i][j]);
							P.setdestX(j);
							P.setdestY(i);
							vM = P.checkMove(pieceList);
							System.out.println("Move check:" + vM);
							System.out.println("Piece type:" + P.getId());
							if(vM == true) {
								//If the move was a valid one it gets here.
								selectedSquare.toggleSelected();
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
	
	/*Is called when host is selected as the connection option upon startup. 
	 * It sets the ip to the local ip
	 * It also gets the port number from the user. If no port is entered it will be set to
	 * 1234 in Communicators hostConnect
	 */
	public void hostConSet(JFrame Frame) {
		System.out.println("---hostConSet Start--");
		System.out.println("---Controller, Line 210 ---");
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
		com.setPort(port);
		try {
			com.hostConnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/*The client equivalent of hostConSet, It displays two popup boxes that the user can enter 
	 * the desired ip and port into. If he user does not enter anything then it will be set to the
	 * local host and to port 1234
	 * 
	 */
		
	public void clientConSet(JFrame Frame) {
		System.out.println("---clientConSet Start--");
		System.out.println("---Controller, Line 248 ---");
		ip = (String)JOptionPane.showInputDialog(Frame, "Please Enter Host IP");
		System.out.println("Entered IP: " + ip);
		port = (String)JOptionPane.showInputDialog(Frame, "Please Enter Desired Port");
		System.out.println("Entered Port: " + port);
		
		com.setIP(ip);
		com.setPort(port);
		
		try {
			com.clientConnect();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/*The method that is called when the submit button is clicked. It simply calls sendMessage.
	 * 
	 */
	public void submit(char t) {
		System.out.println("---submit Start---");
		System.out.println("---Controller, Line 274 ---");
		com.sendMessage();

		System.out.println("---submit End ---");
	}

}
