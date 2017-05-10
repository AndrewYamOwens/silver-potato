package src;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JFrame implements ActionListener { 

	private static final long serialVersionUID = 1L;
	private static Square[][] squares = new Square[8][8];
	private JPanel chessBoard;
	protected boolean pieceClick = false;
	protected Square selectedPieceLocation;
	JFrame Frame;
	protected JMenuBar menu;
	protected JMenu chat;
	protected JRadioButtonMenuItem host, client;
	static ServerSocket chatHost;
	static Socket socket;
	public Piece[] pieceList = new Piece[32];
	private char curTurn = 'w';
	Dimension dim = new Dimension(0,0);
	Controller con;
	Communicator com;
	
	
	public Board(JFrame Frame, Controller con, Communicator com){
		this.Frame = Frame;
		this.con = con;
		this.com = com;
		initialize();
	}
	
	/*Getter and Setter Block
	 * All functions are pretty self explanatory
	 */
	public void setTurn(char t) {
		curTurn = t;
	}
	
	public char getTurn() {
		return curTurn;	
	}
	
	public Piece[] getPL() {
		return pieceList;
	}
	
	public Dimension getMinSize() {
		return dim;
	}
	
	public double getSizeX() {
		return dim.getWidth();
	}
	public double getSizeY() {
		return dim.getHeight();
	}
	public JPanel getChessBoard() {
		return chessBoard;
	}
	
	/**
	 * Initializes the board. Creates and sizes the JPanel and then calls buttonSetUp
	 * 
	 */
	public final void initialize()   {
		System.out.println("---initialize Start ---");
		System.out.println("---Board, Line 40 ---");
		chessBoard = new JPanel(new GridLayout(8,8));
		chessBoard.setPreferredSize(new Dimension(480 , 480));
		buttonSetUp();
		
		
	}	
		
	/**
	 * buttonSetUp creates the 8x8 grid of buttons that make up the chess board.
	 * it sets gives all buttons the action command "button"
	 * it then uses the buttons as they are created and one at a time adds sets the equal to a values in the
	 * 2d array of squares. This square list serves as the orgainizer of all the buttons into locations on the
	 * chess board. Next it calls pieceSetUP. After pieceSetUp has done its thing it will add all the buttons to the
	 * JPanel via the squares 2d array. Finally it sizes the JPanel so that it will be correctly sized overall.
	 * 
	 */
	private void buttonSetUp() {
		System.out.println("---buttonSetUp Start ---");
		System.out.println("---Board, Line 51 ---");
			Dimension max = new Dimension(60, 60);
			char color;
			for (int i = 0; i < squares.length; i++) {
				for (int j = 0; j < squares[i].length; j++) {
					JButton b = new JButton();
					b.addActionListener(this);
					b.setActionCommand("button");
					b.setPreferredSize(max);
					System.out.println("Button Height:" + b.getHeight());
					if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
						b.setBackground(Color.WHITE);
						color = 'w';
					} else {
						b.setBackground(Color.GRAY);
						color = 'b';
					}
					Square squ = new Square(b, j, i, color);
					squares[i][j] = squ;
				}
				updateMinSize(dim.getHeight() + 61, dim.getWidth() + 61);
	 		}
			
			pieceSetup();
			
			System.out.println("dim X:" + dim.getWidth() + " dim Y:" + dim.getHeight());
			for (int i = 0; i < squares.length; i++) {
				for (int j = 0; j < squares[i].length; j++) {
					chessBoard.add(squares[i][j].getButton());
				}
			}	
			chessBoard.setPreferredSize(dim);
			
			chessBoard.setMinimumSize(dim);

		}
	
