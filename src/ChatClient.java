import java.rmi.*;
import java.rmi.server.*; 
import java.rmi.registry.*;
import java.util.Scanner;
import java.util.regex.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatClient implements Client_itf{

	private String name;
	private String color;
	private NameInputFrame nameFrame;
	private InterfaceClient chatFrame;

	public static void main(String [] args){
		try {
		  	if (args.length < 1) {
		  		System.out.println("Usage: java ChatClient <rmiregistry host>");
		   		return;
			}

			String host = args[0];

			Registry registry = LocateRegistry.getRegistry(host);

			//Creation de la reference client
			final ChatClient c = new ChatClient();
			final Client_itf c_stub = (Client_itf) UnicastRemoteObject.exportObject(c, 0);

			//Recuperation des services serveur
			final Chat chat = (Chat) registry.lookup("ChatService");
			final RegistryClients reg = (RegistryClients) registry.lookup("RegistryService");

			//Creation des fenetres d'entree du nom & de chat
			c.setNameFrame(new NameInputFrame());
			c.setChatFrame(new InterfaceClient());
			final JTextField nameField = c.getNameFrame().getNameField();
			final JTextField outputServer = c.getNameFrame().getOutputServer();
			final JButton validateButton = c.getNameFrame().getValidateButton();
			

			//Ajout des listeners
			validateButton.addActionListener(new ActionListener() { 
  				public void actionPerformed(ActionEvent e) { 
  					String nameEntry;
					boolean onlyAlphabet;
    				nameEntry = nameField.getText();
    				onlyAlphabet = nameEntry.matches("^[a-zA-Z]*$");
    				try {
    					if (nameEntry.equals("") || !onlyAlphabet ){
							c.getNameFrame().setSize(700,100);
							c.getNameFrame().setLocationRelativeTo(null);
							outputServer.setText("Erreur : Ce nom n'est pas autorisé, veuillez en entrer un nouveau (uniquement composé de lettres)");
						} else {
							c.setName(nameEntry);
							if (reg.register(c_stub)){
								c.getNameFrame().setVisible(false);
								c.getChatFrame().setVisible(true);
							}							
						}
    				} catch (Exception exc)  {
						System.err.println("Error on client: " + exc);
					}					
  				} 
			} );
			
			final String commandes = "** Commandes disponibles : **\n" + 
				"/history : Affiche l'historique des conversations\n" +
				"/w <Nom de la cible> <Message> : Envoie un message privé à la cible\n" +
				"/help : Affiche la liste des commandes\n" +
				"/quit : Quitte le chat\n";
			c.receive(commandes);
			
			final JButton send = c.getChatFrame().getSendButton();
			final JTextField messageField = c.getChatFrame().getInputField();

			send.addActionListener(new ActionListener() { 
  				public void actionPerformed(ActionEvent e) { 
  					try {
  						String message;
  						message = messageField.getText();

  						if(!message.equals("/quit")){
							if(message.equals("/history")) {
								//Affichage de l'historique
								c.addChatField(chat.loadHistory());
							} else if (message.equals("/help")) {
								c.addChatField(commandes);
							} else if (message.startsWith("/w")) {
								//Message prive
								Scanner scString = new Scanner(message);
								if (message.equals("/w")) {
									c.addChatField("** Utilisation : /w <Nom de la cible> <Message> **");
								} else {
									scString.next();
									if (scString.hasNext()){
										String target = scString.next();
										if (scString.hasNext()){
											String messageWhisp = scString.nextLine();
											chat.whisper(reg, c_stub, target, messageWhisp);
										} else {
											c.addChatField("** Utilisation : /w <Nom de la cible> <Message> **");
										}
									} else {
										c.addChatField("** Utilisation : /w <Nom de la cible> <Message> **");
									}	
								}
							} else if (message.startsWith("/")) {
								c.addChatField("** Commande non reconnue, tapez /help pour les commandes disponibles **");
							} else if (message.equals("")) {
								
							} else {
								chat.publish(reg, c.getName() + " : " + message);
							}
						} else {
							reg.unregister(c_stub);
							System.exit(0);
						}
  					} catch (Exception exc) {
  						System.err.println("Error on client: " + exc);
  					}					
  				} 
			} );			

			messageField.addMouseListener(new MouseAdapter(){
	            @Override
	            public void mouseClicked(MouseEvent e){
	                if(messageField.getText().equals("Entrez votre message")){
	                	messageField.setText("");
	                }
	            }
	        });

	        messageField.addKeyListener(new KeyListener(){
	        	public void keyPressed(KeyEvent e){
	        		
	        	}

	        	public void keyReleased(KeyEvent e){
	        		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
	        			messageField.setText("");
	        		}
	        	}

	        	public void keyTyped(KeyEvent e){
	        		
	        	}
	        });

	        JTextArea usersList = c.getChatFrame().getUsersField();


		} catch (Exception e)  {
			System.err.println("Error on client: " + e);
		}
	}

	public String getName() throws RemoteException {
		return this.name;
	}

	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	public void receive(String message) throws RemoteException {
		addChatField(message);
	}

	public void setColor(String color) throws RemoteException {
		this.color = color;
	}

	public String getColor() throws RemoteException {
		return color;
	}

	public JTextField getNameField() throws RemoteException {
		return nameFrame.getNameField();
	}

	public void setNameFrame(NameInputFrame nameFrame) {
		this.nameFrame = nameFrame;
	}

	public NameInputFrame getNameFrame() {
		return this.nameFrame;
	}

	public JTextArea getChatField() throws RemoteException {
		JTextArea chat = this.chatFrame.getChatField();
		return chat;
	}

	public void setChatFrame(InterfaceClient chatFrame) {
		this.chatFrame = chatFrame;
	}

	public InterfaceClient getChatFrame() {
		return this.chatFrame;
	}

	public void addChatField(String s) throws RemoteException {
		JTextArea chat = this.getChatField();
		chat.append(s + "\n");
	}

	public void setUsersField(String s) throws RemoteException {
		JTextArea users = this.chatFrame.getUsersField();
		users.setText(s);
	}

	public void setOutputServer(String output) throws RemoteException {
		this.nameFrame.getOutputServer().setText(output);
	}

	public void setNameFrameSize(int width, int length) throws RemoteException {
		this.nameFrame.setSize(width, length);
	}
}