package VisWindow;

import AppWindow.appController;
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
import javafx.geometry.HorizontalDirection;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    private static final double DIST = 16; //distance per bollard
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
    private Canvas canvas4;
    @FXML
    private Canvas canvas5;
    @FXML
    private Canvas canvas6;
    @FXML
    private Canvas canvas7;
    @FXML
    private Canvas canvas8;
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

        hourFrom.setDisable(true);
        hourTo.setDisable(true);
        minuteFrom.setDisable(true);
        minuteTo.setDisable(true);
        queryButton.setDisable(true);
        datePicker.setDisable(true);

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

        hourFrom.setDisable(false);
        hourTo.setDisable(false);
        minuteFrom.setDisable(false);
        minuteTo.setDisable(false);
        queryButton.setDisable(false);
        datePicker.setDisable(false );

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
            if (dateType.equalsIgnoreCase("Real Time")){
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE ETA <= ? and ETD >= ?");
                preparedStatement.setTimestamp(1, timestamp1);
                preparedStatement.setTimestamp(2, timestamp2);
            }else{
                preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE ((ETA <= ? and ETD >= ?) OR (ETA >= ? and ETA <= ?))");
                preparedStatement.setTimestamp(1, timestamp1);
                preparedStatement.setTimestamp(2, timestamp1);
                preparedStatement.setTimestamp(3, timestamp1);
                preparedStatement.setTimestamp(4, timestamp2);
                //preparedStatement.setTimestamp(5, timestamp1);
                //preparedStatement.setTimestamp(6, timestamp2);
            }
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
        }finally{
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

        //FIXME Pane should be checked to where it should be drawn (remove this later)

//        Pane pane = (Pane) canvas2.getParent();

        for (Ship s: data){
            boolean flag = false;
            for (ShipModel sm: onScreenShip){
                if (sm.getShip().getId() == s.getId()){
                    flag = true;
                    break;
                }
            }

            if (!flag){
                CreateShip(s);
//                ArrayList values = appear(s.getBollard(),s.getBerth_post(), s.getBerth_pref());
//
//                System.out.println(values.get(0) +" "+  values.get(1));
//                //FIXME set X,Y
//                ShipModel newShip = new ShipModel(s, (double) values.get(1), 250, (double)values.get(0), (double)25);
//                onScreenShip.add(newShip);
//                //FIXME (check first which panel to draw)
//                newShip.setImage(new Image("tryShip.png"));
//                double scale = 1;
//                int additional_bollardsx = 3;
//                int additional_bollardsy = 2;
////                newShip.setScaleX(scale + additional_bollardsx * .5);
////                newShip.setScaleY(scale + additional_bollardsy * .5);
////
////                newShip.setX(60 + (30/2 * (additional_bollardsx * .5)));
////                newShip.setY(300 - (30 * (additional_bollardsy * .5)));
//
//
//                newShip.setScaleX(scale + (additional_bollardsy * .5)) ;
//                newShip.setScaleY(scale + (additional_bollardsx * .5));
//                newShip.setRotate(90);
//
//                newShip.setX(60 + (30 * .5 * additional_bollardsx + 15));
//                newShip.setY(300 - ((30/2 * (additional_bollardsy * .5)) - 15));
//
//
//
////                System.out.println(newShip.getImage().toString());
//                pane.getChildren().add(newShip);
//                mg.makeDraggable(newShip);
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
                Pane onPane = (Pane)sm.getParent();   // line to get the Pane a ship is drawn
                onPane.getChildren().remove(onScreenShip.get(0));
            }
        }

        Name.setCellValueFactory(new PropertyValueFactory<>("vessel_name"));
        ETA.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        ETD.setCellValueFactory(new PropertyValueFactory<>("ETD"));


        System.out.println("\n\ndata size: " + data.size());
        tableShip.setItems(data);
        tableShip.refresh();
    }

    public void CreateShip(Ship s) {

        ShipModel newShip = new ShipModel(s);
        onScreenShip.add(newShip);
        String image;

        if (s.getFilled().equalsIgnoreCase("Liner")) {
            image = "liner.png";
        } else if (s.getFilled().equalsIgnoreCase("Cargo")) {
            image = "cargo.png";
        } else if (s.getFilled().equalsIgnoreCase("Tanker")) {
            image = "tanker.png";
        } else if (s.getFilled().equalsIgnoreCase("Fast Craft")) {
            image = "fastcraft.png";
        } else if (s.getFilled().equalsIgnoreCase("Tugs & Special")) {
            image = "special.png";
        } else if (s.getFilled().equalsIgnoreCase("Fishing")) {
            image = "fishing.png";
        } else if (s.getFilled().equalsIgnoreCase("Pleasure")) {
            image = "pleasure.png";
        } else {
            image = "unspecified.png";
        }

        if(s.getBerth_pref().equalsIgnoreCase("B-20 Tip")) {
            Allocation(newShip, canvas6, image, 32, s.getBerth_post(),
                    1, (double) 2.3 / 35, 162, (double) 165, (double) 167, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-20N Corner")){
            Allocation(newShip, canvas6, image, 32, "mediterranean",
                    1, (double) 11 / 35, 162, (double) 164, (double) 165, 0.1);

        } else if(s.getBerth_pref().contentEquals("B-20N1")){
            Allocation(newShip, canvas6, image, 32, "mediterranean",
                    1, (double) 8.8 / 35, 162, (double) 164, (double) 165, 0.1);

        }  else if(s.getBerth_pref().contentEquals("B-20N2")){
            Allocation(newShip, canvas6, image, 32, "mediterranean",
                    1, (double) 6.6 / 35, 162, (double) 164, (double) 165, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-20N Tip")){
            System.out.println("B-20N hereeeeeeeeeeeeeeeeee");
            Allocation(newShip, canvas6, image, 32, "mediterranean",
                    1, (double) 4.5 / 35, 162, (double) 164, (double) 165, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-20S Corner")){
            Allocation(newShip, canvas6, image, 32, "mediterranean",
                    1, (double) 9 / 35, 162, (double) 167, (double) 168, 1.0);

        /*} *//*else if(s.getBerth_pref().equalsIgnoreCase("B-20S Mid")) {
            Allocation(newShip, canvas6, image, 32,"mediterranean",
                    1, (double) 8.8 / 35, 162, (double) 167, (double) 168, 0.1);

        */}else if(s.getBerth_pref().equalsIgnoreCase("B-20S Tip")){
            Allocation(newShip, canvas6, image, 32, "mediterranean",
                    1, (double) 5.5 / 35, 162, (double) 167, (double) 168, 1.0);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-23 Tip")) {
            Allocation(newShip, canvas6, image, 32, s.getBerth_post(),
                    1, (double) 6.7 / 35, 175, (double) 196, (double) 198,0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-23N Corner")){
            Allocation(newShip, canvas6, image, 32, "mediterranean",
                    1, (double) 11 / 35, 175, (double) 195, (double) 196, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-23N Tip")){
            Allocation(newShip, canvas6, image, 32, "mediterranean",
                    1, (double) 8.9 / 35, 175, (double) 195, (double) 196, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-23S Corner")){
            Allocation(newShip, canvas6, image, 32, "mediterranean",
                    1, (double) 11 / 35, 175, (double) 198, (double) 199, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-23S Tip")) {
            Allocation(newShip, canvas6, image, 32,"mediterranean",
                    1, (double) 8.9/ 35, 175, (double) 198, (double) 199, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-26 Tip")) {
            Allocation(newShip, canvas7, image, 32, s.getBerth_post(),
                    1, (double)  4.5 / 35, 218, (double) 229, (double) 231, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-26N Corner")) {
            Allocation(newShip, canvas7, image, 32, "mediterranean",
                    1, (double) 11 / 35, 218, (double) 228, (double) 229, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-26N Mid")){
            Allocation(newShip, canvas7, image, 32, "mediterranean",
                    1, (double) 8.8 / 35, 218, (double) 228, (double) 229, 0.1);

      /*  } else if(s.getBerth_pref().equalsIgnoreCase("B-26N2")){
            Allocation(newShip, canvas7, image, 32, "mediterranean",
                    1, (double) 6.6 / 35, 218, (double) 228, (double) 229, 0.1);

       */ } else if(s.getBerth_pref().equalsIgnoreCase("B-26N Tip")) {
            Allocation(newShip, canvas7, image, 32, "mediterranean",
                    1, (double) 6.6 / 35, 218, (double) 228, (double) 229, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-26S Corner")) {
            Allocation(newShip, canvas7, image, 32, "mediterranean",
                    1, (double) 11 / 35, 218, (double) 231, (double) 232, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-26S Mid")) {
            Allocation(newShip, canvas7, image, 32, "mediterranean",
                    1, (double) 8.8 / 35, 218, (double) 231, (double) 232, 0.1);

        } else if(s.getBerth_pref().equalsIgnoreCase("B-26S Tip")){
            Allocation(newShip, canvas7, image, 32, "mediterranean",
                    1, (double) 6.6 / 35, 218, (double) 231, (double) 232, 0.1);
        } else {
            ArrayList bollard = parseBollard(s.getBollard());

            if (bollard.size() == 2) {

                if ((double) bollard.get(0) >= 1 && (double) bollard.get(1) <= 33) {

                    Allocation(newShip, canvas1, image, 35, s.getBerth_post(),
                            2, (double) 11 / 35, 1, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                    if ((double) bollard.get(0) >= 30 && (double) bollard.get(1) <= 33) {

                        ShipModel shipCopy = ShipModel.newInstance(newShip);
                        onScreenShip.add(shipCopy);

                        Allocation(shipCopy, canvas2, image, 32, s.getBerth_post(),
                                1, (double) 11 / 35, 30, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);
                    }

                } else if ((double) bollard.get(0) >= 30 && (double) bollard.get(1) <= 44) {

                    Allocation(newShip, canvas2, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 30, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                } else if ((double) bollard.get(0) >= 48 && (double) bollard.get(1) <= 64) {

                    Allocation(newShip, canvas2, image, 32, s.getBerth_post(),
                            1, (double) 15 / 34, 34, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                    if ((double) bollard.get(0) >= 60 && (double) bollard.get(1) <= 64) {

                        ShipModel shipCopy = ShipModel.newInstance(newShip);
                        onScreenShip.add(shipCopy);

                        Allocation(shipCopy, canvas3, image, 32, s.getBerth_post(),
                                1, (double) 11 / 35, 60, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                    }

                } else if ((double) bollard.get(0) >= 60 && (double) bollard.get(1) <= 90) {
                    Allocation(newShip, canvas3, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 60, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                    if ((double) bollard.get(0) >= 86 && (double) bollard.get(1) <= 90) {
                        ShipModel shipCopy = ShipModel.newInstance(newShip);
                        onScreenShip.add(shipCopy);

                        Allocation(shipCopy, canvas4, image, 32, s.getBerth_post(),
                                1, (double) 11 / 35, 86, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);
                    }

                } else if ((double) bollard.get(0) >= 86 && (double) bollard.get(1) <= 107) {
                    Allocation(newShip, canvas4, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 86, (double) bollard.get(0), (double) bollard.get(1),(newShip.getShip().getLOA() / 16) - 3);

                } else if ((double) bollard.get(0) >= 108 && (double) bollard.get(1) <= 117) {
                    Allocation(newShip, canvas4, image, 32, s.getBerth_post(),
                            1, (double) 12 / 35, 87, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                    if ((double) bollard.get(0) >= 114 && (double) bollard.get(1) <= 117) {
                        ShipModel shipCopy = ShipModel.newInstance(newShip);
                        onScreenShip.add(shipCopy);

                        Allocation(shipCopy, canvas5, image, 32, s.getBerth_post(),
                                1, (double) 11 / 35, 114, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);
                    }

                } else if ((double) bollard.get(0) >= 114 && (double) bollard.get(1) <= 129) {
                    Allocation(newShip, canvas5, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 114, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);
                } else if ((double) bollard.get(0) >= 151 && (double) bollard.get(1) <= 164) {
                    Allocation(newShip, canvas5, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 134, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                    if ((double) bollard.get(0) >= 162 && (double) bollard.get(1) <= 165) {
                        ShipModel shipCopy = ShipModel.newInstance(newShip);
                        onScreenShip.add(shipCopy);

                        Allocation(shipCopy, canvas6, image, 32, s.getBerth_post(),
                                1, (double) 11 / 35, 162, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);
                    }

                } else if ((double) bollard.get(0) >= 180 && (double) bollard.get(1) <= 196) {
                    Allocation(newShip, canvas6, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 175, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                } else if ((double) bollard.get(0) >= 213 && (double) bollard.get(1) <= 220) {
                    Allocation(newShip, canvas6, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 190, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                    if ((double) bollard.get(0) >= 218 && (double) bollard.get(1) <= 220) {
                        ShipModel shipCopy = ShipModel.newInstance(newShip);
                        onScreenShip.add(shipCopy);

                        Allocation(shipCopy, canvas7, image, 32, s.getBerth_post(),
                                1, (double) 11 / 35, 218, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);
                    }

                } else if ((double) bollard.get(0) >= 218 && (double) bollard.get(1) <= 229) {
                    Allocation(newShip, canvas7, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 218, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                } else if ((double) bollard.get(0) >= 248 && (double) bollard.get(1) <= 250) {
                    Allocation(newShip, canvas7, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 235, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                } else if ((double) bollard.get(0) >= 251 && (double) bollard.get(1) <= 260) {
                    Allocation(newShip, canvas7, image, 32, s.getBerth_post(),
                            1, (double) 12 / 35, 236, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                } else if ((double) bollard.get(0) >= 261 && (double) bollard.get(1) <= 262) {
                    Allocation(newShip, canvas7, image, 32, s.getBerth_post(),
                            1, (double) 11 / 35, 237, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                } else if ((double) bollard.get(0) >= 263 && (double) bollard.get(1) <= 267) {
                    Allocation(newShip, canvas7, image, 32, s.getBerth_post(),
                            1, (double) 12 / 35, 237, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);

                    if ((double) bollard.get(0) >= 265 && (double) bollard.get(1) <= 267) {
                        ShipModel shipCopy = ShipModel.newInstance(newShip);
                        onScreenShip.add(shipCopy);

                        Allocation(shipCopy, canvas8, image, 32, s.getBerth_post(),
                                6, (double) 12 / 35, 265, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);
                    }
                } else if ((double) bollard.get(0) >= 265 && (double) bollard.get(1) <= 272) {
                    Allocation(newShip, canvas8, image, 32, s.getBerth_post(),
                            6, (double) 12 / 35, 265, (double) bollard.get(0), (double) bollard.get(1), (newShip.getShip().getLOA() / 16) - 3);
                }
            }
        }
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

    public void Allocation(ShipModel newShip, Canvas canvas, String image, double divider, String berthPos,
                           double startOffsetX, double startOffsetY, double startingBollard, double bollardInit, double bollardEnd, double addBollard){
        /*
            newShip -> the shipmodel that is being processed
            canvas -> the canvas to which the ship will be drawn (not exactly, the canvas that points to the Pane to which the ship will be drawn)
            image -> the image url.
            divider -> number of lines per screen. Area 1 = 35, Area 2-8 = 32
            berthPos -> berthPosition if starboard, portside etc
            startOffsetX -> number of lines before the first bollard (eg. area1 = 2, area2 = 1)
            startOffsetY -> number of line before till the edge of the port
            startingBollard -> literal number of the first bollard on the area
            bollardInit -> the min value of the bollard range (bollards 13 - 17)  bollardInit = 13
            bollardEnd -> the max value of the bollard range (bollards 13 - 17)  bollardEnd = 17

         */
        double baseX;
        double baseY;
        double addBollardX;
        double addBollardY;
        double offsetX;
        double offsetY;

        newShip.setImage(new Image(image));
        Pane pane = (Pane) canvas.getParent();
        double scale = 950/divider;
        baseX = scale * startOffsetX;
        baseY = 950 * startOffsetY;
        System.out.println("startOffsetY:" + baseY);
        System.out.println(baseY);

        if (berthPos.equalsIgnoreCase("mediterranean")){
            offsetX = bollardInit - startingBollard;
            addBollardX = 0;
            addBollardY = addBollard;//(newShip.getShip().getLOA() / 16) - 2;

            newShip.setScaleX(1 + addBollardX * .5);
            newShip.setScaleY(1 + addBollardY * .5);

            newShip.setX(baseX + (scale * offsetX) + (scale/2 * (addBollardX * .5)));
            newShip.setY(baseY - (scale * (addBollardY * .5)));

        }else if (berthPos.equalsIgnoreCase("starboard")){
            newShip.setRotate(90);
            offsetX = bollardInit - startingBollard;
            //addBollardX = (bollardEnd - bollardInit - 2);
//            addBollardX = addBollard;
            addBollardX = addBollard;//(newShip.getShip().getLOA() / 16) - 3;
            addBollardY = 0;

            newShip.setScaleX(1 + addBollardY * .5);
            newShip.setScaleY(1 + addBollardX * .5);

            newShip.setX(baseX + (offsetX * scale) + (scale * .5 * addBollardX +(scale/2)));
            newShip.setY(baseY - ((scale/2 * (addBollardY * .5)) - (scale/2)));

        }else if (berthPos.equalsIgnoreCase("portside")){

            newShip.setRotate(270);
            offsetX = bollardInit - startingBollard;
            //addBollardX = (bollardEnd - bollardInit - 2);
            addBollardX = addBollard;//(newShip.getShip().getLOA()/16) - 3;
            addBollardY = 0;

            newShip.setScaleX(1 + addBollardY * .5);
            newShip.setScaleY(1 + addBollardX * .5);

            newShip.setX(baseX + (offsetX * scale) + (scale * .5 * addBollardX +(scale/2)));
            newShip.setY(baseY - ((scale/2 * (addBollardY * .5)) -(scale/2)));

        }else if (berthPos.equalsIgnoreCase("shipside")){
            newShip.setRotate(270);
            offsetX = bollardInit - startingBollard;
            //addBollardX = (bollardEnd - bollardInit - 2);
            addBollardX = addBollard;//(newShip.getShip().getLOA()/16) - 3;
            addBollardY = 0;

            newShip.setScaleX(1 + addBollardY * .5);
            newShip.setScaleY(1 + addBollardX * .5);

            newShip.setX(baseX + (offsetX * scale) + (scale * .5 * addBollardX +(scale/2)));
            newShip.setY(baseY - ((scale/2 * (addBollardY * .5)) -(scale/2)));
        }

        pane.getChildren().add(newShip);
        mg.makeDraggable(newShip);
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

           //InfoWindow.Info(ship);
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoWindow.fxml"));
                Parent root1 = (Parent) loader.load();

                System.out.println("skdfjoreiuoiruvndorh");

                InfoWindow infoController = loader.getController();

                infoController.Info(ship);

                Stage stage = new Stage();
                stage.setTitle("Ship Info");
                stage.setScene(new Scene(root1));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(ship.getScene().getWindow());
                stage.showAndWait();
            }   catch (Exception e){

            }

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
            if (d == -1.0){
                System.out.println(berthprefVal);
            }else{
                System.out.print(d + " ");
            }
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

        if (bollard.get(0) >= 48){
            layoutX = INIT_DIST + (DIST * (bollard.get(0) - FIRST_BOL2A1 - 4));
        }else {
            layoutX = INIT_DIST + (DIST * (bollard.get(0) - FIRST_BOL2A1));
        }

        //layoutX = INIT_DIST + (DIST * (bollard.get(0) - FIRST_BOL2A1));

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

        for(int i = 0; i < strVal.length(); i++)

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

            } else {
                if(i > 1)
                    from = "-1.0";

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
            case "Application":{
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
        try{

        }catch(Exception e){
            window.initStyle(StageStyle.TRANSPARENT);
            window.initStyle(StageStyle.UNDECORATED);
            //window.setFullScreen(true);
        }
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

    public void showLegend(javafx.event.ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Legend.fxml"));
            Parent root1 = (Parent) loader.load();

            Stage stage = new Stage();
            stage.setTitle("");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(homeButton.getScene().getWindow());
            stage.showAndWait();
        }   catch (Exception e){

        }
    }
}


