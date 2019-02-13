package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class ChatBoxTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ChatBox cb = new ChatBox(primaryStage,null);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
