package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.border.*;


public class GUI implements ActionListener {
	private final JPanel gui = new JPanel(new BorderLayout(3,3));
	protected JFrame Frame = new JFrame("Socket Chess - Client");
	protected JMenuBar menu;
	protected JRadioButtonMenuItem host, client;
	private Game g;
	private Communicator c;
	private Controller con;
	
	public GUI() {
		
	}
	
	public Game getGame() {
		return g;
	}
	
	public Board getBoard() {
		return g.getBoard();
	}
	
	public Piece[] getPieceList() {
		return g.getPList();
	}
	
	public JFrame getFrame() {
		return Frame;
	}
	
	private void setCommunicator(Communicator c) {
		this.c = c;
	}
	private void setController(Controller con) {
		this.con = con;
	}
	/**
	 *  Sets up the initial GUI. It uses JFrame and JPanels. This is also where the JMenuBar is Set UP.
	 * The menu bar only has the option to get the current connection settings.
	 * 
	 */
	public void startGUI() throws IOException {
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g = new Game(Frame, c, con);
		Frame.setLayout(new GridLayout(1,1));
		gui.setBorder(new EmptyBorder(5,5,5,5));
		JMenuBar menu = new JMenuBar();
		
		
		JMenu conMenu = new JMenu("Connection"); 
		menu.add(conMenu);

		
		JMenuItem conSet = new JMenuItem("Settings");
		conSet.addActionListener(this);
		conSet.setActionCommand("conSet");
		conMenu.add(conSet);
		
		Frame.setJMenuBar(menu);
		
		g.gameSetUp();
		Frame.add(g.getGameGui());
		System.out.println("Board min size:" + g.getMinSize());
		Dimension d = g.getMinSize();
		System.out.println("Frame Width:" + d.getWidth() + "    Frame Height:" + d.getHeight());
		Frame.pack();
		Frame.setPreferredSize(d);
		Frame.setMinimumSize(d);
		Frame.setVisible(true);
		

	}
	
	
	
	/*GUI updateBoard is used to pass the call to update the board down to game. 
	 * Game will in turn pass it to to the board
	 */
	
	public void updateBoard(Piece[] pL) {
		g.updateBoard(pL);
	}
	
	/* Passes on the command to update the turn to the board.
	 * 
	 */
	public void updateTurn(char t) {
		g.updateTLabel(t);
	}
	
	/*the gui's command to update the board.
	 * 
	 */
	public void updateTurn() {
		if (g.getTurn() == 'w') {
			g.updateTurn('b');
		} else {
			g.updateTurn('w');
		}
	}
	
	
	/*
	 *GUI's iteration of actionPerformed for all components it set up. 
	 * 
	 */ 
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action == "conSet") {
			con.getConSetting(Frame);
		}
		
		
	}
	

	/*
	 * The main method
	 */
	public static void main(String[] args) throws IOException {
		GUI gu = new GUI();
		Communicator com = new Communicator();
		Controller con = new Controller();
		
		
		gu.setCommunicator(com);
		gu.setController(con);
		
		com.addGUI(gu);
		com.setController(con);
		
		con.setCommunicator(com);
		con.setGUI(gu);
		
		gu.startGUI();
		
		com.startConnection(gu.getFrame());
		
		

		}
}