/*Piece list values
 * White King:0
 * White Queen:1
 * White Rooks: 2 & 3
 * White Knights: 4 & 5
 * White King Side Bishop: 6
 * White Queen Side Bishop: 7
 * White Pawns: 8 - 15
 * 
 * Black King: 16
 * Black Queen: 17
 * Black Rooks: 18 & 19
 * Black Knights: 20 & 21
 * Black King Side Bishop: 22
 * Black Queen Side Bishop: 23
 * Black Pawns: 24 - 31
 */
	/**
	 * Piece setup creates all the individual pieces and assigns them to their starting squares.
	 * Each piece is also given an ID which is primarily used for error testing and to identify specific pieces.
	 * Finally it goes through the square list and sets all locations where pieces were added to have occupied=true
	 * 
	 */
	
	public void pieceSetup() {
		System.out.println("---pieceSetup Start ---");
		System.out.println("---Board, Line 106 ---");
		Piece p;
		KingMove km = new KingMove();
		PawnMove pm = new PawnMove();
		QueenMove qm = new QueenMove();
		KnightMove nm = new KnightMove();
		RookMove rm = new RookMove();
		BishopMove bm = new BishopMove();
		int offset = 8;
		//setup white pieces minus pawns
		p = new King(0,3, 'w', km, 0);
		squares[0][3].setPiece(p);
		
		p = new Queen(0,4,'w', qm, 1);
		squares[0][4].setPiece(p);
		
		
		p = new Rook(0,0,'w', rm, 2);
		squares[0][0].setPiece(p);
		
		p = new Rook(0,7,'w', rm, 3);
		squares[0][7].setPiece(p);
		
		
		p = new Knight(0,1,'w', nm, 4);
		squares[0][1].setPiece(p);
		
		
		p = new Knight(0,6,'w', nm, 5);
		squares[0][6].setPiece(p);
		
		
		p = new Bishop(0,2,'w', bm, 6);
		squares[0][2].setPiece(p);
		
		
		p = new Bishop(0,5,'w', bm, 7);
		squares[0][5].setPiece(p);
		
		
		
		for (int x = 0; x < 8; x++) {
			offset = 8 + x;
			p = new Pawn(1,x, 'w', pm, offset);
			squares[1][x].setPiece(p);
			
		}
		
		
		// Set up black pieces minus pawns
		p = new King(7,3, 'b', km, 16);
		squares[7][3].setPiece(p);
		
		
		p = new Queen(7,4,'b', qm, 17);
		squares[7][4].setPiece(p);
		
		
		p = new Rook(7,0,'b', rm, 18);
		squares[7][0].setPiece(p);
		
		
		p = new Rook(7,7,'b', rm, 19);
		squares[7][7].setPiece(p);
		
		
		p = new Knight(7,1,'b', nm, 20);
		squares[7][1].setPiece(p);
		
		
		p = new Knight(7,6,'b', nm, 21);
		squares[7][6].setPiece(p);
		
		p = new Bishop(7,2,'b', bm, 22);
		squares[7][2].setPiece(p);
		
		
		p = new Bishop(7,5,'b', bm, 23);
		squares[7][5].setPiece(p);
		
		
		
		// Set up both sides pawns
		for (int x = 0; x < 8; x++) {
			offset = 24 + x;
			p = new Pawn(6,x, 'b', pm, offset);
			squares[6][x].setPiece(p);
			
		}
		
		//Set squares.occupied = true for all squares now containing pieces 
		int i = 0;
		for (int x = 0; x < 8; x++) {
			for (int Y = 0; Y < 8; Y++) {
				if (squares[x][Y].getOccupied() == true) {
					pieceList[i] = squares[x][Y].getPiece();
					i++;
				}
			}
		}
		
		
		drawBoard();
		
	
		
		
		System.out.println("Piece Setup End");
	}
	
	/**
	 * drawBoard is the method used to update the icons of all of the buttons in the square 2d array
	 * 
	 */
	private void drawBoard() {
		System.out.println("---drawBoard Start ---");
		System.out.println("---Board, Line 218 ---");
		for (int x = 0; x < 8; x++) {
			for (int Y = 0; Y < 8; Y++) {
				squares[x][Y].updateIcon();
			}
		}
	}
	

						
	/**
	 * updateBoard first gets an array of pieces. These arrays are what is passed between the host and client and it is
	 * what allows stores the moves that have been made. The first thing updateBoard does is to wipe away all the pieces from the board
	 * next it loops through the array of pieces pL and sets the square at the pieces location so that it now has the correct piece in
	 * it. Then it calls drawBoard again to redraw the updated board. 
	 * 
	 */
	
	public void updateBoard(Piece[] pL) {
		System.out.println("---updateBoard Start ---");
		System.out.println("---Board, Line 282 ---");
		pieceList = pL;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j].setPiece(null);
			}
		}
		
		System.out.println("----------- PieceList piece locations -----------");
		for (int x = 0; x < 32; x++) {
			System.out.print(pieceList[x].getId() + ":(" + pieceList[x].getX() + "," + pieceList[x].getY() + ") ");
		}
		System.out.println("");
		
		int X;
		int Y;
		
		for (int x = 0; x < 32; x++) {
			
			X = pieceList[x].getX();
			Y = pieceList[x].getY();
			System.out.println("PieceList X: " + X + "   Y: " + Y);
			if (X != 9 && Y != 9) {
				squares[Y][X].setPiece(pieceList[x]);
			}
		}
		
		drawBoard();
	}
	
	/**
	 * updateTurn is the main method used to track and switch between turns after someone makes a move.
	 * 
	 */
	public void updateTurn() {
		System.out.println("---updateTurn Start ---");
		System.out.println("---Board, Line 318 ---");
		if (curTurn == 'w') {
			curTurn = 'b';
		} else {
			curTurn = 'w';
		}
	}
	

	/**
	 * updates the minimum size that the chess board can be displayed at. 
	 * Is called during board setup after every row of buttons is created
	 */
	private void updateMinSize(double d, double e) {
		System.out.println("---updateMinSize Start ---");
		System.out.println("---Board, Line 332 ---");
		dim.setSize(d, e);
	}
	
	/**
	 * printPL prints the location of all the pieces on the current array pL
	 * It is primarily used for more convenient debugging given this is something
	 * the user will never really care about.
	 */
	public void printPL() {
		System.out.println("---printPL Start ---");
		System.out.println("---Board, Line 342 ---");
		System.out.println("----------- PieceList piece locations -----------");
		for (int x = 0; x < 32; x++) {
			System.out.print(pieceList[x].getId() + ":(" + pieceList[x].getX() + "," + pieceList[x].getY() + ") ");
		}
		System.out.println("");
	}
	

	/**
	 * actionPerformed takes any action listener prompts for components created in Boards methods
	 * and checks the action command. It then calls the corresponding method in Controller
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println("---actionPerformed Start ---");
		System.out.println("---Board, Line 356 ---");
		String action = e.getActionCommand();
		System.out.println("Action: " + action);
		if (action.equals("button")) {
			con.buttonClick(squares, e, curTurn, pieceList);
		}

	}


}	
