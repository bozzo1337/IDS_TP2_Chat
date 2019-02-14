package ui;

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

public class NameBox {
    private Stage primaryStage;
    private BorderPane nameBoxRoot;
    private TextField textField;
    private Button connect;
    private Button reset;
    private String title;
    private Label errLabel;
    private RegistryClients reg;
    private Client_itf client;

    public NameBox(Stage primaryStage, RegistryClients reg, Client_itf client) {
        this.title = "Name Box";
        this.primaryStage = primaryStage;
        this.nameBoxRoot = new BorderPane();
        this.textField = new TextField();
        this.errLabel = new Label();
        this.connect = new Button("Connect");
        this.reset = new Button("reset");
        this.nameBoxRoot.setCenter(new VBox(new Label("Enter your User name"),textField,errLabel));
        BorderPane bottom = new BorderPane();
        HBox hBox = new HBox(connect,reset);
        hBox.setSpacing(20);
        bottom.setCenter(hBox);
        this.nameBoxRoot.setBottom(bottom);
        this.setConnectButtonOnAction();
        this.setResetButtonOnAction();
        this.reg = reg;
        this.client = client;
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
                        //c.setName(nameEntry);
                        //Enregistrement au registre
                        //registered = reg.register(c_stub);
                        textField.clear();
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
