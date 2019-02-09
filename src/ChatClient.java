import java.rmi.*;
import java.rmi.server.*; 
import java.rmi.registry.*;
import java.util.Scanner;
import java.util.regex.*;
import java.io.*;

public class ChatClient implements Client_itf{

	private String name;
	private String color;

	public static void main(String [] args){
		try {
		  	if (args.length < 1) {
		  		System.out.println("Usage: java HelloClient <rmiregistry host>");
		   		return;
			}

			String host = args[0];

			Registry registry = LocateRegistry.getRegistry(host);

			//Creation de la reference client
			ChatClient c = new ChatClient();
			Client_itf c_stub = (Client_itf) UnicastRemoteObject.exportObject(c, 0);

			//Recuperation des services serveur
			Chat chat = (Chat) registry.lookup("ChatService");
			RegistryClients reg = (RegistryClients) registry.lookup("RegistryService");

			System.out.println("** Bonjour, quel est votre nom ? (N'utilisez que des caractères alphabétiques) **");
			Scanner sc = new Scanner(System.in);

			boolean registered = false;
			
			//Verification du nom
			String name = "";
			boolean onlyAlphabet = false;
			while(!registered){
				name = sc.nextLine();
				onlyAlphabet = name.matches("^[a-zA-Z]*$");
				while(name.equals("") || !onlyAlphabet ){
					System.out.println("** Erreur : Ce nom n'est pas autorisé, veuillez en entrer un nouveau (uniquement composé de lettres) **");
					name = sc.nextLine();
					onlyAlphabet = name.matches("^[a-zA-Z]*$");
				}
				c.setName(name);
				//Enregistrement au registre
				registered = reg.register(c_stub);
			}
			System.out.println("** Commandes disponibles : **\n" + 
				"/list : Affiche la liste des personnes présentes\n" +
				"/history : Affiche l'historique des conversations\n" +
				"/color : Change la couleur de votre texte\n" +
				"/quit : Quitte le chat\n" +
				"** - - - - - - - - - - - - **");
			String message = sc.nextLine();
			//Effacement de l'entrée faite par l'utilisateur (plus de doublon)
			System.out.print(String.format("\033[%dA", 1)); 
			System.out.print("\033[2K");
			while(!message.equals("/quit")){
				if(message.equals("/list")){
					//Affichage de la liste des personnes connectees
					System.out.println("** Personne(s) présente(s) **");
					for(int i = 0 ; i < reg.getClients().size() ; i++){
						System.out.println(reg.getClients().get(i).getName());
					}
					System.out.println("** - - - - - - - - - - - - **");
				} else if(message.equals("/history")) {
					//Affichage de l'historique
					System.out.println(chat.loadHistory());
					System.out.print(ColorString.ANSI_RESET);
				} else if(message.startsWith("/color")) {
					//Changement de la couleur par l'utilisateur
					if(message.equals("/color")){
						System.out.println("** Utilisation : /color <Couleur> **\n** Couleurs disponibles : default, red, green, yellow, blue, purple, cyan **");
					} else if(message.equals("/color default")){
						c.setColor(ColorString.ANSI_RESET);
					} else if(message.equals("/color red")){
						c.setColor(ColorString.ANSI_RED);
					} else if(message.equals("/color green")){
						c.setColor(ColorString.ANSI_GREEN);
					} else if(message.equals("/color yellow")){
						c.setColor(ColorString.ANSI_YELLOW);
					} else if(message.equals("/color blue")){
						c.setColor(ColorString.ANSI_BLUE);
					} else if(message.equals("/color purple")){
						c.setColor(ColorString.ANSI_PURPLE);
					} else if(message.equals("/color cyan")){
						c.setColor(ColorString.ANSI_CYAN);
					} else {
						System.out.println("Couleur non reconnue");
					}
				}else{
					chat.publish(reg, c.getColor() + c.getName() + " : " + message);
					System.out.print(ColorString.ANSI_RESET);
				}
				message = sc.nextLine();
				//Effacement de l'entrée faite par l'utilisateur (plus de doublon)
				System.out.print(String.format("\033[%dA",1)); 
				System.out.print("\033[2K");
			}
			//Depart de l'utilisateur
			reg.unregister(c_stub);
			System.out.println("** Fin du chat, miaou ! **");
			System.exit(0);

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
		System.out.println(message);
		System.out.print(ColorString.ANSI_RESET);
	}

	public void setColor(String color) throws RemoteException {
		this.color = color;
	}

	public String getColor() throws RemoteException {
		return color;
	}
}