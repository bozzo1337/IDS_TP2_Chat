package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        NameBox nameBox = new NameBox(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.setTitle(nameBox.getTitle());
        primaryStage.setScene(new Scene(nameBox.getNameBoxRoot()));
        primaryStage.setHeight(150);
        primaryStage.setWidth(500);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
