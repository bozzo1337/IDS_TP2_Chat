import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class NameBox {
    private Stage primaryStage;
    private BorderPane nameBoxRoot;
    private TextField textField;
    private Button connect;
    private Button reset;
    private String title;
    private Label errLabel;
    private ChatBox chatBox;
    private RegistryClients reg;
    private Client_itf c_stub;

    public NameBox(Stage primaryStage, ChatBox chatBox, RegistryClients reg, Client_itf client) {
        this.c_stub = client;
        this.reg = reg;
        this.title = "Chat";
        this.chatBox = chatBox;
        this.primaryStage = primaryStage;
        this.nameBoxRoot = new BorderPane();
        this.textField = new TextField();
        this.errLabel = new Label();
        this.connect = new Button("Connexion");
        this.reset = new Button("Reset");
        this.nameBoxRoot.setCenter(new VBox(new Label("Entrez votre nom"),textField,errLabel));
        BorderPane bottom = new BorderPane();
        HBox hBox = new HBox(connect,reset);
        hBox.setSpacing(20);
        bottom.setCenter(hBox);
        this.nameBoxRoot.setBottom(bottom);
        this.setConnectButtonOnAction();
        this.setResetButtonOnAction();
    }

    private void setConnectButtonOnAction(){
        this.connect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean registered = false;
                boolean onlyAlphabet = false;
                if (!registered) {
                    String nameEntry = textField.getText();
                    onlyAlphabet = nameEntry.matches("^[a-zA-Z]*$");
                    if (nameEntry.equals("") || !onlyAlphabet) {
                        errLabel.setText("** Erreur : Ce nom n'est pas autorisé **");
                        primaryStage.show();
                    } else {
                        try {
                            c_stub.setName(nameEntry);
                        } catch (RemoteException e) {
                            System.err.println(e);
                        }
                        //Enregistrement au registre
                        try {
                            if(registered = reg.register(c_stub)){
                                primaryStage.setHeight(1000);
                                primaryStage.setWidth(700);
                                primaryStage.setScene(new Scene(chatBox.getChatRoot()));
                                textField.clear();
                            }
                        } catch (RemoteException e) {
                            System.err.println(e);
                        }
                        
                    }

                }
            }});}

    private void setResetButtonOnAction(){
        this.reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textField.clear();
            }
        });
    }



    public BorderPane getNameBoxRoot() {
        return nameBoxRoot;
    }

    public String getTitle() {
        return title;
    }
}
