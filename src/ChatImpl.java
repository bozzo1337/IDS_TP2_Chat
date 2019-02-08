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
		reg.broadcast(message);
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

	public String loadHistory(){
		return history;
	}
}