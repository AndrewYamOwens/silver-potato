package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controller extends JFrame implements ActionListener {
	
	Board board;
	Square squares[][];
//	Square pieceClick;
	boolean pieceClick = false;
	Square selectedSquare;
	GUI gui;
	Communicator com;
	Piece P;
		
	
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
								board.updateTurn();
								System.out.println("Current Turn:" + curTurn);
								if (squares[i][j].getOccupied() == true) {
									for (int x = 0; x < 32; x++) {
										if (pieceList[x].checkLocation(squares[i][j].getPiece()) == true) {
											pieceList[x].setCaptured(true);
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
	public void conSet(JFrame Frame) {
		
		String ip = (String)JOptionPane.showInputDialog(Frame, "Please Enter Desired IP");
		System.out.println("Entered IP: " + ip);
		String port = (String)JOptionPane.showInputDialog(Frame, "Please Enter Desired Port");
		System.out.println("Entered Port: " + port);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
