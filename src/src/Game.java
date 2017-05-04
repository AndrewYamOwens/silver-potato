package src;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game implements ActionListener {
	JFrame frame;
	Piece wPieces[] = new Piece[16];
	Piece bPieces[] = new Piece[16];
	Piece pList[];
	private Board cB;
	private JLabel l;
	JPanel gui = new JPanel();
	char playerSide;
	Dimension d = new Dimension(0,0);
	int minX;
	int minY;
	Communicator com;
	Controller con;
	char currentTurn;
	String turn = "Current Turn: White";
	
	public Game(JFrame frame, Communicator com, Controller con) throws IOException {
		this.frame = frame;
		this.com = com;
		this.con = con;
	}
	
	public void gameSetUp() {
		System.out.println("gameSetUp Start");
		gameGUI();
		create_pList(cB);
		set_wPieces();
		set_bPieces();
	}
	
	private void gameGUI() {
		Board b = new Board(frame, con, com);
		cB = b;
		JPanel board = b.getChessBoard();
		currentTurn = b.getTurn();
		
//		System.out.println("____________ dim X:" + board.getWidth() + " dim Y:" + board.getHeight() + "____________");
		board.setMaximumSize(b.getMinSize());
		gui.setLayout(new GridBagLayout());
		System.out.println("board X:" + board.getWidth() + " board Y:" + board.getHeight());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		gui.add(board, c);
		updateMinSize(b.getMinSize());
		System.out.println("dim X:" + d.getWidth() + " dim Y:" + d.getHeight());

		
		JButton button = new JButton("Commit Move");
		button.setMinimumSize(new Dimension(120,40));
		button.addActionListener(this);
		button.setActionCommand("submit");
		c.fill = GridBagConstraints.NONE;
		c.ipady = 10;
		c.ipadx = 0;
		c.gridx = 2;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		gui.add(button, c);
		updateMinSize(button.getMinimumSize());
		
		
		l = new JLabel();
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.PAGE_START;
		gui.add(l, c);
		updateTLabel(currentTurn);

		updateMinSize(minX, minY);
		System.out.println("Size of Screen");
		System.out.println("dim X:" + d.getWidth() + " dim Y:" + d.getHeight());		
		
		
	}
	
	public void updateTLabel(char t) {
		if (t == 'w') {
			turn = "Current Turn: White";
		} else {
			turn = "Current Turn: Black";
		}
		System.out.println("t: " + t);
		l.setText(turn);
		
	}
	
	private void create_pList(Board b) {
			pList = b.getPL();
	}
	
	private void set_wPieces() {
		int a = 0;
		for (int x = 0; x < 32; x++) {
			if (pList[x].getColor() == 'w') {
				wPieces[a] = pList[x];
				a++;
			}
		}
	}
	
	private void set_bPieces() {
		int a = 0;
		for (int x = 0; x < 32; x++) {
			if (pList[x].getColor() == 'w') {
				bPieces[a] = pList[x];
				a++;
			}
		}
	}
	
	public Board getBoard() {
		return cB;
	}
	
	public JPanel getGameGui() {
		System.out.println("dim X:" + d.getWidth() + " dim Y:" + d.getHeight());
		return gui;
	}
	
	public Dimension getMinSize() {
		gui.setMinimumSize(d);
		return d;
	}
	
	private void updateMinSize(double minX, double minY) {
		d.setSize(d.getWidth() + minX, d.getHeight() + minY);
	}
	
	private void updateMinSize(Dimension minD) {
		d.setSize(d.getWidth() + minD.getWidth(), d.getHeight() + minD.getHeight());
	}
	
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		
		if (s == "submit") {
			con.submit(currentTurn);
		}
	}
	
	
}
