package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.rmi.registry.*;
import java.rmi.*;
import nf.*;


public class ChatClientUI extends Application implements Client_itf {

    private String name;


    @Override
    public void start(Stage primaryStage) throws Exception {
        ChatBox chatBox = new ChatBox(primaryStage, chat, reg, this.name);
        NameBox nameBox = new NameBox(primaryStage, chatBox, reg, c_stub);
        primaryStage.setResizable(false);
        primaryStage.setTitle(nameBox.getTitle());
        primaryStage.setScene(new Scene(nameBox.getNameBoxRoot()));
        primaryStage.setHeight(150);
        primaryStage.setWidth(500);
        primaryStage.show();
    }

    public static void main(String[] args){
        Registry registry = LocateRegistry.getRegistry(host);

        //Creation de la reference client
        ChatClientUI c = new ChatClientUI();
        Client_itf c_stub = (Client_itf) UnicastRemoteObject.exportObject(c, 0);

        //Recuperation des services serveur
        Chat chat = (Chat) registry.lookup("ChatService");
        RegistryClients reg = (RegistryClients) registry.lookup("RegistryService");


        launch(args);
    }

    public void receive (String message) throws RemoteException {
        chatBox.getScrollPane().append(message + "\n");
    }

    public String getName() throws RemoteException {
        return this.name;
    }
    public void setName(String name) throws RemoteException {
        this.name = name;
    }
}
