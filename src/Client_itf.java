import java.rmi.*;

public interface Client_itf extends Remote {
	public void receive(String publisher, String message)  throws RemoteException;
	public String getName() throws RemoteException;
	public void setName(String name) throws RemoteException;
}