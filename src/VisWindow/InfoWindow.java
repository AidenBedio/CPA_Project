package VisWindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoWindow {


//    @FXML
//    private TextField txt_name;
//    @FXML
//    private TextField txt_voyage;
//    @FXML
//    private TextField txt_nationality;
//    @FXML
//    private TextField txt_grt;
//    @FXML
//    private TextField txt_loa;
//    @FXML
//    private TextField txt_berth;
//    @FXML
//    private TextField txt_bollard;
//    @FXML
//    private TextField txt_master;
//    @FXML
//    private TextField txt_nrt;
//    @FXML
//    private TextField txt_dwt;
//    @FXML
//    private TextField txt_beam;
//    @FXML
//    private TextField txt_eta;
//    @FXML
//    private TextField txt_etd;
//    @FXML
//    private TextField txt_dfwd;
//    @FXML
//    private TextField txt_daft;
//    @FXML
//    private TextField txt_lp;
//    @FXML
//    private TextField txt_np;
//    @FXML
//    private TextField txt_berthingpost;
//    @FXML
//    private TextField txt_remarks;
//    @FXML
//    private TextField txt_filled;

    public static void Display(ShipModel model){
        //Parent newWindow = null;
        Stage window = new Stage();
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoWindow.fxml"));
        window.initOwner(model.getScene().getWindow());
        window.setWidth(500);
        window.setHeight(700);

      Label  txt_name = new Label();
        Label txt_voyage = new Label();
        Label txt_nationality = new Label();
        Label txt_grt = new Label();
        Label txt_loa = new Label();
        Label txt_berth = new Label();
        Label txt_bollard = new Label();
        Label txt_master = new Label();
        Label txt_nrt = new Label();
        Label txt_dwt = new Label();
        Label txt_beam = new Label();
        Label txt_eta = new Label();
         Label txt_etd = new Label();
        Label txt_dfwd = new Label();
        Label txt_daft = new Label();
        Label txt_lp = new Label();
        Label txt_np = new Label();
        Label txt_berthingpost = new Label();
        Label txt_remarks = new Label();
        Label txt_filled = new Label();

        txt_name.setText("Vessel Name: " + model.getShip().getVessel_name());
        System.out.println(txt_name.getText() + " BACOOOOOOOOOOOOOOOOOOOOOOOOOOOONNSSS!!");
        txt_voyage.setText("Voyage Number:" + model.getShip().getVoyage_num());
        txt_nationality.setText("Nationality: " + model.getShip().getNationality());
        txt_grt.setText(String.valueOf("GRT: " + model.getShip().getGRT()));
        txt_loa.setText(String.valueOf("LOA: " + model.getShip().getLOA()));
        txt_lp.setText("Last Port: " + model.getShip().getLast_port());
        txt_np.setText("Next Port: " + model.getShip().getNext_port());
        txt_berth.setText("Berth: " + model.getShip().getBerth_pref());
        txt_bollard.setText("Bollard: " + model.getShip().getBollard());
        txt_master.setText("Master: " + model.getShip().getMaster());
        txt_nrt.setText("NRT: " + String.valueOf(model.getShip().getNRT()));
        txt_dwt.setText("DWT: " + String.valueOf(model.getShip().getDWT()));
        txt_beam.setText("Beam: " + String.valueOf(model.getShip().getBeam()));
        txt_eta.setText("ETA: " + String.valueOf(model.getShip().getETA()));
        txt_etd.setText("ETD: " + String.valueOf(model.getShip().getETD()));
        txt_dfwd.setText("Dfwd: " + String.valueOf(model.getShip().draft_fwdProperty()));
        txt_daft.setText("Daft: " + String.valueOf(model.getShip().getDraft_aft()));
        txt_berthingpost.setText("Orientation: " + model.getShip().getBerth_post());
        txt_filled.setText("Ship type: " + model.getShip().getFilled());
        txt_remarks.setText("Remarks: " + model.getShip().getRemarks());

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Ship Info");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(txt_name,txt_voyage, txt_nationality, txt_grt, txt_loa, txt_lp, txt_np, txt_berth, txt_bollard, txt_master, txt_nrt,
                txt_dwt, txt_beam, txt_eta, txt_etd, txt_dfwd, txt_daft, txt_berthingpost, txt_filled, txt_remarks);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);

        System.out.println("  RQWEQWEQW");
        System.out.println("  RQWEQWEQW");
        System.out.println("  RQWEQWEQW");
        System.out.println("  RQWEQWEQW");
        System.out.println("  RQWEQWEQW");
        System.out.println(model.getShip().getVessel_name());
        System.out.println(model.getShip().getVoyage_num());
        System.out.println("  RQWEQWEQW");
        System.out.println("  RQWEQWEQW");
        System.out.println("  RQWEQWEQW");
        System.out.println("  RQWEQWEQW");



        window.showAndWait();

    }
}

