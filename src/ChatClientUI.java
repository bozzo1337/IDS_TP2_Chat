import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.rmi.registry.*;
import java.rmi.*;
import java.rmi.server.*;
import javafx.scene.control.*;


public class ChatClientUI extends Application implements Client_itf {

    private String name;
    private ChatBox chatBox;
    private NameBox nameBox;
    private static Chat chat;
    private static Client_itf c_stub;
    private static RegistryClients reg;


    @Override
    public void start(Stage primaryStage) throws Exception {
        chatBox = new ChatBox(primaryStage, chat, reg, c_stub);
        nameBox = new NameBox(primaryStage, chatBox, reg, c_stub);
        primaryStage.setResizable(false);
        primaryStage.setTitle(nameBox.getTitle());
        primaryStage.setScene(new Scene(nameBox.getNameBoxRoot()));
        primaryStage.setHeight(150);
        primaryStage.setWidth(500);
        primaryStage.show();
    }

    public static void main(String[] args){
        try {
            if (args.length < 1) {
                    System.out.println("Usage: java ChatClient <rmiregistry host>");
                    System.exit(0);
                }

            String host = args[0];

            Registry registry = LocateRegistry.getRegistry(host);

            //Creation de la reference client
            ChatClientUI c = new ChatClientUI();
            c_stub = (Client_itf) UnicastRemoteObject.exportObject(c, 0);

            //Recuperation des services serveur
            chat = (Chat) registry.lookup("ChatService");
            reg = (RegistryClients) registry.lookup("RegistryService");
        } catch (Exception e) {
            System.err.println("Error on client : " + e);
        }

        launch(args);
    }

    public void receive (String message) throws RemoteException {
        chatBox.getMessages().getItems().addAll(new Label(message + "\n"));
    }

    public String getName() throws RemoteException {
        return this.name;
    }
    public void setName(String name) throws RemoteException {
        this.name = name;
    }
}
