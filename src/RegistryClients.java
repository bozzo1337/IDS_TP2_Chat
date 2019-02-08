import java.rmi.*;
import java.util.*;

public interface RegistryClients extends Remote {
	public void broadcast(String message) throws RemoteException;
	public boolean register(Client_itf client) throws RemoteException;
	public void unregister(Client_itf client) throws RemoteException;
	public ArrayList<Client_itf> getClients() throws RemoteException;
}