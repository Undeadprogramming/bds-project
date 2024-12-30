package bds.api;

public class WorkplaceAuthView {

    private long idWorkplace;          // Represents the workplace ID
    private int city;                  // Represents the city (ID of the city in the address table)
    private String buildingAddress;    // Represents the building address
    private int floor;                 // Represents the floor in the building
    private String seatPlacement;      // Represents the seat placement in the workplace
    private int idWorker;              // Represents the worker ID associated with the workplace

    // Getter and Setter methods

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
        return "WorkplaceAuthView{" +
                "idWorkplace=" + idWorkplace +
                ", city=" + city +
                ", buildingAddress='" + buildingAddress + '\'' +
                ", floor=" + floor +
                ", seatPlacement='" + seatPlacement + '\'' +
                ", idWorker=" + idWorker +
                '}';
    }
}
