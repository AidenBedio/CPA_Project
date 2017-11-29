package RepWindow;

import DBWindow.ConnectionConfiguration;
import DBWindow.Ship;
import DBWindow.dbController;
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
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class repController implements Initializable{

    @FXML
    private Pane repPane;
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

    private String nextScene;

    @FXML
    private TableView<Ship> tableShip;
    @FXML
    private TableColumn<Ship, String> Berth_No;
    @FXML
    private TableColumn<Ship, String> Bollard_No;
    @FXML
    private TableColumn<Ship, String> Name;
    @FXML
    private TableColumn<Ship, Timestamp> ETD;
    @FXML
    private TableColumn<Ship, Timestamp> ETA;
    @FXML
    private TableColumn<Ship, String> LastPort;
    @FXML
    private TableColumn<Ship, String> NextPort;
    @FXML
    private TableColumn<Ship, String> Remarks;
    @FXML
    private Button Save;
    @FXML
    private DatePicker datePicker;

    private ObservableList<Ship> data;
    private ObservableList<Ship> datab;
    private ObservableList<Ship> search;
    private ConnectionConfiguration connect;

    private void loadNextScreen() throws IOException {

        ConnectionConfiguration connect = new ConnectionConfiguration();

        Parent newWindow = null;

        switch (nextScene) {
            case "Application":
                newWindow = FXMLLoader.load(getClass().getResource("../AppWindow/appWindow.fxml"));
                break;
            case "Visual": {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../VisWindow/visWindow.fxml"));
                newWindow = loader.load();
                visController vController = loader.getController();
                vController.setTime();
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
        Stage window = (Stage) repPane.getScene().getWindow();
        window.setFullScreenExitHint("");
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setScene(newScene);
        window.setFullScreen(true);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //repPane.setOpacity(0);
        connect = new ConnectionConfiguration();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        datePicker.setValue(localDate);
    }

    public void changeScreenButton(javafx.event.ActionEvent event) throws IOException, InterruptedException {

        if (event.getSource() == homeButton){
            nextScene = "HomeButton";
        } else if (event.getSource() == appButton){
            nextScene = "Application";
        } else if (event.getSource() == dbButton){
            nextScene = "Database";
        } else if (event.getSource() == visButton){
            nextScene = "Visual";
        }

        loadNextScreen();

    }


    @FXML
    public void loadFromDatabase(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date();
        Timestamp timestamp = Timestamp.valueOf(dateFormat.format(date));
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        try {
            Connection connection = connect.getConnection();
            data = FXCollections.observableArrayList();
            preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE ETA <= ? and ETD >= ?");
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setTimestamp(2, timestamp);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                data.add(new Ship(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getFloat(5),
                        resultSet.getFloat(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9),
                        resultSet.getString(10), resultSet.getFloat(11), resultSet.getFloat(12), resultSet.getFloat(13),
                        resultSet.getTimestamp(14), resultSet.getTimestamp(15), resultSet.getFloat(16), resultSet.getFloat(17),
                        resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), resultSet.getString(21)));
            }
        }catch (SQLException e){
            System.err.println("Error"+e);
        }
        System.out.println();
        Berth_No.setCellValueFactory(new PropertyValueFactory<>("berth_pref"));
        Bollard_No.setCellValueFactory(new PropertyValueFactory<>("bollard"));
        Name.setCellValueFactory(new PropertyValueFactory<>("vessel_name"));
        ETD.setCellValueFactory(new PropertyValueFactory<>("ETD"));
        ETA.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        LastPort.setCellValueFactory(new PropertyValueFactory<>("last_port"));
        NextPort.setCellValueFactory(new PropertyValueFactory<>("next_port"));
        //Remarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));


        tableShip.setItems(null);
        tableShip.setItems(data);

    }

    @FXML
    private void filter(ActionEvent event){
        try{
            datePicker.getValue().toString();
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Save unsuccessful! Pick a date");
            alert.showAndWait();
        }
        String s = datePicker.getValue().toString();
        String timestamp = String.format("%s 00:00:00", s);
        String time = String.format("%s 23:59:59", s);
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        try {
            Connection connection = connect.getConnection();
            search = FXCollections.observableArrayList();
            preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE ((ETA <= ? and ETD >= ?) OR (ETA >= ? and ETA <= ?))");
            preparedStatement.setString(1, timestamp);
            preparedStatement.setString(2, timestamp);
            preparedStatement.setString(3, timestamp);
            preparedStatement.setString(4, time);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                search.add(new Ship(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getFloat(5),
                        resultSet.getFloat(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9),
                        resultSet.getString(10), resultSet.getFloat(11), resultSet.getFloat(12), resultSet.getFloat(13),
                        resultSet.getTimestamp(14), resultSet.getTimestamp(15), resultSet.getFloat(16), resultSet.getFloat(17),
                        resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), resultSet.getString(21)));
            }
        }catch (SQLException e){
            System.err.println("Error"+e);
        }
        System.out.println();
        Berth_No.setCellValueFactory(new PropertyValueFactory<>("berth_pref"));
        Bollard_No.setCellValueFactory(new PropertyValueFactory<>("bollard"));
        Name.setCellValueFactory(new PropertyValueFactory<>("vessel_name"));
        ETA.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        ETD.setCellValueFactory(new PropertyValueFactory<>("ETD"));
        LastPort.setCellValueFactory(new PropertyValueFactory<>("last_port"));
        NextPort.setCellValueFactory(new PropertyValueFactory<>("next_port"));
        //Remarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        tableShip.setItems(null);
        tableShip.setItems(search);
    }


    @FXML
    private void saving(ActionEvent event) {
        try {
            datePicker.getValue().toString();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Save unsuccessful! Pick a date");
            alert.showAndWait();
        }
        String s = datePicker.getValue().toString();
        String timestamp = String.format("%s 00:00:00", s);
        String time = String.format("%s 23:59:59", s);
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        try {
            Connection connection = connect.getConnection();
            datab = FXCollections.observableArrayList();
            preparedStatement = connection.prepareStatement("SELECT * FROM ship WHERE ((ETA <= ? and ETD >= ?) OR (ETA >= ? and ETA <= ?))");
            preparedStatement.setString(1, timestamp);
            preparedStatement.setString(2, timestamp);
            preparedStatement.setString(3, timestamp);
            preparedStatement.setString(4, time);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                datab.add(new Ship(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getFloat(5),
                        resultSet.getFloat(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9),
                        resultSet.getString(10), resultSet.getFloat(11), resultSet.getFloat(12), resultSet.getFloat(13),
                        resultSet.getTimestamp(14), resultSet.getTimestamp(15), resultSet.getFloat(16), resultSet.getFloat(17),
                        resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), resultSet.getString(21)));
            }
        } catch (SQLException e) {
            System.err.println("Error" + e);
        }
        System.out.println();
        Berth_No.setCellValueFactory(new PropertyValueFactory<>("berth_pref"));
        Bollard_No.setCellValueFactory(new PropertyValueFactory<>("bollard"));
        Name.setCellValueFactory(new PropertyValueFactory<>("vessel_name"));
        ETA.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        ETD.setCellValueFactory(new PropertyValueFactory<>("ETD"));
        LastPort.setCellValueFactory(new PropertyValueFactory<>("last_port"));
        NextPort.setCellValueFactory(new PropertyValueFactory<>("next_port"));
        //Remarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        tableShip.setItems(null);
        tableShip.setItems(datab);

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();
            XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("Berth Number");
            row.createCell(1).setCellValue("Bollard Number");
            row.createCell(2).setCellValue("Vessel Name");
            row.createCell(3).setCellValue("ETA");
            row.createCell(4).setCellValue("ETD");
            row.createCell(5).setCellValue("Last Port");
            row.createCell(6).setCellValue("Next Port");
            for (int i = 0; i < 7; i++) {
                CellStyle styleHeading = workbook.createCellStyle();
                XSSFFont font = workbook.createFont();
                font.setBold(true);
                font.setFontName(HSSFFont.FONT_ARIAL);
                font.setFontHeightInPoints((short) 10);
                styleHeading.setFont(font);
                row.getCell(i).setCellStyle(styleHeading);
            }

            for (int i = 0; i < tableShip.getItems().size(); i++) {
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(tableShip.getItems().get(i).getBerth_pref());
                row.createCell(1).setCellValue(tableShip.getItems().get(i).getBollard());
                row.createCell(2).setCellValue(tableShip.getItems().get(i).getVessel_name());
                row.createCell(3).setCellValue(tableShip.getItems().get(i).getETA().toString());
                row.createCell(4).setCellValue(tableShip.getItems().get(i).getETD().toString());
                row.createCell(5).setCellValue(tableShip.getItems().get(i).getLast_port());
                row.createCell(6).setCellValue(tableShip.getItems().get(i).getNext_port());
                System.out.println(row.getCell(3).toString());
                System.out.println(tableShip.getItems().get(i).getETD());
            }

            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream fos = null;

            fos = new FileOutputStream(new File("E:/Angkla/DailyReport(" + s + ").xlsx"));
            workbook.write(fos);
            fos.close();
            fos.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Saved Successfully");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
