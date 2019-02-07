import java.rmi.*;
import java.rmi.server.*; 
import java.rmi.registry.*;
import java.util.Scanner;

public class ChatClient implements Client_itf{

	private String name;

	public static void main(String [] args){
		try {
		  	if (args.length < 1) {
		  		System.out.println("Usage: java HelloClient <rmiregistry host>");
		   		return;
			}

			String host = args[0];

			Registry registry = LocateRegistry.getRegistry(host);

			ChatClient c = new ChatClient();
			Client_itf c_stub = (Client_itf) UnicastRemoteObject.exportObject(c, 0);

			Chat chat = (Chat) registry.lookup("ChatService");
			RegistryClients reg = (RegistryClients) registry.lookup("RegistryService");

			System.out.println("Bonjour, quel est votre nom ?");
			Scanner sc = new Scanner(System.in);

			boolean registered = false;
			while(!registered){
				c.setName(sc.nextLine());
				registered = reg.register(c_stub);
			}
			System.out.println("Bienvenue sur le service de chat");
			System.out.println(chat.loadHistory());
			String message = sc.nextLine();
			while(!message.equals("/quit")){			
				chat.publish(reg, c_stub, message);
				message = sc.nextLine();
			}
			reg.unregister(c_stub);
			System.out.println("Fin du chat, miaou !");
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

	public void receive(String publisher, String message) throws RemoteException {
		System.out.println(publisher + " : " + message);
	}
}