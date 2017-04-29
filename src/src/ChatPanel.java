package src;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPanel {
	static ServerSocket chatHost;
	static Socket socket;
	private JPanel chatPanel = new JPanel(new GridBagLayout());
	
	public ChatPanel() {
		
	}
	
	public void createChat(GridBagConstraints c) throws IOException {
		
		JTextArea Hist = new JTextArea();
		Hist.setBounds(20, 20, 450, 360);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.gridwidth = 3;
		c.ipady = 360;
		c.ipadx = 450;
		chatPanel.add(Hist, c);
		
		JTextField newMsg = new JTextField();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridheight = 3;
		c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 0;
		chatPanel.add(newMsg, c);
		
		JButton Send = new JButton("Send");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;
		c.ipadx = 50;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 4;
		
		chatPanel.add(Send, c);
		
		
		Hist.setText("Waiting for Client");
		socket = chatHost.accept();
		Hist.setText(Hist.getText() + '\n' + "Client Found");
		
		
		
		
		
		
		while (true) {
			try {
					DataInputStream is = new DataInputStream(socket.getInputStream());
					String string = is.readUTF();
					Hist.setText(Hist.getText() + '\n' + "Client:" + string);
			} catch (Exception e1) {
				Hist.setText(Hist.getText() + '\n' + "Message sending fail:Network Error");
			
				try {
					Thread.sleep(3000);
					System.exit(0);
				} catch (InterruptedException e) {
					// TODO Auto-Generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
}
