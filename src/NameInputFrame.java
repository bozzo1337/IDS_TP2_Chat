import javax.swing.*;
import java.awt.*;

public class NameInputFrame extends JFrame {

	private JPanel pane;
	private JPanel paneBottom;
	private LayoutManager layout;
	private LayoutManager layoutBottom;
	private JButton validate;
	private JTextField nameField;
	private JTextField outputServer;
	
	public NameInputFrame(){
		this.setTitle("Chat");
		this.setSize(400, 100);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    layout = new BorderLayout();
	    pane = new JPanel(layout);
	    layoutBottom = new BorderLayout();
	    paneBottom = new JPanel(layoutBottom);
	    nameField = new JTextField();
	    outputServer = new JTextField("Bonjour, quel est votre nom ?");
	    outputServer.setHorizontalAlignment(JTextField.CENTER);
	    outputServer.setEditable(false);
	    validate = new JButton("Valider");
	    paneBottom.add(nameField, BorderLayout.CENTER);
	    paneBottom.add(validate, BorderLayout.LINE_END);
	    pane.add(outputServer, BorderLayout.CENTER);
	    pane.add(paneBottom, BorderLayout.PAGE_END);
	    this.getRootPane().setDefaultButton(validate);
	    this.getContentPane().add(pane);
	    this.setVisible(true);
	    nameField.requestFocus();
	}

	public JTextField getNameField(){
		return nameField;
	}

	public JButton getValidateButton(){
		return validate;
	}

	public JTextField getOutputServer(){
		return outputServer;
	}
}