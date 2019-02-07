import java.rmi.*;
import java.rmi.server.*; 
import java.rmi.registry.*;

public class ChatClient {

	private String name;

	public ChatClient(String name){
		this.name = name;
	}

	public static void main(String [] args){
		try {
		  	if (args.length < 2) {
		  		System.out.println("Usage: java HelloClient <rmiregistry host> <name>");
		   		return;
			}

		} catch (Exception e)  {
			System.err.println("Error on client: " + e);
		}
	} 
}