import java.rmi.*; 
import java.rmi.server.*; 
import java.rmi.registry.*;

public class ChatServer {

	public static void main(String [] args){
		try {

			ChatImpl c = new ChatImpl();
			Chat c_stub = (Chat) UnicastRemoteObject.exportObject(c, 0);

			Registry registry= LocateRegistry.getRegistry();
			registry.bind("ChatService", c_stub);

			RegistryClientsImpl r = new RegistryClientsImpl();
			RegistryClients r_stub = (RegistryClients) UnicastRemoteObject.exportObject(r, 0);

			registry.bind("RegistryService", r_stub);

			System.out.println ("Serveur prêt.");

		} catch (Exception e) {
			System.err.println("Error on server :" + e) ;
			e.printStackTrace();
		}
	}
}