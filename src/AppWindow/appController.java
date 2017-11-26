package AppWindow;

import DBWindow.ConnectionConfiguration;
import DBWindow.Ship;
import DBWindow.dbController;
import RepWindow.repController;
import VisWindow.visController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class appController implements Initializable {

    @FXML
    private Pane appPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button appButton;
    @FXML
    private Button dbButton;
    @FXML
    private Button visButton;
    @FXML
    private Button repButton;

    @FXML
    private TableView<Ship>  recentLogs;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_voyage;
    @FXML
    private ComboBox<String> nation;
    @FXML
    private TextField txt_grt;
    @FXML
    private TextField txt_loa;
    @FXML
    private TextField txt_berth;
    @FXML
    private TextField txt_bollard;
    @FXML
    private TextField txt_master;
    @FXML
    private TextField txt_nrt;
    @FXML
    private TextField txt_dwt;
    @FXML
    private TextField txt_beam;
    @FXML
    private TextField txt_eta;
    @FXML
    private TextField txt_etd;
    @FXML
    private TextField txt_dfwd;
    @FXML
    private TextField txt_daft;
    @FXML
    private TextField txt_lp;
    @FXML
    private TextField txt_np;
    @FXML
    private TextField txt_berthingpost;
    @FXML
    private TextField txt_remarks;
    @FXML
    private ComboBox<String> fill;

    private ConnectionConfiguration connect;

    private String nextScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connect = new ConnectionConfiguration();
        nation.getItems().removeAll(nation.getItems());
        nation.getItems().addAll("N/A", "Afghan", "Albanian","Algerian", "American", "Andorran", "Angolan", "Antiguans",
                "Argentinean", "Armenian", "Australian", "Austrian", "Azerbaijani", "Bahamian", "Bahraini", "Bangladeshi",
                "Barbadian", "Barbudans", "Batswana", "Belarusian", "Belgian", "Belizean", "Beninese", "Bhutanese", "Bolivian",
                "Bosnian", "Brazilian", "British", "Bruneian", "Bulgarian", "Burkinabe", "Burmese", "Burundian", "Cambodian",
                "Cameroonian", "Canadian", "Cape Verdean", "Central African", "Chadian", "Chilean", "Chinese", "Colombian",
                "Comoran", "Congolese", "Costa Rican", "Croatian", "Cuban", "Cypriot", "Czech", "Danish", "Djibouti", "Dominican",
                "Dutch", "East Timorese", "Ecuadorean", "Egyptian", "Emirian", "Equatorial Guinean", "Eritrean", "Estonian",
                "Ethiopian", "Fijian", "Filipino", "Finnish", "French", "Gabonese", "Gambian", "Georgian", "German", "Ghanaian",
                "Greek", "Grenadian", "Guatemalan", "Guinea-Bissauan", "Guinean", "Guyanese", "Haitian", "Herzegovinian", "Honduran",
                "Hungarian", "Icelander", "Indian", "Indonesian", "Iranian", "Iraqi", "Irish", "Israeli", "Italian", "Ivorian",
                "Jamaican", "Japanese", "Jordanian", "Kazakhstani", "Kenyan", "Kittian and Nevisian", "Kuwaiti", "Kyrgyz",
                "Laotian", "Latvian", "Lebanese", "Liberian", "Libyan", "Liechtensteiner", "Lithuanian", "Luxembourger",
                "Macedonian", "Malagasy", "Malawian", "Malaysian", "Maldivan", "Malian", "Maltese", "Marshallese", "Mauritanian",
                "Mauritian", "Mexican", "Micronesian", "Moldovan", "Monacan", "Mongolian", "Moroccan", "Mosotho", "Motswana",
                "Mozambican", "Namibian", "Nauruan", "Nepalese", "New Zealander", "Ni-Vanuatu", "Nicaraguan", "Nigerien",
                "North Korean", "Northern Irish", "Norwegian", "Omani", "Pakistani", "Palauan", "Panamanian", "Papua New Guinean",
                "Paraguayan", "Paraguayan", "Polish", "Portuguese", "Qatari", "Romanian", "Russian", "Rwandan", "Saint Lucian",
                "Salvadoran", "Samoan", "San Marinese", "Sao Tomean", "Saudi", "Scottish", "Senegalese", "Serbian", "Seychellois",
                "Sierra Leonean", "Singaporean", "Slovakian", "Slovenian", "Solomon Islander", "Somali", "South African",
                "South Korean", "Spanish", "Sri Lankan", "Sudanese", "Surinamer", "Swazi", "Swedish", "Swiss", "Syrian",
                "Taiwanese", "Tajik", "Tanzanian", "Thai", "Togolese", "Tongan", "Trinidadian or Tobagonian", "Tunisian",
                "Turkish", "Tuvaluan", "Ugandan", "Ukrainian", "Uruguayan", "Uzbekistani", "Venezuelan", "Welsh", "Yemenite",
                "Zambian", "Zimbabwean");
        nation.setValue("Filipino");
        fill.getItems().removeAll(fill.getItems());
        fill.getItems().addAll("True", "False");
        fill.setValue("False");
    }

    @FXML
    private void add(ActionEvent event){

        //FIXME (try to add labels under each field that are set to opacity 0, pops out every time 'add' is clicked and a field has an invalid input
        String dname = new String(txt_name.getText());
        String dvoyage = new String(txt_voyage.getText());
        String dnationality = new String(nation.getValue());
        Float dgrt = Float.valueOf(new String(txt_grt.getText()));
        Float dloa = Float.valueOf(new String(txt_loa.getText()));
        String dlp = new String(txt_lp.getText());
        String dnp = new String(txt_np.getText());
        String dberth = new String(txt_berth.getText());
        String dbollard = new String(txt_bollard.getText());
        String dmaster = new String(txt_master.getText());
        Float dnrt = Float.valueOf(new String(txt_nrt.getText()));
        Float ddwt = Float.valueOf(new String(txt_dwt.getText()));
        Float dbeam = Float.valueOf(new String(txt_beam.getText()));
        Timestamp deta = Timestamp.valueOf(new String(txt_eta.getText()));
        Timestamp detd = Timestamp.valueOf(new String(txt_etd.getText()));
        Float ddfwd = Float.valueOf(new String(txt_dfwd.getText()));
        Float ddaft = Float.valueOf(new String(txt_daft.getText()));
        String dberthpost = new String(txt_berthingpost.getText());
        String dremarks = new String(txt_remarks.getText());
        String dfilled = new String(fill.getValue());


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO ship (vessel_name, voyage_no, nationality, GRT, " +
                    "LOA, last_port, next_port, berth_pref, master, NRT, DWT, beam, ETA, ETD, draft_fwd, draft_aft, berth_post," +
                    "bollard, remarks, filled)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, dname);
            preparedStatement.setString(2, dvoyage);
            preparedStatement.setString(3, dnationality);
            preparedStatement.setFloat(4, dgrt);
            preparedStatement.setFloat(5, dloa);
            preparedStatement.setString(6, dlp);
            preparedStatement.setString(7, dnp);
            preparedStatement.setString(8, dberth);
            preparedStatement.setString(9, dmaster);
            preparedStatement.setFloat(10, dnrt);
            preparedStatement.setFloat(11, ddwt);
            preparedStatement.setFloat(12, dbeam);
            preparedStatement.setTimestamp(13, deta);
            preparedStatement.setTimestamp(14, detd);
            preparedStatement.setFloat(15, ddfwd);
            preparedStatement.setFloat(16, ddaft);
            preparedStatement.setString(17, dberthpost);
            preparedStatement.setString(18, dbollard);
            preparedStatement.setString(19, dremarks);
            preparedStatement.setString(20, dfilled);
            boolean flag = preparedStatement.execute();
            System.out.println(flag);


            if (flag == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.initOwner(recentLogs.getScene().getWindow());
                alert.setContentText("Added successfully!");
                alert.showAndWait();
                // new AutoEmail("rizelleannbahin@gmail.com", "approval");
                txt_name.clear();
                txt_voyage.clear();
                nation.setValue("Filipino");
                txt_grt.clear();
                txt_loa.clear();
                txt_lp.clear();
                txt_np.clear();
                txt_berth.clear();
                txt_master.clear();
                txt_nrt.clear();
                txt_dwt.clear();
                txt_beam.clear();
                txt_eta.clear();
                txt_etd.clear();
                txt_dfwd.clear();
                txt_daft.clear();
                txt_berthingpost.clear();
                txt_bollard.clear();
                txt_remarks.clear();
                fill.setValue("False");

            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.initOwner(recentLogs.getScene().getWindow());
                alert.setContentText("Process unsuccessful! Please fill the necessary details.");
                alert.showAndWait();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //FIXME add an sql query that gets the recent 15 inputs and put it in the table

    //FIXME add a function that when the table is click info are put on the field (easier job for the secretary)

    private void loadNextScreen() throws IOException {

        ConnectionConfiguration connect = new ConnectionConfiguration();

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
            case "HomeButton":
                newWindow = FXMLLoader.load(getClass().getResource("../HomeWindow/homeWindow.fxml"));
                break;
        }



        Scene newScene = new Scene(newWindow);
        Stage window = (Stage) appPane.getScene().getWindow();
        window.setFullScreenExitHint("");
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setScene(newScene);
        window.setFullScreen(true);
        window.show();
    }

    public void changeScreenButton(ActionEvent event) throws IOException, InterruptedException {

        if (event.getSource() == homeButton){
            nextScene = "HomeButton";
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
