package nf;

import java.rmi.*;
import java.util.*;

public interface RegistryClients extends Remote {
	public boolean register(Client_itf client) throws RemoteException;
	public void unregister(Client_itf client) throws RemoteException;
	public ArrayList<Client_itf> getClients() throws RemoteException;
	public Client_itf getClient(String name) throws RemoteException;
}