package AppWindow;

import DBWindow.ConnectionConfiguration;
import DBWindow.Ship;
import DBWindow.dbController;
import RepWindow.repController;
import VisWindow.visController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
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
    private TextField txt_name;
    @FXML
    private TextField txt_voyage;
    @FXML
    //private ComboBox<String> nation;

    private TextField nation;
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
    @FXML
    private DatePicker etaDate;
    @FXML
    private DatePicker etdDate;
    @FXML
    private ComboBox<String> etaHour;
    @FXML
    private ComboBox<String> etaMinute;
    @FXML
    private ComboBox<String> etdHour;
    @FXML
    private ComboBox<String> etdMinute;
    @FXML
    private ComboBox<String> berthPosition;
    @FXML
    private ComboBox<String> schedule;
    @FXML
    private TextField validity;

    @FXML
    private TableView<Ship>  recentLogs;
    @FXML
    private TableColumn<Ship, String> Name;
    @FXML
    private TableColumn<Ship, String> ETA;
    @FXML
    private TableColumn<Ship, String> ETD;

    @FXML
    private Text vesselNametxt;
    @FXML
    private Text voyageNumbertxt;
    @FXML
    private Text nationalitytxt;
    @FXML
    private Text grttxt;
    @FXML
    private Text loatxt;
    @FXML
    private Text lastPorttxt;
    @FXML
    private Text nextPorttxt;
    @FXML
    private Text berthNumbertxt;
    @FXML
    private Text bollardNumbertxt;
    @FXML
    private Text mastertxt;
    @FXML
    private Text nrttxt;
    @FXML
    private Text dwttxt;
    @FXML
    private Text beamtxt;
    @FXML
    private Text etatxt;
    @FXML
    private Text berthingPositiontxt;
    @FXML
    private Text filledtxt;
    @FXML
    private Text remarkstxt;
    @FXML
    private Text etdtxt;
    @FXML
    private Text draftFwdtxt;
    @FXML
    private Text draftAfttxt;

    //error messages
    @FXML
    private Text grtErrortxt;
    @FXML
    private Text loaErrortxt;
    @FXML
    private Text bollardNumberErrortxt;
    @FXML
    private Text nrtErrortxt;
    @FXML
    private Text dwtErrortxt;
    @FXML
    private Text beamErrortxt;
    @FXML
    private Text draftFwdErrortxt;
    @FXML
    private Text draftAftErrortxt;

    private ConnectionConfiguration connect;

    private String nextScene;

    private ObservableList<Ship> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vesselNametxt.setOpacity(0);
        voyageNumbertxt.setOpacity(0);
        nationalitytxt.setOpacity(0);
        grttxt.setOpacity(0);
        loatxt.setOpacity(0);
        lastPorttxt.setOpacity(0);
        nextPorttxt.setOpacity(0);
        berthNumbertxt.setOpacity(0);
        bollardNumbertxt.setOpacity(0);
        mastertxt.setOpacity(0);
        nrttxt.setOpacity(0);
        dwttxt.setOpacity(0);
        beamtxt.setOpacity(0);
        etatxt.setOpacity(0);
        berthingPositiontxt.setOpacity(0);
        filledtxt.setOpacity(0);
        remarkstxt.setOpacity(0);
        etdtxt.setOpacity(0);
        draftFwdtxt.setOpacity(0);
        draftAfttxt.setOpacity(0);

        //error messages
        grtErrortxt.setOpacity(0);
        loaErrortxt.setOpacity(0);
        bollardNumberErrortxt.setOpacity(0);
        nrtErrortxt.setOpacity(0);
        dwtErrortxt.setOpacity(0);
        beamErrortxt.setOpacity(0);
        draftFwdErrortxt.setOpacity(0);
        draftAftErrortxt.setOpacity(0);

        etaHour.getItems().removeAll(etaHour.getItems());
        etaHour.getItems().addAll("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
        etaMinute.getItems().removeAll(etaMinute.getItems());
        etaMinute.getItems().addAll("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25",
                "26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50",
                "51","52","53","54","55","56","57","58","59");

        etdHour.getItems().removeAll(etdHour.getItems());
        etdHour.getItems().addAll("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
        etdMinute.getItems().removeAll(etdMinute.getItems());
        etdMinute.getItems().addAll("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25",
                "26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50",
                "51","52","53","54","55","56","57","58","59");

        berthPosition.getItems().removeAll(berthPosition.getItems());
        berthPosition.getItems().addAll("portside","mediterranean","starboard", "tip", "corner");

        connect = new ConnectionConfiguration();
        fill.getItems().removeAll(fill.getItems());
        fill.getItems().addAll("Passenger", "Cargo", "Tanker", "Unspecified");
        schedule.getItems().removeAll(schedule.getItems());
        schedule.getItems().addAll("Everyday", "Weekdays", "MonWedFriSat", "SunTueFriSat", "MonTueWedThuFriSat");

        setCellValueTextfield();
    }

    @FXML
    private void add(ActionEvent event){

        vesselNametxt.setOpacity(0);
        voyageNumbertxt.setOpacity(0);
        nationalitytxt.setOpacity(0);
        grttxt.setOpacity(0);
        loatxt.setOpacity(0);
        lastPorttxt.setOpacity(0);
        nextPorttxt.setOpacity(0);
        berthNumbertxt.setOpacity(0);
        bollardNumbertxt.setOpacity(0);
        mastertxt.setOpacity(0);
        nrttxt.setOpacity(0);
        dwttxt.setOpacity(0);
        beamtxt.setOpacity(0);
        etatxt.setOpacity(0);
        berthingPositiontxt.setOpacity(0);
        filledtxt.setOpacity(0);
        remarkstxt.setOpacity(0);
        etdtxt.setOpacity(0);
        draftFwdtxt.setOpacity(0);
        draftAfttxt.setOpacity(0);
        //error messages
        grtErrortxt.setOpacity(0);
        loaErrortxt.setOpacity(0);
        bollardNumberErrortxt.setOpacity(0);
        nrtErrortxt.setOpacity(0);
        dwtErrortxt.setOpacity(0);
        beamErrortxt.setOpacity(0);
        draftFwdErrortxt.setOpacity(0);
        draftAftErrortxt.setOpacity(0);

        //FIXME (try to add labels under each field that are set to opacity 0, pops out every time 'add' is clicked and a field has an invalid input

        String dname = null;
        if (txt_name.getText().equals(null) || txt_name.getText().equals("")){
            vesselNametxt.setOpacity(1);
        }else{
            dname = new String(txt_name.getText());
        }
        String dvoyage = null;
        if (txt_voyage.getText().equals(null) || txt_voyage.getText().equals("")){
            voyageNumbertxt.setOpacity(1);
        }else{
            dvoyage = new String(txt_voyage.getText());
        }
        String dnationality = null;
        if (nation.getText().equals(null) || nation.getText().equals("")){
            nationalitytxt.setOpacity(1);
        }else{
            dnationality = new String(nation.getText());
        }
        //check if float value
        Float dgrt = null;
        if (txt_grt.getText().equals(null) || txt_grt.getText().equals("")){
            grttxt.setOpacity(1);
            grtErrortxt.setOpacity(1);
        }else{
            try{
                dgrt = Float.valueOf(new String(txt_grt.getText()));
            }catch(Exception e){
                grttxt.setOpacity(1);
                grtErrortxt.setOpacity(1);
            }
        }

        Float dloa = null;

        if (txt_loa.getText().equals(null) || txt_loa.getText().equals("")){
            loatxt.setOpacity(1);
            loaErrortxt.setOpacity(1);
        }else{
            try{
                dloa = Float.valueOf(new String(txt_loa.getText()));
            }catch(Exception e){
                loatxt.setOpacity(1);
                loaErrortxt.setOpacity(1);
            }
        }

        String dlp = null;

        if (txt_lp.getText().equals(null) || txt_lp.getText().equals("")){
            lastPorttxt.setOpacity(1);
        }else{
            dlp = new String(txt_lp.getText());
        }
        String dnp = null;
        if (txt_np.getText().equals(null) || txt_np.getText().equals("")){
            nextPorttxt.setOpacity(1);
        }else{
            dnp = new String(txt_np.getText());
        }

        String dberth = null;
        if (txt_berth.getText().equals(null) || txt_berth.getText().equals("")){
            berthNumbertxt.setOpacity(1);
        }else{
            dberth = new String(txt_berth.getText());
        }

        String dbollard = null;
        if (txt_bollard.getText().equals(null) || txt_bollard.getText().equals("")){
            bollardNumbertxt.setOpacity(1);
            bollardNumberErrortxt.setOpacity(1);
        }else{
            dbollard = new String(txt_bollard.getText());
            //bollardNumberErrortxt.setOpacity(1);
        }

        String dmaster = null;
        if (txt_master.getText().equals(null) || txt_master.getText().equals("")){
            mastertxt.setOpacity(1);
        }else{
            dmaster = new String(txt_master.getText());
        }


        Float dnrt = null;
        if (txt_nrt.getText().equals(null) || txt_nrt.getText().equals("")){
            nrttxt.setOpacity(1);
            nrtErrortxt.setOpacity(1);
        }else{
            try{
                dnrt = Float.valueOf(new String(txt_nrt.getText()));
            }catch(Exception e){
                nrttxt.setOpacity(1);
                nrtErrortxt.setOpacity(1);
            }
        }

        Float ddwt = null;
        if (txt_dwt.getText().equals(null) || txt_dwt.getText().equals("")){
            dwttxt.setOpacity(1);
            dwtErrortxt.setOpacity(1);
        }else{
            try{
                ddwt = Float.valueOf(new String(txt_dwt.getText()));
            }catch(Exception e){
                dwttxt.setOpacity(1);
                dwtErrortxt.setOpacity(1);
            }
        }

        Float dbeam = null;
        if (txt_beam.getText().equals(null) || txt_beam.getText().equals("")){
            beamtxt.setOpacity(1);
            beamErrortxt.setOpacity(1);
        }else{
            try{
                dbeam = Float.valueOf(new String(txt_beam.getText()));
            }catch(Exception e){
                beamtxt.setOpacity(1);
                beamErrortxt.setOpacity(1);
            }
        }

        Timestamp deta = null;

        if (etaDate.getValue().equals(null) || etaDate.getValue().equals("") || etaHour.getValue().equals(null) || etaHour.getValue().equals("")){
            etatxt.setOpacity(1);
        }else{
            String etaTemp = String.format(etaDate.getValue()+" "+etaHour.getValue()+":"+etaMinute.getValue()+":00");
            System.out.println(etaTemp);
            deta = Timestamp.valueOf(new String(etaTemp));
        }


        Timestamp detd = null;

        if (etdDate.getValue().equals(null) || etdDate.getValue().equals("") || etdHour.getValue().equals(null) || etdHour.getValue().equals("")){
            etdtxt.setOpacity(1);
        }else{
            String etdTemp = String.format(etdDate.getValue()+" "+etdHour.getValue()+":"+etdMinute.getValue()+":00");
            System.out.println(etdTemp);
            detd = Timestamp.valueOf(new String(etdTemp));
        }

        /*
        Timestamp deta = null;
        if (txt_eta.getText().equals(null) || txt_eta.getText().equals("")){
            etatxt.setOpacity(1);
        }else{
            deta = Timestamp.valueOf(new String(txt_eta.getText()));
        }

        Timestamp detd = null;

        if (txt_etd.getText().equals(null) || txt_etd.getText().equals("")){
            etdtxt.setOpacity(1);
        }else{
            detd = Timestamp.valueOf(new String(txt_etd.getText()));
        }
        */

        Float ddfwd = null;

        if (txt_dfwd.getText().equals(null) || txt_dfwd.getText().equals("")){
            draftFwdtxt.setOpacity(1);
            draftFwdErrortxt.setOpacity(1);
        }else{
            try{
                ddfwd = Float.valueOf(new String(txt_dfwd.getText()));
            }catch(Exception e){
                draftFwdtxt.setOpacity(1);
                draftFwdErrortxt.setOpacity(1);
            }
        }

        Float ddaft = null;
        if (txt_daft.getText().equals(null) || txt_daft.getText().equals("")){
            draftAfttxt.setOpacity(1);
            draftAftErrortxt.setOpacity(1);
        }else{
            try{
                ddaft = Float.valueOf(new String(txt_daft.getText()));
            }catch(Exception e){
                draftAfttxt.setOpacity(1);
                draftAftErrortxt.setOpacity(1);
            }
        }

        String dberthpost = null;
        if (berthPosition.getValue().equals(null) || berthPosition.getValue().equals("")){
            berthNumbertxt.setOpacity(1);
        }else{
            dberthpost = new String(berthPosition.getValue());
        }

        String dremarks = new String(txt_remarks.getText());
        String dfilled = new String(fill.getValue());




        Integer dvalidity = null;

        if (dfilled == "Passenger"){
            dvalidity = new Integer(validity.getText().toString());
        }else{
            dvalidity = 1;
        }
        boolean flag = false;


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            for(int i = 0; i < dvalidity; i++){
                preparedStatement = connection.prepareStatement("INSERT INTO ship (vessel_name, voyage_no, nationality, GRT, " +
                        "LOA, last_port, next_port, berth_pref, master, NRT, DWT, beam, ETA, ETD, draft_fwd, draft_aft, berth_post," +
                        "bollard, remarks, liner)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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

                flag = preparedStatement.execute();

                Calendar cal = Calendar.getInstance();
                cal.setTime(deta);
                cal.add(Calendar.DATE, 1);
                deta.setTime(cal.getTime().getTime());

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(detd);
                cal2.add(Calendar.DATE, 1);
                detd.setTime(cal.getTime().getTime());
            }


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
                nation.clear();
                txt_grt.clear();
                txt_loa.clear();
                txt_lp.clear();
                txt_np.clear();
                txt_berth.clear();
                txt_master.clear();
                txt_nrt.clear();
                txt_dwt.clear();
                txt_beam.clear();
                txt_berthingpost.clear();
                txt_bollard.clear();
                txt_remarks.clear();
                validity.clear();

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
        ETA.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        ETD.setCellValueFactory(new PropertyValueFactory<>("ETD"));

        recentLogs.setItems(data);

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

    //FIXME add a function that when the table is click info are put on the field (easier job for the secretary)
    private void setCellValueTextfield(){
        System.out.println("I was called??");
        recentLogs.setOnMouseClicked(event -> {
            Ship sp = recentLogs.getItems().get(recentLogs.getSelectionModel().getSelectedIndex());
            txt_name.setText(sp.getVessel_name());
            txt_voyage.setText(sp.getVoyage_num());
            nation.setText(sp.getNationality());
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
            //txt_eta.setText(String.valueOf(sp.getETA()));
            //txt_etd.setText(String.valueOf(sp.getETD()));
            txt_lp.setText(sp.getLast_port());
            txt_np.setText(sp.getNext_port());
            //txt_berthingpost.setText(sp.getBerth_post());
            berthPosition.setValue(sp.getBerth_post());
            //txt_remarks.setText(sp.getRemarks());
        });
    }


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
