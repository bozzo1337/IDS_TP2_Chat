package nf;

import java.rmi.*;
import java.rmi.server.*; 
import java.rmi.registry.*;
import java.util.Scanner;

public class ChatClient implements Client_itf {

	private String name;
	private String color;

	public static void main(String [] args){
		try {
		  	if (args.length < 1) {
		  		System.out.println("Usage: java ChatClient <rmiregistry host>");
		   		return;
			}

			String host = args[0];

			

			boolean registered = false;
			
			//Verification du nom
			String nameEntry = "";
			boolean onlyAlphabet = false;
			while(!registered){
				nameEntry = sc.nextLine();
				onlyAlphabet = nameEntry.matches("^[a-zA-Z]*$");
				while(nameEntry.equals("") || !onlyAlphabet ){
					System.out.println("** Erreur : Ce nom n'est pas autorisé, veuillez en entrer un nouveau (uniquement composé de lettres) **");
					nameEntry = sc.nextLine();
					onlyAlphabet = nameEntry.matches("^[a-zA-Z]*$");
				}
				c.setName(nameEntry);
				//Enregistrement au registre
				registered = reg.register(c_stub);
			}
			String commandes = "** Commandes disponibles : **\n" + 
				"/list : Affiche la liste des personnes présentes\n" +
				"/history : Affiche l'historique des conversations\n" +
				"/color <Couleur> : Change la couleur de votre texte\n" +
				"/w <Nom de la cible> <Message> : Envoie un message privé à la cible\n" +
				"/help : Affiche la liste des commandes\n" +
				"/quit : Quitte le chat\n" +
				"** - - - - - - - - - - - - **";
			System.out.println(commandes);
			String message = sc.nextLine();
			//Effacement de l'entrée faite par l'utilisateur (plus de doublon)
			System.out.print(String.format("\033[%dA", 1)); 
			System.out.print("\033[2K");
			while(!message.equals("/quit")){
				if(message.equals("/history")) {
					//Affichage de l'historique
					System.out.println(chat.loadHistory());
					System.out.print(ColorString.ANSI_RESET);
				} else if (message.equals("/help")) {
					System.out.println(commandes);
				} else if (message.startsWith("/w")) {
					Scanner scString = new Scanner(message);
					if (message.equals("/w")) {
						System.out.println("** Utilisation : /w <Nom de la cible> <Message> **");
					} else {
						scString.next();
						if (scString.hasNext()){
							String target = scString.next();
							if (scString.hasNext()){
								String messageWhisp = scString.nextLine();
								chat.whisper(reg, c_stub, target, messageWhisp);
							} else {
								System.out.println("** Utilisation : /w <Nom de la cible> <Message> **");
							}
						} else {
							System.out.println("** Utilisation : /w <Nom de la cible> <Message> **");
						}	
					}
					//scString.sloce();
				} else if (message.startsWith("/")) {
					System.out.println("** Commande non reconnue, tapez /help pour les commandes disponibles **");
				} else {
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
			sc.close();
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