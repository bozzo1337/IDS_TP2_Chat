import java.rmi.*;
import javax.swing.*;

public interface Client_itf extends Remote {
	public void receive(String message)  throws RemoteException;
	public String getName() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public void setColor(String color) throws RemoteException;
	public String getColor() throws RemoteException;
	public JTextField getNameField() throws RemoteException;
	public JTextArea getChatField() throws RemoteException;
	public void addChatField(String s) throws RemoteException;
	public void setUsersField(String s) throws RemoteException;
	public void setOutputServer(String output) throws RemoteException;
	public void setNameFrameSize(int width, int length) throws RemoteException;
}