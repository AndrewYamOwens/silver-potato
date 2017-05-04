package src;

	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.net.*;

	 

	import javax.swing.*;


	public class Host extends JFrame implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		static ServerSocket host;
		static Socket soc;
		JPanel panel;
		JTextField newMsg;
		JTextArea Hist;
		JButton Send;
		DataInputStream is;
		DataOutputStream os;
		
		public Host() throws UnknownHostException, IOException {
			
			panel = new JPanel();
			newMsg = new JTextField();
			Hist = new JTextArea();
			Send = new JButton("Send");
			this.setSize(500,500);
			this.setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			panel.setLayout(null);
			this.add(panel);
			Hist.setBounds(20, 20, 450, 360);
			panel.add(Hist, null);
			newMsg.setBounds(20, 400, 340, 30);
			panel.add(newMsg);
			Send.setBounds(375, 400, 95, 30);
			panel.add(Send, null);
			this.setTitle("Host");
			Send.addActionListener(this);
			host = new ServerSocket(2000, 1, InetAddress.getLocalHost());
			Hist.setText("Waiting for Client");
			soc = host.accept();
			Hist.setText(Hist.getText() + '\n' + "Client Found");
			
			while (true) {
				try {
						DataInputStream is = new DataInputStream(soc.getInputStream());
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
		
		public void actionPerformed(ActionEvent e) {
			
			if ((e.getSource() == Send) && (newMsg.getText() != "")) {
				Hist.setText(Hist.getText() + '\n' + "ME:" + newMsg.getText());
				try {
					DataOutputStream os = new DataOutputStream(soc.getOutputStream());
					os.writeUTF(newMsg.getText());
				} catch (Exception e1) {
					try {
						Thread.sleep(3000);
						System.exit(0);
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
				}
				newMsg.setText("");
			}
		}
		
	}

