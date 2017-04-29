package src;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Game {
	Piece wPieces[] = new Piece[16];
	Piece bPieces[] = new Piece[16];
	Piece pList[];
	private Board cB;
	JPanel gui = new JPanel();
	char playerSide;
	Dimension d = new Dimension(0,0);
	int minX;
	int minY;
	
	public Game() throws IOException {
		
	}
	
	public void gameSetUp() {
		System.out.println("gameSetUp Start");
		gameGUI();
		create_pList(cB);
		set_wPieces();
		set_bPieces();
	}
	
	private void gameGUI() {
		Board b = new Board();
		cB = b;
		JPanel board = b.getChessBoard();
		board.setMaximumSize(b.getMinSize());
		gui.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = (int) b.getSizeX();
		c.ipady = (int) b.getSizeY();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		gui.add(board, c);
		updateMinSize(b.getMinSize());
		System.out.println("dim X:" + d.getWidth() + " dim Y:" + d.getHeight());
		gui.setMinimumSize(d);
		
		JButton button = new JButton("Commit Move");
		button.setMinimumSize(new Dimension(80,40));
		c.fill = GridBagConstraints.NONE;
		c.ipady = 20;
		c.ipadx = 0;
		c.gridx = 2;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		gui.add(button, c);
		updateMinSize(button.getMinimumSize());
		
		System.out.println("MinX:" + minX + "   MinY: " + minY);
		updateMinSize(minX, minY);
		System.out.println("Size of Screen");
		System.out.println("dim X:" + d.getWidth() + " dim Y:" + d.getHeight());		
		
		
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
	
	
	
	
}
