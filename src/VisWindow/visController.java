package VisWindow;

import DBWindow.ConnectionConfiguration;
import DBWindow.Ship;
import DBWindow.dbController;
import RepWindow.repController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;



public class visController implements Initializable{

    private static final double DIST = 17; //distance per bollard
    private static final double WIDTH = 20; // width sa barko
    private static final double INIT_DIST = 27; //distance from first bollard and leftmost part sa map
    //	private static final double FIRST_BOL1 = 33;// first bollard number for current map
    private static final double FIRST_BOL2A1 = 27;

    @FXML
    private Pane visPane;
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
    private Button realTimeButton;
    @FXML
    private Button snapButton;
    @FXML
    private Button queryButton;
    @FXML
    private ComboBox hourFrom;
    @FXML
    private ComboBox hourTo;
    @FXML
    private ComboBox minuteFrom;
    @FXML
    private ComboBox minuteTo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label to;
    @FXML
    private Canvas canvas1;
    @FXML
    private Canvas canvas2;
    @FXML
    private Canvas canvas3;
    @FXML
    private Label visualTimer;
    @FXML
    private TableView<Ship> tableShip;
    @FXML
    private TableColumn<Ship, String> Name;
    @FXML
    private TableColumn<Ship, Timestamp> ETA;
    @FXML
    private TableColumn<Ship, Timestamp> ETD;

    private ObservableList<Ship> data = FXCollections.observableArrayList();
    private ObservableList<ShipModel> onScreenShip = FXCollections.observableArrayList();

    private String nextScene;

    private MouseGestures mg = new MouseGestures();

    private Timeline timeline;



