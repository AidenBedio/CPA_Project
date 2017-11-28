package HomeWindow;

import DBWindow.ConnectionConfiguration;
import DBWindow.dbController;
import RepWindow.repController;
import VisWindow.visController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {
    /*
        Initialization of the objects used in the FXML
     */
    @FXML
    private Pane homePane;
    @FXML
    public Button dbButton;
    @FXML
    public Button visButton;
    @FXML
    public Button repButton;
    @FXML
    public Button appButton;

    private String nextScene;

    private void loadNextScreen() throws IOException {

        Parent newWindow = null;

        switch (nextScene) {
            case "Database": {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../DBWindow/dbWindow.fxml"));
                newWindow = loader.load();
                dbController dbController = loader.getController();
                dbController.loadDataFromDatabase();
                break;
            }
            case "Report": {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../RepWindow/repWindow.fxml"));
                newWindow = loader.load();
                repController reportController = loader.getController();
                //FIXME
                //reportController.loadFromDatabase();
                break;
            }
            case "Visual": {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../VisWindow/visWindow.fxml"));
                newWindow = loader.load();
                visController vController = loader.getController();
                vController.setTime();
                break;
            }
            case "Application":
                newWindow = FXMLLoader.load(getClass().getResource("../AppWindow/appWindow.fxml"));
                break;
        }



        Scene newScene = new Scene(newWindow);
        Stage window = (Stage) homePane.getScene().getWindow();
        window.setFullScreenExitHint("");
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setScene(newScene);
        window.setFullScreen(true);
        window.show();
    }

    public void changeScreenButton(ActionEvent event) throws IOException, InterruptedException {

        if (event.getSource() == appButton){
            nextScene = "Application";
        } else if (event.getSource() == dbButton){
            nextScene = "Database";
        } else if (event.getSource() == visButton){
            nextScene = "Visual";
        } else if (event.getSource() == repButton){
            nextScene = "Report";
        }

        loadNextScreen();

    }
}



