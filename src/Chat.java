import java.rmi.*;

public interface Chat extends Remote {
	public void publish(RegistryClients reg, String message) throws RemoteException;
	public String loadHistory() throws RemoteException;
}