package bds.controllers;

import bds.api.ClothesCreateView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import bds.data.ClothesRepository;
import bds.services.ClothesService;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClothesCreateController {

    private static final Logger logger = LoggerFactory.getLogger(ClothesCreateController.class);

    @FXML
    private Button newClothesCreateButton;

    @FXML
    private TextField newClothesName;

    @FXML
    private TextField newClothesSize;

    @FXML
    private TextField newClothesColor;

    @FXML
    private TextField newClothesType;

    @FXML
    private TextField newClothesQuantity;

    @FXML
    private TextField newClothesPrice;

    private ClothesService clothesService;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        ClothesRepository clothesRepository = new ClothesRepository();
        clothesService = new ClothesService(clothesRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newClothesName, Validator.createEmptyValidator("The name must not be empty."));
        validation.registerValidator(newClothesSize, Validator.createEmptyValidator("The size must not be empty."));
        validation.registerValidator(newClothesColor, Validator.createEmptyValidator("The color must not be empty."));
        validation.registerValidator(newClothesType, Validator.createEmptyValidator("The type must not be empty."));
        validation.registerValidator(newClothesQuantity, Validator.createRegexValidator("Quantity must be a number.", "\\d+", null));
        validation.registerValidator(newClothesPrice, Validator.createRegexValidator("Price must be a valid number.", "^\\d+\\.?\\d*$", null));

        newClothesCreateButton.disableProperty().bind(validation.invalidProperty());
        logger.info("ClothesCreateController initialized");
    }

    @FXML
    void handleCreateNewClothes(ActionEvent event) {

        String name = newClothesName.getText();
        String size = newClothesSize.getText();
        String color = newClothesColor.getText();
        String type = newClothesType.getText();
        int quantity = Integer.parseInt(newClothesQuantity.getText());
        double price = Double.parseDouble(newClothesPrice.getText());


        ClothesCreateView clothesCreateView = new ClothesCreateView();
        clothesCreateView.setClothesName(name);
        clothesCreateView.setClothesSize(size);
        clothesCreateView.setClothesColour(color);
        clothesCreateView.setClothesType(type);
        clothesCreateView.setClothesQuantity(quantity);
        clothesCreateView.setClothesPrice(price);


        clothesService.createClothes(clothesCreateView);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        clothesCreatedConfirmationDialog();
    }

    private void clothesCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clothes Created Confirmation");
        alert.setHeaderText("Your clothes item was successfully created.");


        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();

        alert.showAndWait();
    }
}
