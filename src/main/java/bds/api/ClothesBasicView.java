package bds.api;

import javafx.beans.property.*;

public class ClothesBasicView {
    private LongProperty idClothes = new SimpleLongProperty();
    private StringProperty clothesName = new SimpleStringProperty();
    private StringProperty clothesType = new SimpleStringProperty();
    private StringProperty clothesColour = new SimpleStringProperty();
    private IntegerProperty clothesQuantity = new SimpleIntegerProperty();
    private StringProperty clothesSize = new SimpleStringProperty();
    private DoubleProperty clothesPrice = new SimpleDoubleProperty();

    public Long getIdClothes() {
        return idClothesProperty().get();
    }

    public void setIdClothes(Long idClothes) {
        this.idClothesProperty().set(idClothes);
    }

    public String getClothesName() {
        return clothesNameProperty().get();
    }

    public void setClothesName(String clothesName) {
        this.clothesNameProperty().set(clothesName);
    }

    public String getClothesType() {
        return clothesTypeProperty().get();
    }

    public void setClothesType(String clothesType) {
        this.clothesTypeProperty().set(clothesType);
    }

    public String getClothesColour() {
        return clothesColourProperty().get();
    }

    public void setClothesColour(String clothesColour) {
        this.clothesColourProperty().set(clothesColour);
    }

    public int getClothesQuantity() {
        return clothesQuantityProperty().get();
    }

    public void setClothesQuantity(int clothesQuantity) {
        this.clothesQuantityProperty().set(clothesQuantity);
    }

    public String getClothesSize() {
        return clothesSizeProperty().get();
    }

    public void setClothesSize(String clothesSize) {
        this.clothesSizeProperty().set(clothesSize);
    }

    public double getClothesPrice() {
        return clothesPriceProperty().get();
    }

    public void setClothesPrice(double clothesPrice) {
        this.clothesPriceProperty().set(clothesPrice);
    }

    public LongProperty idClothesProperty() {
        return idClothes;
    }

    public StringProperty clothesNameProperty() {
        return clothesName;
    }

    public StringProperty clothesTypeProperty() {
        return clothesType;
    }

    public StringProperty clothesColourProperty() {
        return clothesColour;
    }

    public IntegerProperty clothesQuantityProperty() {
        return clothesQuantity;
    }

    public StringProperty clothesSizeProperty() {
        return clothesSize;
    }

    public DoubleProperty clothesPriceProperty() {
        return clothesPrice;
    }
}
