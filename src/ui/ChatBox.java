package ui;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import nf.Client_itf;

public class ChatBox {
    private Stage primaryStage;
    private BorderPane chatRoot;
    private ScrollPane messageContainer;
    private BorderPane connectedUsers;
    private Button sendButton;
    private TextField message;
    private final ListView<String> onlineUsers;


    public ChatBox(Stage primStage,ObservableList<Client_itf> observableConnectedUsers) {
        this.primaryStage = primStage;
        this.chatRoot = new BorderPane();
        this.messageContainer = new ScrollPane();
        this.connectedUsers = new BorderPane();
        this.connectedUsers.setPrefWidth(150);
        this.messageContainer.setContent(new Label("** Commandes disponibles : **\n" +
                "/list : Affiche la liste des personnes présentes\n" +
                "/history : Affiche l'historique des conversations\n" +
                "/color <Couleur> : Change la couleur de votre texte\n" +
                "/w <Nom de la cible> <Message> : Envoie un message privé à la cible\n" +
                "/help : Affiche la liste des commandes\n" +
                "/quit : Quitte le chat\n" +
                "** - - - - - - - - - - - - **"));
        this.chatRoot.setCenter(this.messageContainer);
        BorderPane bottom = new BorderPane();
        this.sendButton = new Button("Publish");
        this.message = new TextField("Ecrivez un message");
        bottom.setLeft(this.sendButton);
        bottom.setCenter(this.message);
        this.chatRoot.setBottom(bottom);
        this.onlineUsers = new ListView<>();
        this.onlineUsers.getItems().setAll("Mouataz","Benjamin");
        //this.PutListennerOnConnectedPeople(observableConnectedUsers);
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

  //  public void PutListennerOnConnectedPeople(ObservableList<Client_itf> connectedUsers) {
//        connectedUsers.addListener(new ListChangeListener<Client_itf>() {
//            @Override
//            public void onChanged(Change<? extends Client_itf> c) {
//                onlineUsers.getItems().addAll(String.valueOf(c.getAddedSubList()));
//            }
//        });
 //}

}
