package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.border.*;


public class GUI implements ActionListener {
	private final JPanel gui = new JPanel(new BorderLayout(3,3));
	protected JFrame Frame = new JFrame("Socket Chess");
	protected JMenuBar menu;
	protected JRadioButtonMenuItem host, client;
	private Game g;
	private Communicator c;
	private Controller con;
	// Look I changed Something
	// Even more changes
	// EVEN MORE CHANGES
	
	public GUI() {
		
	}
	
	public void startGUI() throws IOException {
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g = new Game(Frame, c, con);
		Frame.setLayout(new GridLayout(1,1));
		gui.setBorder(new EmptyBorder(5,5,5,5));
		JMenuBar menu = new JMenuBar();
		
		
		
		
		
		JMenu chat = new JMenu("Chat Options");
		menu.add(chat);
		
		JMenu conMenu = new JMenu("Connection"); 
		menu.add(conMenu);
		
		JRadioButtonMenuItem host = new JRadioButtonMenuItem("Host");
		host.setActionCommand("host");
		chat.add(host);
		
		JRadioButtonMenuItem client = new JRadioButtonMenuItem("Client");
		client.setActionCommand("client");
		chat.add(client);
		
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
	
	
	
	public Board getBoard() {
		return g.getBoard();
	}
	
	private void addCommunicator(Communicator c) {
		this.c = c;
	}
	private void setController(Controller con) {
		this.con = con;
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action == "conSet") {
			con.conSet(Frame);
		}
		
		
		
		
	}
	
	public static void main(String[] args) throws IOException {
		GUI gu = new GUI();
		Communicator com = new Communicator();
		Controller con = new Controller();
		
		
		gu.addCommunicator(com);
		gu.setController(con);
		
		com.addGUI(gu);
		com.setController(con);
		
		con.setCommunicator(com);
		con.setGUI(gu);
		
		gu.startGUI();
		//System.out.println("2");

		//System.out.println("3");
//		gu.getGUI());
		}
}



