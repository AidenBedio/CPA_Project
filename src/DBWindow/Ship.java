package DBWindow;

import javafx.beans.property.*;

import java.sql.Timestamp;

public class Ship {

    private IntegerProperty id;
    private StringProperty vessel_name;
    private StringProperty voyage_num;
    private StringProperty nationality;
    private FloatProperty GRT;
    private FloatProperty LOA;
    private StringProperty last_port;
    private StringProperty next_port;
    private StringProperty berth_pref;
    private StringProperty master;
    private FloatProperty NRT;
    private FloatProperty DWT;
    private FloatProperty beam;
    private SimpleObjectProperty<Timestamp> ETA;
    private SimpleObjectProperty<Timestamp> ETD;
    private FloatProperty draft_fwd;
    private FloatProperty draft_aft;
    private StringProperty berth_post;
    private StringProperty bollard;
    private StringProperty remarks;
    private StringProperty filled;


    public Ship() {
    }

    public Ship(Integer id, String vessel_name, String voyage_num, String nationality, Float GRT, Float LOA, String last_port,
                String next_port, String berth_pref, String master, Float NRT, Float DWT, Float beam, Timestamp ETA,
                Timestamp ETD, Float draft_fwd, Float draft_aft, String berth_post, String bollard, String remarks, String filled) {

        System.out.println("NEW SHIP ALWAYS");
        this.id = new SimpleIntegerProperty(id);
        this.vessel_name = new SimpleStringProperty(vessel_name);
        this.voyage_num = new SimpleStringProperty(voyage_num);
        this.nationality = new SimpleStringProperty(nationality);
        this.GRT = new SimpleFloatProperty(GRT);
        this.LOA = new SimpleFloatProperty(LOA);
        this.last_port = new SimpleStringProperty(last_port);
        this.next_port = new SimpleStringProperty(next_port);
        this.berth_pref = new SimpleStringProperty(berth_pref);
        this.master = new SimpleStringProperty(master);
        this.NRT = new SimpleFloatProperty(NRT);
        this.DWT = new SimpleFloatProperty(DWT);
        this.beam = new SimpleFloatProperty(beam);
        this.ETA = new SimpleObjectProperty<>(ETA);
        this.ETD = new SimpleObjectProperty<>(ETD);
        this.draft_fwd = new SimpleFloatProperty(draft_fwd);
        this.draft_aft = new SimpleFloatProperty(draft_aft);
        this.berth_post = new SimpleStringProperty(berth_post);
        this.bollard = new SimpleStringProperty(bollard);
        this.remarks = new SimpleStringProperty(remarks);
        this.filled = new SimpleStringProperty(filled);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getVessel_name() {
        return vessel_name.get();
    }

    public StringProperty vessel_nameProperty() {
        return vessel_name;
    }

    public void setVessel_name(String vessel_name) {
        this.vessel_name.set(vessel_name);
    }

    public String getVoyage_num() {
        return voyage_num.get();
    }

    public StringProperty voyage_numProperty() {
        return voyage_num;
    }

    public void setVoyage_num(String voyage_num) {
        this.voyage_num.set(voyage_num);
    }

    public String getNationality() {
        return nationality.get();
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    public float getGRT() {
        return GRT.get();
    }

    public FloatProperty GRTProperty() {
        return GRT;
    }

    public void setGRT(float GRT) {
        this.GRT.set(GRT);
    }

    public float getLOA() {
        return LOA.get();
    }

    public FloatProperty LOAProperty() {
        return LOA;
    }

    public void setLOA(float LOA) {
        this.LOA.set(LOA);
    }

    public String getLast_port() {
        return last_port.get();
    }

    public StringProperty last_portProperty() {
        return last_port;
    }

    public void setLast_port(String last_port) {
        this.last_port.set(last_port);
    }

    public String getNext_port() {
        return next_port.get();
    }

    public StringProperty next_portProperty() {
        return next_port;
    }

    public void setNext_port(String next_port) {
        this.next_port.set(next_port);
    }

    public String getBerth_pref() {
        return berth_pref.get();
    }

    public StringProperty berth_prefProperty() {
        return berth_pref;
    }

    public void setBerth_pref(String berth_pref) {
        this.berth_pref.set(berth_pref);
    }

    public String getMaster() {
        return master.get();
    }

    public StringProperty masterProperty() {
        return master;
    }

    public void setMaster(String master) {
        this.master.set(master);
    }

    public float getNRT() {
        return NRT.get();
    }

    public FloatProperty NRTProperty() {
        return NRT;
    }

    public void setNRT(float NRT) {
        this.NRT.set(NRT);
    }

    public float getDWT() {
        return DWT.get();
    }

    public FloatProperty DWTProperty() {
        return DWT;
    }

    public void setDWT(float DWT) {
        this.DWT.set(DWT);
    }

    public float getBeam() {
        return beam.get();
    }

    public FloatProperty beamProperty() {
        return beam;
    }

    public void setBeam(float beam) {
        this.beam.set(beam);
    }

    public Timestamp getETA() {
        return ETA.get();
    }

    public SimpleObjectProperty<Timestamp> ETAProperty() {
        return ETA;
    }

    public void setETA(Timestamp ETA) {
        this.ETA.set(ETA);
    }

    public Timestamp getETD() {
        return ETD.get();
    }

    public SimpleObjectProperty<Timestamp> ETDProperty() {
        return ETD;
    }

    public void setETD(Timestamp ETD) {
        this.ETD.set(ETD);
    }

    public float getDraft_fwd() {
        return draft_fwd.get();
    }

    public FloatProperty draft_fwdProperty() {
        return draft_fwd;
    }

    public void setDraft_fwd(float draft_fwd) {
        this.draft_fwd.set(draft_fwd);
    }

    public float getDraft_aft() {
        return draft_aft.get();
    }

    public FloatProperty draft_aftProperty() {
        return draft_aft;
    }

    public void setDraft_aft(float draft_aft) {
        this.draft_aft.set(draft_aft);
    }

    public String getBerth_post() {
        return berth_post.get();
    }

    public StringProperty berth_postProperty() {
        return berth_post;
    }

    public void setBerth_post(String berth_post) {
        this.berth_post.set(berth_post);
    }

    public String getBollard() {
        return bollard.get();
    }

    public StringProperty bollardProperty() {
        return bollard;
    }

    public void setBollard(String bollard) {
        this.bollard.set(bollard);
    }

    public String getRemarks() {
        return remarks.get();
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    public String getFilled() {
        return filled.get();
    }

    public StringProperty filledProperty() {
        return filled;
    }

    public void setFilled(String filled) {
        this.filled.set(filled);
    }
}
