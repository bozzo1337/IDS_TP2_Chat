import java.rmi.*;
import java.util.*;
import javax.swing.*;

public class RegistryClientsImpl implements RegistryClients {

	private ArrayList<Client_itf> clients;

	public RegistryClientsImpl(){
		this.clients = new ArrayList<Client_itf>();
	}

	public boolean register(Client_itf client) throws RemoteException {
		boolean alreadyRegistered = false;
		int i = 0;
		while(i < clients.size() && !alreadyRegistered) {
			if (client.getName().equals(clients.get(i).getName())) {
				alreadyRegistered = true;
			}
			i++;
		}
		if (!alreadyRegistered) {
			clients.add(client);
			client.setColor(ColorString.drawColor());
			client.addChatField("** Bienvenue sur le service de chat **\n");
			//Broadcast sauf pour l'utilisateur qui vient de se connecter
			for(int j = 0 ; j < clients.size() ; j++) {
				if (!clients.get(j).getName().equals(client.getName())){
					clients.get(j).addChatField("** " + client.getName() + " vient de rejoindre le chat **");
				}	
			}
			return true;
		} else {
			client.setOutputServer("Erreur : Nom déjà utilisé, veuillez en entrer un nouveau");
			client.setNameFrameSize(500, 100);
			return false;
		}
	}

	public void unregister(Client_itf client) throws RemoteException {
		boolean unregisterDone = false;
		int i = 0;
		while(i < clients.size() && !unregisterDone){
			if (client.getName().equals(clients.get(i).getName())) {
				clients.remove(i);
				unregisterDone = true;
				for(int j = 0 ; j < clients.size() ; j++) {
					clients.get(j).receive("** " + client.getName() + " vient de quitter le chat **");
				}
			}
			i++;
		}
	}

	public ArrayList<Client_itf> getClients(){
		return this.clients;
	}

	public Client_itf getClient(String name) throws RemoteException{
		boolean clientFound = false;
		int i = 0;
		while(i < clients.size() && !clientFound) {
			if (name.equals(clients.get(i).getName())) {
				clientFound = true;
				return clients.get(i);
			}
			i++;
		}
		return null;
	}
}