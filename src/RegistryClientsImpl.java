import java.rmi.*;
import java.util.*;

public class RegistryClientsImpl implements RegistryClients {

	private ArrayList<Client_itf> clients;

	public RegistryClientsImpl(){
		this.clients = new ArrayList<Client_itf>();
	}

	public void broadcast(String message) throws RemoteException {
		for(int i = 0 ; i < clients.size() ; i++) {
			clients.get(i).receive(message);
		}
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
			broadcast("** " + client.getName() + " vient de rejoindre le chat **");
			return true;
		} else {
			client.receive("** Erreur : Nom déjà utilisé, veuillez en entrer un nouveau **");
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
				broadcast("** " + client.getName() + " vient de quitter le chat **");
			}
			i++;
		}
	}

	public ArrayList<Client_itf> getClients(){
		return this.clients;
	}
}