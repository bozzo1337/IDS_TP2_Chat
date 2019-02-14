package ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import nf.Chat;
import nf.Client_itf;
import nf.RegistryClients;

import java.rmi.RemoteException;

public class ChatBox extends Parent {
    private Stage primaryStage;
    private BorderPane chatRoot;
    private ScrollPane messageContainer;
    private BorderPane connectedUsers;
    private Button sendButton;
    private TextField message;
    private final ListView<String> onlineUsers;
    private final ListView<Label> messages;
    private Chat chatService;
    private RegistryClients reg;
    private String clientName;
    private Client_itf c_stub;



    public ChatBox(Stage primStage, Chat chatService, RegistryClients reg, String clientName,Client_itf c_stub) {
        this.c_stub = c_stub;
        this.clientName = clientName;
        this.reg = reg;
        this.chatService = chatService;
        this.primaryStage = primStage;
        this.chatRoot = new BorderPane();
        this.messageContainer = new ScrollPane();
        this.connectedUsers = new BorderPane();
        this.connectedUsers.setPrefWidth(150);
        this.messages = new ListView<>();
        this.messages.getItems().addAll(new Label("** Commandes disponibles : **\n" +
                "/history : Affiche l'historique des conversations\n" +
                "/w <Nom de la cible> <Message> : Envoie un message privé à la cible\n" +
                "/help : Affiche la liste des commandes\n" +
                "/quit : Quitte le chat\n" +
                "** - - - - - - - - - - - - **"));
        this.messageContainer.setContent(this.messages);
        this.chatRoot.setCenter(this.messageContainer);
        BorderPane bottom = new BorderPane();
        this.sendButton = new Button("Publish");
        this.message = new TextField();
        bottom.setLeft(this.sendButton);
        bottom.setCenter(this.message);
        this.chatRoot.setBottom(bottom);
        this.onlineUsers = new ListView<>();
        this.onlineUsers.getItems().setAll("Mouataz","Benjamin");
        try {
            this.putListennerOnConnectedPeople(reg.getClients());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.onlineUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.connectedUsers.setTop(new Label("Online users"));
        this.connectedUsers.setCenter(this.onlineUsers);
        this.chatRoot.setLeft(this.connectedUsers);
        this.primaryStage.setTitle("ChatBox");
        this.primaryStage.setHeight(1000);
        this.primaryStage.setWidth(700);
        this.primaryStage.setScene(new Scene(this.chatRoot));
        this.primaryStage.show();
    }

   public void putListennerOnConnectedPeople(ObservableList<Client_itf> connectedUsers) {
        connectedUsers.addListener(new ListChangeListener<Client_itf>() {
           @Override
           public void onChanged(Change<? extends Client_itf> c) {
               onlineUsers.getItems().addAll(String.valueOf(c.getAddedSubList()));
          }
      });
 }

 public void setPublishButtonOnAction(){
        this.sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                if (!message.getText().equals("")){
                    String msg = message.getText();
                    if(!msg.equals("/quit")){
                        if(message.equals("/history")) {
                            //Affichage de l'historique
                            messages.getItems().addAll(new Label(chatService.loadHistory()));
                            }
                         else if (message.equals("/help")) {
                             messages.getItems().addAll(new Label("\"** Commandes disponibles : **\\n\" +\n" +
                                     "                \"/history : Affiche l'historique des conversations\\n\" +\n" +
                                     "                \"/w <Nom de la cible> <Message> : Envoie un message privé à la cible\\n\" +\n" +
                                     "                \"/help : Affiche la liste des commandes\\n\" +\n" +
                                     "                \"/quit : Quitte le chat\\n\" +\n" +
                                     "                \"** - - - - - - - - - - - - **\""));
                        }
                        else if (msg.startsWith("/w")) {
                            //Scanner scString = new Scanner(message);
                            if (msg.equals("/w")) {
                                messages.getItems().addAll(new Label("** Utilisation : /w <Nom de la cible> <Message> **"));
                                message.clear();
                            } else {
                                String scString = message.getText() ;
                                String[] parts = scString.split(" ");
                                chatService.whisper(reg, c_stub, parts[1], parts[2]);
                                }
                            }
                            else{
                                messages.getItems().addAll(new Label("** Commande non reconnue, tapez /help pour les commandes disponibles **"));
                            }
                        }
                        else{
                        reg.unregister(c_stub);
                        System.out.println("** Fin du chat, miaou ! **");
                        }
                    }
                } catch (RemoteException e) {
                   messages.getItems().add(new Label("Error on client: " + e));
                }
            }
        });
 }


    public ScrollPane getMessageContainer() {
        return messageContainer;
    }
}
