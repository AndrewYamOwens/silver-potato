package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.border.*;


public class GUI {
	private final JPanel gui = new JPanel(new BorderLayout(3,3));
	private JPanel chatPanel = new JPanel(new GridBagLayout());
	private JPanel chessBoard;
	protected JFrame Frame = new JFrame("Socket Chess");
	protected JMenuBar menu;
	protected JRadioButtonMenuItem host, client;
	private Game g;
	private ChatPanel cP;
	
	
	public GUI() throws IOException {
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g = new Game();
		GridBagConstraints c = new GridBagConstraints();
		Frame.setLayout(new GridLayout(1,2));
		gui.setBorder(new EmptyBorder(5,5,5,5));
		JMenuBar menu = new JMenuBar();
		
		JMenu chat = new JMenu("Chat Options");
		menu.add(chat);
		
		JRadioButtonMenuItem host = new JRadioButtonMenuItem("Host");
		host.setActionCommand("host");
		chat.add(host);
		
		JRadioButtonMenuItem client = new JRadioButtonMenuItem("Client");
		client.setActionCommand("client");
		chat.add(client);
		
		Frame.setJMenuBar(menu);
		
		
		g.gameSetUp();
		chessBoard = g.getGameGui();
		Frame.add(chessBoard);
		
		Frame.setMinimumSize(g.getMinSize());
		Frame.setVisible(true);
		
		
		
		
	}
	
	
	private JPanel getGUI() {
		return gui;
	}
	
	public static void main(String[] args) throws IOException {
		GUI gu = new GUI();
		//System.out.println("2");
		JFrame window = new JFrame("Practice");
		//System.out.println("3");
		window.add(gu.getGUI());
		window.setVisible(true);
		}
}



