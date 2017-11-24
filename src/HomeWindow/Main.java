package HomeWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("homeWindow.fxml"));
        primaryStage.setTitle("Angkla");
        Scene homeWindowScene = new Scene (root,1366, 768);
        ;homeWindowScene.getStylesheets().add(Main.class.getResource("homeWindow.css").toExternalForm());
        primaryStage.setScene(homeWindowScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
