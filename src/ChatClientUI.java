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
    private static ChatClientUI client;
    private static RegistryClients reg;

    @Override
    public void start(Stage primaryStage) throws Exception {
        chatBox = new ChatBox(primaryStage, chat, reg, client);
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
                    System.out.println("Usage: java ChatClientUI <rmiregistry host>");
                    System.exit(0);
                }

            String host = args[0];

            Registry registry = LocateRegistry.getRegistry(host);

            //Creation de la reference client
            client = new ChatClientUI();
            c_stub = (Client_itf) UnicastRemoteObject.exportObject(client, 0);

            //Recuperation des services serveur
            chat = (Chat) registry.lookup("ChatService");
            reg = (RegistryClients) registry.lookup("RegistryService");

            client.launch();
        } catch (Exception e) {
            System.err.println("Error on client : " + e);
        }
    }

    public void receive (String message) throws RemoteException {
        this.chatBox.getMessages().getItems().addAll(new Label(message));
    }

    public String getName() throws RemoteException {
        return this.name;
    }
    public void setName(String name) throws RemoteException {
        this.name = name;
    }

    public void setChatBox(ChatBox cb){
        this.chatBox = cb;
    }

    public ChatBox getChatBox(){
        return this.chatBox;
    }

    public void setNameBox(NameBox nb){
        this.nameBox = nb;
    }

    public NameBox getNameBox(){
        return this.nameBox;
    }

    public Client_itf getRef(){
        return c_stub;
    }
}
