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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private TextField txt_vesselType;
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

    @FXML
    private Text idTxt;
    @FXML
    private Text berthPositiontxt;
    @FXML
    private Text berthtxt;
    @FXML
    private Text bollardNumbertxt;
    @FXML
    private Text bollardNumberErrortxt;
    @FXML
    private Text bollardNumberErrortxt2;
    @FXML
    private Text etatxt;
    @FXML
    private Text etdtxt;
    @FXML
    private Text lastPorttxt;
    @FXML
    private Text nextPorttxt;

    //FIXME
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTxt.setOpacity(0);
        berthPositiontxt.setOpacity(0);
        berthtxt.setOpacity(0);

        bollardNumbertxt.setOpacity(0);
        bollardNumberErrortxt.setOpacity(0);
        bollardNumberErrortxt2.setOpacity(0);
        etatxt.setOpacity(0);
        etdtxt.setOpacity(0);
        lastPorttxt.setOpacity(0);
        nextPorttxt.setOpacity(0);

        System.out.println("I came here");
        //dbPane.setOpacity(0);
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Vessel Name", "Berth No","Bollard No", "Last Port", "Next Port");
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
            txt_bpost.setText(sp.getBerth_post());
            txt_remarks.setText(sp.getRemarks());
            txt_id.setText(String.valueOf(sp.getId()));
            txt_vesselType.setText(sp.getFilled());
        });
    }

    @FXML
    public void loadDataFromDatabase(){

        data = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connect.getConnection();

            resultSet = connection.createStatement().executeQuery("SELECT * FROM ship ORDER BY ETD DESC");

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
        Filled.setCellValueFactory(new PropertyValueFactory<>("filled"));

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
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE lower(vessel_name) = ? OR vessel_name = ?");
            }else if (comboBox.getValue() == "Berth No" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE lower(berth_pref) = ? OR berth_pref = ?");
            }else if (comboBox.getValue() == "Bollard No" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE lower(bollard) = ? OR bollard = ?");
            }else if (comboBox.getValue() == "Last Port" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE lower(last_port) = ? OR last_port = ?");
            }else if (comboBox.getValue() == "Next Port" ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE lower(next_port) = ? OR next_port = ?");
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.initOwner(comboBox.getScene().getWindow());
                alert.setHeaderText(null);
                alert.setContentText("Select from the dropbox or Input what you want to search");
                alert.showAndWait();
            }
            preparedStatement.setString(1, input);
            preparedStatement.setString(2, input);
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
        Filled.setCellValueFactory(new PropertyValueFactory<>("filled"));

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

        boolean canUpdate = true;

        idTxt.setOpacity(0);
        berthPositiontxt.setOpacity(0);
        berthtxt.setOpacity(0);
        bollardNumbertxt.setOpacity(0);
        bollardNumberErrortxt.setOpacity(0);
        bollardNumberErrortxt2.setOpacity(0);
        etatxt.setOpacity(0);
        etdtxt.setOpacity(0);
        lastPorttxt.setOpacity(0);
        nextPorttxt.setOpacity(0);



        //ID
        Integer did = null;
        try{
            did = new Integer(txt_id.getText());
        }catch(Exception e){
            idTxt.setOpacity(1);
            canUpdate = false;
        }

        //Berth
        String dberthpost = null;
        //String dberthpost = new String(txt_bpost.getText());
        if (txt_bpost.getText().equals(null) || txt_bpost.getText().equals("")){
            berthPositiontxt.setOpacity(1);
            canUpdate = false;
        } else if (txt_bpost.getText().equalsIgnoreCase("portside") || txt_bpost.getText().equalsIgnoreCase("mediterranean") || txt_bpost.getText().equalsIgnoreCase("starboard")){
            dberthpost = new String(txt_bpost.getText());
        }else{
            berthPositiontxt.setOpacity(1);
            canUpdate = false;
        }

        String dberth = null;
        //String dberth = new String(txt_berth.getText());
        if (txt_berth.getText().equals(null) || txt_berth.getText().equals("")){
            berthtxt.setOpacity(1);
            canUpdate = false;
        }else{
            if (parseBerth(txt_berth.getText()) == 20 || parseBerth(txt_berth.getText()) == 23 || parseBerth(txt_berth.getText()) == 26){
                if (isBollardSpecial(txt_berth.getText())){
                    //correct
                    dberth = new String(txt_berth.getText());
                }else{
                    //wrong
                    canUpdate = false;
                    berthtxt.setOpacity(1);
                }
            }else{
                if (isBerth(txt_berth.getText()) && parseBerth(txt_berth.getText()) >= 2 && parseBerth(txt_berth.getText()) <= 28){
                    //correct
                    dberth = new String(txt_berth.getText());
                }else{
                    //wrong
                    canUpdate = false;
                    berthtxt.setOpacity(1);
                }
            }

        }


        //String dbollard = new String(txt_bollard.getText());

        String dbollard = null;
        if (txt_bollard.getText() == null || txt_bollard.getText().equals("")){
            if (isBollardSpecial(txt_berth.getText())){
                //nothing
            }else{
                canUpdate = false;
                bollardNumbertxt.setOpacity(1);
                bollardNumberErrortxt.setOpacity(1);
            }
        }else{
            if (isBollardSpecial(txt_berth.getText())){
                //no need for bollard
                canUpdate = false;
                bollardNumberErrortxt2.setOpacity(1);
                bollardNumberErrortxt.setOpacity(0);
                txt_bollard.clear();
            }else{
                if (isBollard(txt_bollard.getText()) && parseBollard(true, txt_bollard.getText()) >= 1 && parseBollard(false, txt_bollard.getText()) <= 272 && parseBollard(true,txt_bollard.getText()) < parseBollard(false,txt_bollard.getText())){
                    dbollard = new String(txt_bollard.getText());
                }else {
                    canUpdate = false;
                    bollardNumberErrortxt2.setOpacity(0);
                    bollardNumbertxt.setOpacity(1);
                    bollardNumberErrortxt.setOpacity(1);
                }
            }
        }

        Timestamp deta = null;

        if (txt_eta.getText() == null || txt_eta.getText().equals("")){
            etatxt.setOpacity(1);
            canUpdate = false;
        }else{
            if (isCorrectFormat(txt_eta.getText())){
                deta = Timestamp.valueOf(new String(txt_eta.getText()));
            }else{
                etatxt.setOpacity(1);
                canUpdate = false;
            }
        }

        Timestamp detd = null;

        if (txt_etd.getText() == null || txt_etd.getText().equals("")){
            etdtxt.setOpacity(1);
            canUpdate = false;
        }else{
            if (isCorrectFormat(txt_etd.getText())){
                detd = Timestamp.valueOf(new String(txt_etd.getText()));
            }else{
                etdtxt.setOpacity(1);
                canUpdate = false;
            }
        }


        String dlp = null;

        if (txt_lp.getText().equals(null) || txt_lp.getText().equals("")){
            lastPorttxt.setOpacity(1);
            canUpdate = false;
        }else{
            dlp = new String(txt_lp.getText());
        }
        String dnp = null;
        if (txt_np.getText().equals(null) || txt_np.getText().equals("")){
            nextPorttxt.setOpacity(1);
            canUpdate = false;
        }else{
            dnp = new String(txt_np.getText());
        }

        String dremarks = new String(txt_remarks.getText());

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if(canUpdate) {
            try {
                connection = ConnectionConfiguration.getConnection();
                preparedStatement = connection.prepareStatement("UPDATE ship SET " +
                        "berth_pref = ?, bollard = ?, berth_post = ?, ETA = ?, ETD = ?, last_port = ?, next_port = ?, remarks = ? WHERE id = ?");
                preparedStatement.setInt(9, did);
                preparedStatement.setString(1, dberth);
                preparedStatement.setString(2, dbollard);
                preparedStatement.setString(3, dberthpost);
                preparedStatement.setTimestamp(4, deta);
                preparedStatement.setTimestamp(5, detd);
                preparedStatement.setString(6, dlp);
                preparedStatement.setString(7, dnp);
                preparedStatement.setString(8, dremarks);


                int i = preparedStatement.executeUpdate();
                if (i == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Update successful!");
                    alert.showAndWait();
                    loadDataFromDatabase();
                    txt_name.clear();
                    txt_voyage.clear();
                    txt_nationality.clear();
                    txt_grt.clear();
                    txt_loa.clear();
                    txt_lp.clear();
                    txt_np.clear();
                    txt_berth.clear();
                    txt_master.clear();
                    txt_nrt.clear();
                    txt_dwt.clear();
                    txt_beam.clear();
                    txt_bollard.clear();
                    txt_remarks.clear();
                    txt_bollard.clear();
                    txt_dfwd.clear();
                    txt_daft.clear();
                    txt_vesselType.clear();
                    txt_eta.clear();
                    txt_etd.clear();
                    txt_id.clear();
                    txt_bpost.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Update NOT successful!");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Update NOT successful! Fill necessary fields");
            alert.showAndWait();
        }

        idTxt.setOpacity(0);
        berthPositiontxt.setOpacity(0);
        berthtxt.setOpacity(0);
        bollardNumbertxt.setOpacity(0);
        bollardNumberErrortxt.setOpacity(0);
        bollardNumberErrortxt2.setOpacity(0);
        etatxt.setOpacity(0);
        etdtxt.setOpacity(0);
        lastPorttxt.setOpacity(0);
        nextPorttxt.setOpacity(0);
        canUpdate = true;

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
        //window.setFullScreenExitHint("");
        //window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setFullScreen(false);
        window.setScene(newScene);
        window.sizeToScene();
        try{

        }catch(Exception e){
            window.initStyle(StageStyle.TRANSPARENT);
            window.initStyle(StageStyle.UNDECORATED);
            //window.setFullScreen(true);
        }
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


    public static boolean isBollard(String bollardString){
        int dash = 0;

        for (int i = 0; i < bollardString.length(); i++){
            if ((bollardString.charAt(i) >= '0' && bollardString.charAt(i) <= '9') || bollardString.charAt(i) == '-' || bollardString.charAt(i) == ' '){
                if (bollardString.charAt(i) == '-'){
                    dash++;
                }
            }else{
                return false;
            }
            if(dash > 1){
                return false;
            }
        }

        if (dash <= 0){
            return false;
        }

        return true;
    }

    public static boolean isBollardSpecial(String berthString){
        String part;

        if (berthString.length() < 5){
            return false;
        }

        if (berthString.charAt(0) != 'B' && berthString.charAt(0) != 'b'){
            return false;
        }

        if (berthString.charAt(1) != '-'){
            return false;
        }
        part = berthString.substring(4);
        System.out.println(part);
        if (part.equalsIgnoreCase("N tip") || part.equalsIgnoreCase("N corner") || part.equalsIgnoreCase("S tip") || part.equalsIgnoreCase("S corner") || part.equalsIgnoreCase(" tip")){
            //it ok
            System.out.println("IS Bollsarddfjskjngldmfsgkd");
        }else{
            return false;
        }

        return true;
    }



    public static boolean isBerth(String berthString){

        if (berthString.length() < 3){
            return false;
        }

        if (berthString.charAt(0) != 'B' && berthString.charAt(0) != 'b'){
            return false;
        }

        if (berthString.charAt(1) != '-'){
            return false;
        }
        for (int i = 2; i < berthString.length(); i++){
            if (berthString.charAt(i) >= '0' && berthString.charAt(i) <= '9'){
                //number
            } else{
                return false;
            }
        }

        return true;
    }


    public static int parseBerth(String berthString){
        int berth = 0;
        int berthLength = berthString.length();

        for (int i = 2, ten = 1; i < berthLength; i++){
            if (berthString.charAt(i) >= '0' && berthString.charAt(i) <= '9'){
                berth *= ten;
                berth += (berthString.charAt(i) - 48);
                ten *= 10;
            }
        }
        return berth;
    }


    public static int parseBollard(boolean first, String bollardString){
        int bollardNumber = 0;

        if (first){
            for (int i = 0, ten = 1; i < bollardString.length(); i++){
                if (bollardString.charAt(i) == '-'){
                    break;
                }
                bollardNumber *= ten;
                bollardNumber += Integer.parseInt(""+bollardString.charAt(i));
                ten = 10;
            }
        }else{
            int j;

            for (j = 0; j < bollardString.length(); j++){
                if (bollardString.charAt(j) == '-'){
                    j++;
                    break;
                }
            }

            for (int ten = 1; j < bollardString.length();j++){
                bollardNumber *= ten;
                bollardNumber += Integer.parseInt(""+bollardString.charAt(j));
                ten = 10;
            }
        }

        return bollardNumber;
    }


    public static boolean isCorrectFormat(String date){
        int year = 0;
        int month = 0;
        int day = 0;

        int hour = 0;
        int minute = 0;

        if (date.length() < 21){
            return false;
        }

        for (int i = 0, ten = 1; i < 4; i++){
            year *= ten;
            try{
                year += Integer.parseInt(""+date.charAt(i));
            }catch (Exception e){
                return false;
            }

            ten = 10;
        }
        System.out.println("Year == " +year);

        if (date.charAt(4) != '-' || date.charAt(7) != '-' || date.charAt(10) != ' ' || date.charAt(13) != ':' || date.charAt(16) != ':' || date.charAt(19) != '.'){
            return false;
        }

        if ((date.charAt(5) >= '0' && date.charAt(5) <= '9') && (date.charAt(6) >= '0' && date.charAt(6) <= '9')){
            //correct
            for (int i = 5, ten = 1; i < 7; i++){
                month *= ten;
                try{
                    month += Integer.parseInt(""+date.charAt(i));
                }catch (Exception e){
                }
                ten = 10;
            }

        }else{
            return false;
        }


        System.out.println("Month: "+month);

        if (month < 1 || month > 12 ){
            return false;
        }

        if ((date.charAt(8) >= '0' && date.charAt(8) <= '9') && (date.charAt(9) >= '0' && date.charAt(9) <= '9')){
            //correct
            for (int i = 8, ten = 1; i < 10; i++){
                day *= ten;
                try{
                    day += Integer.parseInt(""+date.charAt(i));
                }catch (Exception e){
                }
                ten = 10;
            }

        }else{
            return false;
        }

        System.out.println("day == "+day);

        if (day < 1 || day > 31){
            return false;
        }

        if ((date.charAt(11) >= '0' && date.charAt(11) <= '9') && (date.charAt(12) >= '0' && date.charAt(12) <= '9')){
            //correct
            for (int i = 11, ten = 1; i < 13; i++){
                hour *= ten;
                try{
                    hour += Integer.parseInt(""+date.charAt(i));
                }catch (Exception e){
                }
                ten = 10;
            }

        }else{
            return false;
        }

        System.out.println("Hour == "+hour);

        if (hour < 0 || hour > 23){
            return false;
        }

        if ((date.charAt(14) >= '0' && date.charAt(14) <= '9') && (date.charAt(15) >= '0' && date.charAt(15) <= '9')){
            //correct
            for (int i = 14, ten = 1; i < 16; i++){
                minute *= ten;
                try{
                    minute += Integer.parseInt(""+date.charAt(i));
                }catch (Exception e){
                }
                ten = 10;
            }

        }else{
            return false;
        }

        System.out.println("minute == "+minute);

        if ((date.charAt(17) >= '0' && date.charAt(17) <= '9') && (date.charAt(18) >= '0' && date.charAt(18) <= '9') && (date.charAt(20) >= '0' && date.charAt(20) <= '9')) {
            //correct
        }else{
            return false;
        }
        return true;
    }



}
