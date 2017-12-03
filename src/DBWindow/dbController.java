package DBWindow;

import AppWindow.appController;
import RepWindow.repController;
import VisWindow.visController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class dbController implements Initializable {

    @FXML
    private Pane dbPane;
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
    private TextField searchField;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_voyage;
    @FXML
    private TextField txt_nationality;
    @FXML
    private TextField txt_grt;
    @FXML
    private TextField txt_loa;
    @FXML
    private TextField txt_master;
    @FXML
    private TextField txt_nrt;
    @FXML
    private TextField txt_dwt;
    @FXML
    private TextField txt_beam;
    @FXML
    private TextField txt_dfwd;
    @FXML
    private TextField txt_daft;
    @FXML
    private TextField txt_bpost;
    @FXML
    private TextField txt_berth;
    @FXML
    private TextField txt_bollard;
    @FXML
    private TextField txt_eta;
    @FXML
    private TextField txt_etd;
    @FXML
    private TextField txt_lp;
    @FXML
    private TextField txt_np;
    @FXML
    private TextField txt_remarks;
    @FXML
    private TextField txt_id;
    @FXML
    private TableView<Ship> tableShip;
    @FXML
    private TableColumn<Ship, String> Name;
    @FXML
    private TableColumn<Ship, String> Voyage;
    @FXML
    private TableColumn<Ship, String> Nationality;
    @FXML
    private TableColumn<Ship, Float> GRT;
    @FXML
    private TableColumn<Ship, Float> LOA;
    @FXML
    private TableColumn<Ship, String> Last_Port;
    @FXML
    private TableColumn<Ship, String> Next_Port;
    @FXML
    private TableColumn<Ship, String> Berth_No;
    @FXML
    private TableColumn<Ship, String> Bollard_No;
    @FXML
    private TableColumn<Ship, String> Master;
    @FXML
    private TableColumn<Ship, Float> NRT;
    @FXML
    private TableColumn<Ship, Float> DWT;
    @FXML
    private TableColumn<Ship, Float> Beam;
    @FXML
    private TableColumn<Ship, Timestamp> ETA;
    @FXML
    private TableColumn<Ship, Timestamp> ETD;
    @FXML
    private TableColumn<Ship, Float> Draft_fwd;
    @FXML
    private TableColumn<Ship, Float> Draft_aft;
    @FXML
    private TableColumn<Ship, Float> Berth_post;
    @FXML
    private TableColumn<Ship, String> Remarks;
    @FXML
    private TableColumn<Ship, String> Filled;

    private String nextScene;

    private ObservableList<Ship> data;
    private ObservableList<Ship> data1;
    private ObservableList<Ship> search;
    private ConnectionConfiguration connect;
    private ArrayList<String> suggestion;

    //FIXME
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("I came here");
        //dbPane.setOpacity(0);
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Vessel Name", "Berth No","Bollard No", "ETA", "ETD", "Last Port", "Next Port");
        comboBox.setValue("Select Here");
        connect = new ConnectionConfiguration();
        setCellValueTextfield();
        //makeFadeInTransition();
    }

    private void setCellValueTextfield(){
        System.out.println("I was called??");
        tableShip.setOnMouseClicked(event -> {
            Ship sp = tableShip.getItems().get(tableShip.getSelectionModel().getSelectedIndex());
            txt_name.setText(sp.getVessel_name());
            txt_voyage.setText(sp.getVoyage_num());
            txt_nationality.setText(sp.getNationality());
            txt_grt.setText(String.valueOf(sp.getGRT()));
            txt_loa.setText(String.valueOf(sp.getLOA()));
            txt_berth.setText(sp.getBerth_pref());
            txt_bollard.setText(sp.getBollard());
            txt_master.setText(sp.getMaster());
            txt_nrt.setText(String.valueOf(sp.getNRT()));
            txt_dwt.setText(String.valueOf(sp.getDWT()));
            txt_beam.setText(String.valueOf(sp.getBeam()));
            txt_dfwd.setText(String.valueOf(sp.getDraft_fwd()));
            txt_daft.setText(String.valueOf(sp.getDraft_aft()));
            txt_eta.setText(String.valueOf(sp.getETA()));
            txt_etd.setText(String.valueOf(sp.getETD()));
            txt_lp.setText(sp.getLast_port());
            txt_np.setText(sp.getNext_port());
            //txt_bpost.setText(sp.getBerth_post());
            txt_remarks.setText(sp.getRemarks());
            txt_id.setText(String.valueOf(sp.getId()));
        });
    }

    @FXML
    public void loadDataFromDatabase(){

        data = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connect.getConnection();

            resultSet = connection.createStatement().executeQuery("SELECT * FROM ship");

            while (resultSet.next()){

                data.add(new Ship(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getFloat(5),
                        resultSet.getFloat(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9),
                        resultSet.getString(10), resultSet.getFloat(11), resultSet.getFloat(12), resultSet.getFloat(13),
                        resultSet.getTimestamp(14), resultSet.getTimestamp(15), resultSet.getFloat(16), resultSet.getFloat(17),
                        resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), resultSet.getString(21)));
            }
        }catch (SQLException e){
            System.err.println("Error"+e);
        }

        Name.setCellValueFactory(new PropertyValueFactory<>("vessel_name"));
        Voyage.setCellValueFactory(new PropertyValueFactory<>("voyage_num"));
        Nationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        GRT.setCellValueFactory(new PropertyValueFactory<>("GRT"));
        LOA.setCellValueFactory(new PropertyValueFactory<>("LOA"));
        Last_Port.setCellValueFactory(new PropertyValueFactory<>("last_port"));
        Next_Port.setCellValueFactory(new PropertyValueFactory<>("next_port"));
        Berth_No.setCellValueFactory(new PropertyValueFactory<>("berth_pref"));
        Bollard_No.setCellValueFactory(new PropertyValueFactory<>("bollard"));
        Master.setCellValueFactory(new PropertyValueFactory<>("master"));
        NRT.setCellValueFactory(new PropertyValueFactory<>("NRT"));
        DWT.setCellValueFactory(new PropertyValueFactory<>("DWT"));
        Beam.setCellValueFactory(new PropertyValueFactory<>("beam"));
        ETA.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        ETD.setCellValueFactory(new PropertyValueFactory<>("ETD"));
        Draft_fwd.setCellValueFactory(new PropertyValueFactory<>("draft_fwd"));
        Draft_aft.setCellValueFactory(new PropertyValueFactory<>("draft_aft"));
        Berth_post.setCellValueFactory(new PropertyValueFactory<>("berth_post"));
        Remarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        Filled.setCellValueFactory(new PropertyValueFactory<>("liner"));

        tableShip.setItems(data);

        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //FIXME
    @FXML
    private void searchFilter(ActionEvent event){

        String input = new String(searchField.getText());
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            search = FXCollections.observableArrayList();
            connection = ConnectionConfiguration.getConnection();
            if (comboBox.getValue() == "Vessel Name" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE vessel_name = ?");
            }else if (comboBox.getValue() == "Berth No" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE berth_pref = ?");
            }else if (comboBox.getValue() == "Bollard No" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE bollard = ?");
            }else if (comboBox.getValue() == "ETA" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE ETA = ?");
            }else if (comboBox.getValue() == "ETD" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE ETD = ?");
            }else if (comboBox.getValue() == "Last Port" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE last_port = ?");
            }else if (comboBox.getValue() == "Next Port" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE next_port = ?");
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.initOwner(comboBox.getScene().getWindow());
                alert.setHeaderText(null);
                alert.setContentText("Select from the dropbox or Input what you want to search");
                alert.showAndWait();
            }
            preparedStatement.setString(1, input);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                search.add(new Ship(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getFloat(5),
                        resultSet.getFloat(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9),
                        resultSet.getString(10), resultSet.getFloat(11), resultSet.getFloat(12), resultSet.getFloat(13),
                        resultSet.getTimestamp(14), resultSet.getTimestamp(15), resultSet.getFloat(16), resultSet.getFloat(17),
                        resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), resultSet.getString(21)));
                //System.out.println(search.get(0).getVoyage_num());
            }
        }catch (SQLException e){
            System.err.println("Error"+e);
        }

        Name.setCellValueFactory(new PropertyValueFactory<>("vessel_name"));
        Voyage.setCellValueFactory(new PropertyValueFactory<>("voyage_num"));
        Nationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        GRT.setCellValueFactory(new PropertyValueFactory<>("GRT"));
        LOA.setCellValueFactory(new PropertyValueFactory<>("LOA"));
        Last_Port.setCellValueFactory(new PropertyValueFactory<>("last_port"));
        Next_Port.setCellValueFactory(new PropertyValueFactory<>("next_port"));
        Berth_No.setCellValueFactory(new PropertyValueFactory<>("berth_pref"));
        Bollard_No.setCellValueFactory(new PropertyValueFactory<>("bollard"));
        Master.setCellValueFactory(new PropertyValueFactory<>("master"));
        NRT.setCellValueFactory(new PropertyValueFactory<>("NRT"));
        DWT.setCellValueFactory(new PropertyValueFactory<>("DWT"));
        Beam.setCellValueFactory(new PropertyValueFactory<>("beam"));
        ETA.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        ETD.setCellValueFactory(new PropertyValueFactory<>("ETD"));
        Draft_fwd.setCellValueFactory(new PropertyValueFactory<>("draft_fwd"));
        Draft_aft.setCellValueFactory(new PropertyValueFactory<>("draft_aft"));
        Berth_post.setCellValueFactory(new PropertyValueFactory<>("berth_post"));
        Remarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        Filled.setCellValueFactory(new PropertyValueFactory<>("liner"));

        tableShip.setItems(null);
        tableShip.setItems(search);

        if (preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void updateDataDatabase(ActionEvent event){
        Integer did = new Integer(txt_id.getText());
        String dname = new String(txt_name.getText());
        String dberth = new String(txt_berth.getText());
        String dbollard = new String(txt_bollard.getText());
        Timestamp deta = Timestamp.valueOf(new String(txt_eta.getText()));
        Timestamp detd = Timestamp.valueOf(new String(txt_etd.getText()));
        String dlp = new String(txt_lp.getText());
        String dnp = new String(txt_np.getText());
        String dremarks = new String(txt_remarks.getText());


        if (dname == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Update NOT successful");
            alert.setContentText("Select row and input data to update");
            alert.showAndWait();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();

            preparedStatement = connection.prepareStatement("UPDATE ship SET " +
                    "berth_pref = ?, bollard = ?, vessel_name = ?, ETA = ?, ETD = ?, last_port = ?, next_port = ?, remarks = ? WHERE id = ?");
            preparedStatement.setInt(9, did);
            preparedStatement.setString(1, dberth);
            preparedStatement.setString(2, dbollard);
            preparedStatement.setString(3, dname);
            preparedStatement.setTimestamp(4, deta);
            preparedStatement.setTimestamp(5, detd);
            preparedStatement.setString(6, dlp);
            preparedStatement.setString(7, dnp);
            preparedStatement.setString(8, dremarks);


            int i = preparedStatement.executeUpdate();
            if (i == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Update successful!");
                alert.showAndWait();
                loadDataFromDatabase();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Update NOT successful!");
                alert.showAndWait();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadNextScreen() throws IOException {

        ConnectionConfiguration connect = new ConnectionConfiguration();

        Parent newWindow = null;

        switch (nextScene) {
            case "Application": {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../AppWindow/appWindow.fxml"));
                newWindow = loader.load();
                appController aController = loader.getController();
                aController.loadDataFromDatabase();
                break;
            }
            case "Report": {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../RepWindow/repWindow.fxml"));
                newWindow = loader.load();
                repController reportController = loader.getController();
                reportController.loadFromDatabase();
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
        Stage window = (Stage) dbPane.getScene().getWindow();
        window.setFullScreenExitHint("");
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setScene(newScene);
        window.setFullScreen(true);
        window.show();
    }

    public void changeScreenButton(ActionEvent event) throws IOException, InterruptedException {

        if (event.getSource() == homeButton){
            nextScene = "HomeButton";
        } else if (event.getSource() == appButton){
            nextScene = "Application";
        } else if (event.getSource() == visButton){
            nextScene = "Visual";
        } else if (event.getSource() == repButton){
            nextScene = "Report";
        }

        loadNextScreen();

    }


}
