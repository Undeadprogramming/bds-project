package bds.api;

public class ClothesEditView {
    private Long idClothes;
    private String clothesName;
    private String clothesType;
    private String clothesColour;
    private int clothesQuantity;
    private String clothesSize;
    private double clothesPrice;


    public Long getIdClothes() {
        return idClothes;
    }

    public void setIdClothes(Long idClothes) {
        this.idClothes = idClothes;
    }

    public String getClothesName() {
        return clothesName;
    }

    public void setClothesName(String clothesName) {
        this.clothesName = clothesName;
    }

    public String getClothesType() {
        return clothesType;
    }

    public void setClothesType(String clothesType) {
        this.clothesType = clothesType;
    }

    public String getClothesColour() {
        return clothesColour;
    }

    public void setClothesColour(String clothesColour) {
        this.clothesColour = clothesColour;
    }

    public int getClothesQuantity() {
        return clothesQuantity;
    }

    public void setClothesQuantity(int clothesQuantity) {
        this.clothesQuantity = clothesQuantity;
    }

    public String getClothesSize() {
        return clothesSize;
    }

    public void setClothesSize(String clothesSize) {
        this.clothesSize = clothesSize;
    }

    public double getClothesPrice() {
        return clothesPrice;
    }

    public void setClothesPrice(double clothesPrice) {
        this.clothesPrice = clothesPrice;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "idClothes=" + idClothes +
                ", clothesName='" + clothesName + '\'' +
                ", clothesType='" + clothesType + '\'' +
                ", clothesColour='" + clothesColour + '\'' +
                ", clothesQuantity=" + clothesQuantity +
                ", clothesSize='" + clothesSize + '\'' +
                ", clothesPrice=" + clothesPrice +
                '}';
    }
}
