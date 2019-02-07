import java.rmi.*;

public interface Chat extends Remote {
	public void publish(String publisher, String message)  throws RemoteException;

	public void receive() throws RemoteException;
}