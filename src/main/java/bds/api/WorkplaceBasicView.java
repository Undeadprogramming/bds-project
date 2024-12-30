package bds.api;

import javafx.beans.property.*;

public class WorkplaceBasicView {

    private LongProperty idWorkplace = new SimpleLongProperty();
    private IntegerProperty city = new SimpleIntegerProperty();
    private StringProperty buildingAddress = new SimpleStringProperty();
    private IntegerProperty floor = new SimpleIntegerProperty();
    private StringProperty seatPlacement = new SimpleStringProperty();
    private IntegerProperty idWorker = new SimpleIntegerProperty();


    public long getIdWorkplace() {
        return idWorkplaceProperty().get();
    }

    public void setIdWorkplace(long idWorkplace) {
        this.idWorkplaceProperty().set(idWorkplace);
    }

    public int getCity() {
        return cityProperty().get();
    }

    public void setCity(int city) {
        this.cityProperty().set(city);
    }

    public String getBuildingAddress() {
        return buildingAddressProperty().get();
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddressProperty().set(buildingAddress);
    }

    public int getFloor() {
        return floorProperty().get();
    }

    public void setFloor(int floor) {
        this.floorProperty().set(floor);
    }

    public String getSeatPlacement() {
        return seatPlacementProperty().get();
    }

    public void setSeatPlacement(String seatPlacement) {
        this.seatPlacementProperty().set(seatPlacement);
    }

    public int getIdWorker() {
        return idWorkerProperty().get();
    }

    public void setIdWorker(int idWorker) {
        this.idWorkerProperty().set(idWorker);
    }



    public LongProperty idWorkplaceProperty() {
        return idWorkplace;
    }

    public IntegerProperty cityProperty() {
        return city;
    }

    public StringProperty buildingAddressProperty() {
        return buildingAddress;
    }

    public IntegerProperty floorProperty() {
        return floor;
    }

    public StringProperty seatPlacementProperty() {
        return seatPlacement;
    }

    public IntegerProperty idWorkerProperty() {
        return idWorker;
    }
}
