import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class InterfaceClient extends JFrame {

	private JPanel pane;
	private JPanel paneBottom;
	private LayoutManager layout;
	private LayoutManager layoutBottom;
	private JButton send;
	private JTextArea chat;
	private JTextField input;
	private JTextArea users;

	public InterfaceClient() {                
	    this.setTitle("Chat");
	    this.setSize(700, 600);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    layout = new BorderLayout();
	    layoutBottom = new BorderLayout();
	    pane = new JPanel(layout);
	    paneBottom = new JPanel(layoutBottom);
	    input = new JTextField("Entrez votre message");
	    chat = new JTextArea();
	    JScrollPane scrollPane = new JScrollPane(chat);
	    DefaultCaret caret = (DefaultCaret) chat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    chat.setEditable(false);
	    users = new JTextArea();
	    JScrollPane scrollUsers = new JScrollPane(users);
	    users.setColumns(10);
	    users.setEditable(false);
	    send = new JButton("Envoyer");
	    paneBottom.add(input, BorderLayout.CENTER);
	    paneBottom.add(send, BorderLayout.LINE_END);
	    pane.add(scrollPane, BorderLayout.CENTER);
	    pane.add(scrollUsers, BorderLayout.LINE_END);
	    pane.add(paneBottom, BorderLayout.PAGE_END);
	    this.getRootPane().setDefaultButton(send);
	    this.getContentPane().add(pane);
	    this.setVisible(false);
	}

	public JTextArea getChatField(){
		return chat;
	}

	public JTextField getInputField(){
		return input;
	}

	public JTextArea getUsersField(){
		return users;
	}

	public JButton getSendButton(){
		return send;
	}
}