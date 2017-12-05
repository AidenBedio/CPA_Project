package VisWindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoWindow {

            @FXML
            private static Label lblBerthPost;

            @FXML
            private static Label lblDraftAft;

            @FXML
            private static Label lblType;

            @FXML
            private static Label lblBollard;

            @FXML
            private static Label lblBeam;

            @FXML
            private static Label lblDWT;

            @FXML
            private static Label lblLOA;

            @FXML
            private static Label lblRemarks;

            @FXML
            private static Label lblDraftFwd;

            @FXML
            private static Label lblBerthNo;

            @FXML
            private static Label lblGRT;

            @FXML
            private static Label lblName;

            @FXML
            private static Label lblNRT;

            @FXML
            private static Label lblNationality;

            @FXML
            private Pane pane;

            @FXML
            private static Label lblNextPort;

            @FXML
            private static Label lblVoyage;

            @FXML
            private static Label lblMaster;

            @FXML
            private static Label lblETD;

            @FXML
            private static Label lblLastPort;

            @FXML
            private static Label lblETA;

    public void Info(ShipModel model){

        lblName.setText("Vessel Name: " + model.getShip().getVessel_name());
        //System.out.println(txt_name.getText() + " BACOOOOOOOOOOOOOOOOOOOOOOOOOOOONNSSS!!");
        lblVoyage.setText("Voyage Number:" + model.getShip().getVoyage_num());
        lblNationality.setText("Nationality: " + model.getShip().getNationality());
        lblGRT.setText(String.valueOf("GRT: " + model.getShip().getGRT()));
        lblLOA.setText(String.valueOf("LOA: " + model.getShip().getLOA()));
        lblLastPort.setText("Last Port: " + model.getShip().getLast_port());
        lblNextPort.setText("Next Port: " + model.getShip().getNext_port());
        lblBerthNo.setText("Berth: " + model.getShip().getBerth_pref());
        lblBollard.setText("Bollard: " + model.getShip().getBollard());
        lblMaster.setText("Master: " + model.getShip().getMaster());
        lblNRT.setText("NRT: " + String.valueOf(model.getShip().getNRT()));
        lblDWT.setText("DWT: " + String.valueOf(model.getShip().getDWT()));
        lblBeam.setText("Beam: " + String.valueOf(model.getShip().getBeam()));
        lblETA.setText("ETA: " + String.valueOf(model.getShip().getETA()));
        lblETD.setText("ETD: " + String.valueOf(model.getShip().getETD()));
        lblDraftFwd.setText("Draft_fwd: " + String.valueOf(model.getShip().draft_fwdProperty()));
        lblDraftAft.setText("Draft_aft: " + String.valueOf(model.getShip().getDraft_aft()));
        lblBerthPost.setText("Orientation: " + model.getShip().getBerth_post());
        lblType.setText("Vessel type: " + model.getShip().getFilled());
        lblRemarks.setText("Remarks: " + model.getShip().getRemarks());

       }


}

