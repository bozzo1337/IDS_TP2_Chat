package nf;

import java.rmi.*;
import java.io.*;

public class ChatImpl implements Chat {

	private String history;

	public ChatImpl(){
		try {
			history = "";
			InputStream stream = new FileInputStream("history.txt"); 
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader buff = new BufferedReader(reader);
			String line;
			while ((line = buff.readLine())!=null){
				history = history + line + "\n";
			}
			buff.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

	public void publish(RegistryClients reg, String message)  throws RemoteException {
		broadcast(reg, message);
		history = history + message + "\n";
		try {
			FileOutputStream fosHistory = new FileOutputStream(new File("history.txt"));
			fosHistory.write(history.getBytes());
			fosHistory.flush();
			fosHistory.close();
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}

	public void broadcast(RegistryClients reg, String message) throws RemoteException {
		for(int i = 0 ; i < reg.getClients().size() ; i++) {
			reg.getClients().get(i).receive(message);
		}
	}

	public String loadHistory(){
		return history;
	}
	
	public void whisper(RegistryClients reg, Client_itf sender, String target, String message) throws RemoteException {
		Client_itf clientTarget = reg.getClient(target);
		if (clientTarget != null){
			clientTarget.receive(sender.getName() + " vous chuchote :" + message);
			sender.receive("Vous chuchotez à " + target + " :" + message);
		} else {
			sender.receive("** Erreur : " + target + " non connecté **");
		}
	}
}