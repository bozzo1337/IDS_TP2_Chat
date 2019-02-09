import java.rmi.*;

public interface Chat extends Remote {
	public void publish(RegistryClients reg, String message) throws RemoteException;
	public String loadHistory() throws RemoteException;
	public void whisper(RegistryClients reg, Client_itf sender, String target, String message) throws RemoteException;
}