    /*
    Logic flow in visual

          Set default to Real Time

            -> Get data from current date
            -> Display all ships that are currently on dock
                    -> highlight the tableview

          If set to snap
            -> Stop real time
            -> get Ships from that snap
                display

     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        hourFrom.setOpacity(0);
        hourTo.setOpacity(0);
        minuteFrom.setOpacity(0);
        minuteTo.setOpacity(0);
        queryButton.setOpacity(0);
        to.setOpacity(0);
        datePicker.setOpacity(0);

        realTimeButton.setStyle("-fx-background-color: #001e25; ");

        hourFrom.getItems().removeAll(hourFrom.getItems());
        hourFrom.getItems().addAll("00","01", "02","03", "04","05","06","07","08","09","10","11","12",
                "13","14","15","16","17","18","19","20","21","22","23");
        minuteFrom.getItems().removeAll(minuteFrom.getItems());
        minuteFrom.getItems().addAll("00", "01","02", "03", "04","05","06","07","08","09","10","11","12",
                "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59");

        hourTo.getItems().removeAll(hourTo.getItems());
        hourTo.getItems().addAll("00","01", "02","03", "04","05","06","07","08","09","10","11","12",
                "13","14","15","16","17","18","19","20","21","22","23");
        minuteTo.getItems().removeAll(minuteTo.getItems());
        minuteTo.getItems().addAll("00", "01","02", "03", "04","05","06","07","08","09","10","11","12",
                "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59");
    }

    public void RealTime(){
        hourFrom.setOpacity(0);
        hourTo.setOpacity(0);
        minuteFrom.setOpacity(0);
        minuteTo.setOpacity(0);
        queryButton.setOpacity(0);
        to.setOpacity(0);
        datePicker.setOpacity(0);
        snapButton.setStyle("-fx-background-color: #37617b;");
        realTimeButton.setStyle("-fx-background-color: #001e25; ");
        setTime();
    }

    public void SnapTime(){
        hourFrom.setOpacity(1);
        hourTo.setOpacity(1);
        minuteFrom.setOpacity(1);
        minuteTo.setOpacity(1);
        queryButton.setOpacity(1);
        to.setOpacity(1);
        datePicker.setOpacity(1);
        realTimeButton.setStyle("-fx-background-color: #37617b;");
        snapButton.setStyle("-fx-background-color: #001e25; ");
        timeline.stop();
        reset();
        visualTimer.setText("yyyy-MM-dd HH:mm:ss");
    }

    public void setTime(){
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> perSecondChecking()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void perSecondChecking() {
        searchFilter("Real Time");
    }

    public void availability(){
        //FIXME probable (probably lang naman) bug when ship has same slot time of the day stack it up

        if (hourFrom.getValue() == null || minuteFrom.getValue() == null || hourTo.getValue() == null || minuteTo.getValue() == null || datePicker.getValue() == null){

        }else{
            reset();
            searchFilter("Snap Time");
        }
    }

    public void searchFilter(String dateType){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Timestamp timestamp1;
        Timestamp timestamp2;

        if (dateType.equalsIgnoreCase("Real Time")){
            visualTimer.setText(dateFormat.format(date));
            timestamp1 = Timestamp.valueOf(dateFormat.format(date));
            timestamp2 = Timestamp.valueOf(dateFormat.format(date));

        }else{
            String t1 = datePicker.getValue() + " " + hourFrom.getValue() + ":" +
                    minuteFrom.getValue().toString() + ":" + "00";

            String t2 = datePicker.getValue() + " " + hourTo.getValue() + ":" +
                    minuteTo.getValue() + ":" + "00";

            timestamp1 = Timestamp.valueOf(t1);
            timestamp2 = Timestamp.valueOf(t2);
        }


        try {
            connection = ConnectionConfiguration.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE ETA <= ? and ETD >= ?");
            preparedStatement.setTimestamp(1, timestamp1);
            preparedStatement.setTimestamp(2, timestamp2);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                boolean flag = false;
                for (Ship s: data){
                    if (resultSet.getInt(1) == s.getId()){
                        flag = true;
                        break;
                    }
                }

                if (!flag){
                    data.add(new Ship(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getFloat(5),
                            resultSet.getFloat(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9),
                            resultSet.getString(10), resultSet.getFloat(11), resultSet.getFloat(12), resultSet.getFloat(13),
                            resultSet.getTimestamp(14), resultSet.getTimestamp(15), resultSet.getFloat(16), resultSet.getFloat(17),
                            resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), resultSet.getString(21)));
                }
            }

            for (Ship s: data){
                boolean flag = false;
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    if (resultSet.getInt(1) == s.getId()){
                        flag = true;
                        break;
                    }
                }

                if (!flag){
                    data.remove(s);
                }
            }

        }catch (SQLException e){
            System.err.println("Error"+e);
        }

        //FIXME Pane should be checked to where it should be drawn (remove this later)

        Pane pane = (Pane) canvas2.getParent();

        for (Ship s: data){
            boolean flag = false;
            for (ShipModel sm: onScreenShip){
                if (sm.getShip().getId() == s.getId()){
                    flag = true;
                    break;
                }
            }

            if (!flag){
                ArrayList values = appear(s.getBollard(),s.getBerth_post(), s.getBerth_pref());

                System.out.println(values.get(0) +" "+  values.get(1));
                //FIXME set X,Y
                ShipModel newShip = new ShipModel(s, (double) values.get(1), 250, (double)values.get(0), (double)25);
                onScreenShip.add(newShip);
                //FIXME (check first which panel to draw)
                pane.getChildren().add(newShip);
                mg.makeDraggable(newShip);
            }


        }

        for (ShipModel sm : onScreenShip){
            boolean flag = false;
            for (Ship s: data){
                if (s.getId() == sm.getShip().getId()){
                    flag = true;
                    break;
                }
            }

            if (!flag){
                onScreenShip.remove(sm);
                pane.getChildren().remove(sm);
            }
        }

        Name.setCellValueFactory(new PropertyValueFactory<>("vessel_name"));
        ETA.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        ETD.setCellValueFactory(new PropertyValueFactory<>("ETD"));


        System.out.println("\n\ndata size: " + data.size());
        tableShip.setItems(data);
        tableShip.refresh();
    }

    public void reset(){

        for (int i = 0; i != data.size(); ){
            data.remove(0);
        }

        for (int i = 0; i != onScreenShip.size(); ){
            Pane onPane = (Pane)onScreenShip.get(0).getParent();   // line to get the Pane a ship is drawn
            onPane.getChildren().remove(onScreenShip.get(0));
            onScreenShip.remove(0);
        }

        tableShip.setItems(data);
        tableShip.refresh();
    }

    //applying listener to each ship
    public static class MouseGestures {

        void makeDraggable(ShipModel shipModel) {
            shipModel.setOnMousePressed(shipOnMousePressedEventHandler);
        }

        EventHandler<MouseEvent> shipOnMousePressedEventHandler = t -> {

            ShipModel ship = (ShipModel) t.getSource();
            System.out.println("pressed");
            System.out.println(ship.getShip().getVessel_name());

            InfoWindow.Display(ship);

        };

    }

    //getting the values for berth and bollard
    public ArrayList<Double> appear(String bollardVal, String orientation, String berthprefVal) {
        ArrayList<Double> bollard = parseBollard(bollardVal);
        ArrayList<Double> berthpref = parseBerth(berthprefVal);
        double shipSize = 0;
        double layoutX = 0;
        System.out.print("berth :\t\t");
        for(Double d: berthpref) {
            System.out.print(d + " ");
        }
        System.out.println();

        System.out.print("bollard :\t");
        for(Double d: bollard) {
            System.out.print(d + " ");
        }
        System.out.println();

        if(bollard.size() == 1) {
            shipSize = DIST * bollard.get(0);

        } else if(bollard.size() == 2) {
            shipSize = DIST * (bollard.get(1) - bollard.get(0));
        }

        layoutX = INIT_DIST + (DIST * (bollard.get(0) - FIRST_BOL2A1));

        //plot ship to map
        System.out.println("shipSize :\t" + shipSize);
        System.out.println("layoutX :\t" + layoutX);
        System.out.println("orientation :\t" + orientation);

        ArrayList<Double> ret = new ArrayList<>();
        ret.add(shipSize);
        ret.add(layoutX);

        return ret;
    }

    //parse for berth
    public ArrayList<Double> parseBerth(String strVal){
        ArrayList<Double> arrVal = new ArrayList<Double>();
        String from = " " , to = " ";

        for(int i = 0; i < strVal.length(); i++){
            if(strVal.charAt(i) >= '0' && strVal.charAt(i) <= '9'){
                from += strVal.charAt(i);
            } else if(strVal.charAt(i) == '/') {
                i++;
                for(int j = i; j < strVal.length(); j++) {
                    if(strVal.charAt(j) >= '0' && strVal.charAt(j) <= '9'){
                        to += strVal.charAt(j);
                    }
                }
                break;

            }
        }

        //parse store print

        if(from != null) {
            arrVal.add(Double.parseDouble(from));
            if(to != " ") {
                arrVal.add(Double.parseDouble(to));
            }
        }

        for(Double  d: arrVal) {
            System.out.print(d + " ");

        }
        System.out.println();
        return arrVal;
    }

    // parse for bollard
    public ArrayList<Double> parseBollard(String strVal){
        ArrayList<Double> arrVal = new ArrayList<Double>();
        String from = " " , to = " ";

        for(int i = 0; i < strVal.length(); i++){
            if(strVal.charAt(i) >= '0' && strVal.charAt(i) <= '9' || strVal.charAt(i) == '.'){
                from += strVal.charAt(i);
            } else if(strVal.charAt(i) == '-' || strVal.charAt(i) == '/') {
                i++;
                for(int j = i; j < strVal.length(); j++) {
                    if(strVal.charAt(j) >= '0' && strVal.charAt(j) <= '9' || strVal.charAt(j) == '.'){
                        to += strVal.charAt(j);
                    }
                }
                break;

            }
        }


        //parse store print

        if(from != null) {
            arrVal.add(Double.parseDouble(from));
            if(to != " ") {
                arrVal.add(Double.parseDouble(to));
            }
        }

        for(Double  d: arrVal) {
            System.out.print(d + " ");

        }
        System.out.println();
        return arrVal;

    }

    private void loadNextScreen() throws IOException {

        ConnectionConfiguration connect = new ConnectionConfiguration();

        Parent newWindow = null;

        switch (nextScene) {
            case "Application":
                newWindow = FXMLLoader.load(getClass().getResource("../AppWindow/appWindow.fxml"));
                break;
            case "Report": {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../RepWindow/repWindow.fxml"));
                newWindow = loader.load();
                repController reportController = loader.getController();
                //FIXME
                //reportController.loadFromDatabase();
                break;
            }
            case "Database": {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../DBWindow/dbWindow.fxml"));
                newWindow = loader.load();
                dbController dbController = loader.getController();
                dbController.loadDataFromDatabase();
                break;
            }
            case "HomeButton":
                newWindow = FXMLLoader.load(getClass().getResource("../HomeWindow/homeWindow.fxml"));
                break;
        }



        Scene newScene = new Scene(newWindow);
        Stage window = (Stage) visPane.getScene().getWindow();
        window.setFullScreenExitHint("");
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setScene(newScene);
        window.setFullScreen(true);
        window.show();
    }

    public void changeScreenButton(javafx.event.ActionEvent event) throws IOException, InterruptedException {

        if (event.getSource() == homeButton){
            nextScene = "HomeButton";
        } else if (event.getSource() == appButton){
            nextScene = "Application";
        } else if (event.getSource() == dbButton){
            nextScene = "Database";
        } else if (event.getSource() == repButton){
            nextScene = "Report";
        }

        loadNextScreen();

    }

}


