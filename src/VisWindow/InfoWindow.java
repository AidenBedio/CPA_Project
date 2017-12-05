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
            private Label lblBerthPost;

            @FXML
            private Label lblDraftAft;

            @FXML
            private Label lblType;

            @FXML
            private Label lblBollard;

            @FXML
            private Label lblBeam;

            @FXML
            private Label lblDWT;

            @FXML
            private Label lblLOA;

            @FXML
            private Label lblRemarks;

            @FXML
            private Label lblDraftFwd;

            @FXML
            private Label lblBerthNo;

            @FXML
            private Label lblGRT;

            @FXML
            private Label lblName;

            @FXML
            private Label lblNRT;

            @FXML
            private Label lblNationality;

            @FXML
            private Pane pane;

            @FXML
            private Label lblNextPort;

            @FXML
            private Label lblVoyage;

            @FXML
            private Label lblMaster;

            @FXML
            private Label lblETD;

            @FXML
            private Label lblLastPort;

            @FXML
            private Label lblETA;

    public void Info(ShipModel model){

        lblName.setText("Vessel Name:       " + model.getShip().getVessel_name());
        //System.out.println(txt_name.getText() + " BACOOOOOOOOOOOOOOOOOOOOOOOOOOOONNSSS!!");
        lblVoyage.setText("Voyage Number:            " + model.getShip().getVoyage_num());
        lblNationality.setText("Nationality:                       " + model.getShip().getNationality());
        lblGRT.setText(String.valueOf("GRT:                                      " + model.getShip().getGRT()));
        lblLOA.setText(String.valueOf("LOA:                                      " + model.getShip().getLOA()));
        lblLastPort.setText("Last Port:                           " + model.getShip().getLast_port());
        lblNextPort.setText("Next Port:                          " + model.getShip().getNext_port());
        lblBerthNo.setText("Berth:                                   " + model.getShip().getBerth_pref());
        lblBollard.setText("Bollard:                               " + model.getShip().getBollard());
        lblMaster.setText("Master:                                " + model.getShip().getMaster());
        lblNRT.setText("NRT:                                      " + String.valueOf(model.getShip().getNRT()));
        lblDWT.setText("DWT:                                    " + String.valueOf(model.getShip().getDWT()));
        lblBeam.setText("Beam:                                   " + String.valueOf(model.getShip().getBeam()));
        lblETA.setText("ETA:                                      " + String.valueOf(model.getShip().getETA()));
        lblETD.setText("ETD:                                      " + String.valueOf(model.getShip().getETD()));
        lblDraftFwd.setText("Draft_fwd:                         " + String.valueOf(model.getShip().getDraft_fwd()));
        lblDraftAft.setText("Draft_aft:                           " + String.valueOf(model.getShip().getDraft_aft()));
        lblBerthPost.setText("Orientation:                     " + model.getShip().getBerth_post());
        lblType.setText("Vessel type:                      " + model.getShip().getFilled());
        lblRemarks.setText("Remarks:                            " + model.getShip().getRemarks());

       }


}

