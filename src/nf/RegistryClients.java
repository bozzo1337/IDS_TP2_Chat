package nf;

import javafx.collections.ObservableList;

import java.rmi.*;

public interface RegistryClients extends Remote {
	public boolean register(Client_itf client) throws RemoteException;
	public void unregister(Client_itf client) throws RemoteException;
	public ObservableList<Client_itf> getClients() throws RemoteException;
	public Client_itf getClient(String name) throws RemoteException;
}