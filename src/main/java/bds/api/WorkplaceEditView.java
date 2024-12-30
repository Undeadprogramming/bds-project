package bds.api;

public class WorkplaceEditView {
    private long idWorkplace;
    private int city;
    private String buildingAddress;
    private int floor;
    private String seatPlacement;
    private int idWorker;

    // Getters and Setters
    public long getIdWorkplace() {
        return idWorkplace;
    }

    public void setIdWorkplace(long idWorkplace) {
        this.idWorkplace = idWorkplace;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getSeatPlacement() {
        return seatPlacement;
    }

    public void setSeatPlacement(String seatPlacement) {
        this.seatPlacement = seatPlacement;
    }

    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    @Override
    public String toString() {
        return "WorkplaceEditView{" +
                "idWorkplace=" + idWorkplace +
                ", city=" + city +
                ", buildingAddress='" + buildingAddress + '\'' +
                ", floor=" + floor +
                ", seatPlacement='" + seatPlacement + '\'' +
                ", idWorker=" + idWorker +
                '}';
    }
}
