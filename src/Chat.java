import java.rmi.*;

public interface Chat extends Remote {
	public void publish(RegistryClients reg, Client_itf client, String message) throws RemoteException;
	public String loadHistory() throws RemoteException;
}