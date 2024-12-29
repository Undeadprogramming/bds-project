package bds.controllers;

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
import javafx.stage.Stage;
import javafx.util.Duration;
import bds.api.ClothesBasicView;
import bds.api.ClothesEditView;
import bds.data.ClothesRepository;
import bds.services.ClothesService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ClothesEditController {

    private static final Logger logger = LoggerFactory.getLogger(ClothesEditController.class);

    @FXML
    public Button editClothesButton;
    @FXML
    public TextField idClothesTextField;
    @FXML
    private TextField clothesNameTextField;
    @FXML
    private TextField clothesSizeTextField;
    @FXML
    private TextField clothesColorTextField;
    @FXML
    private TextField clothesTypeTextField;
    @FXML
    private TextField clothesQuantityTextField;
    @FXML
    private TextField clothesPriceTextField;

    private ClothesService clothesService;
    private ClothesRepository clothesRepository;
    private ValidationSupport validation;

    // Used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        clothesRepository = new ClothesRepository();
        clothesService = new ClothesService(clothesRepository);

        validation = new ValidationSupport();
        validation.registerValidator(idClothesTextField, Validator.createEmptyValidator("The clothes ID must not be empty."));
        idClothesTextField.setEditable(false);
        validation.registerValidator(clothesNameTextField, Validator.createEmptyValidator("The name must not be empty."));
        validation.registerValidator(clothesSizeTextField, Validator.createEmptyValidator("The size must not be empty."));
        validation.registerValidator(clothesColorTextField, Validator.createEmptyValidator("The color must not be empty."));
        validation.registerValidator(clothesTypeTextField, Validator.createEmptyValidator("The type must not be empty."));
        validation.registerValidator(clothesQuantityTextField, Validator.createEmptyValidator("The quantity must not be empty."));
        validation.registerValidator(clothesPriceTextField, Validator.createEmptyValidator("The price must not be empty."));

        editClothesButton.disableProperty().bind(validation.invalidProperty());

        loadClothesData();

        logger.info("ClothesEditController initialized");
    }

    /**
     * Load passed data from the previous controller.
     */
    private void loadClothesData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof ClothesBasicView) {
            ClothesBasicView clothesEditView = (ClothesBasicView) stage.getUserData();
            idClothesTextField.setText(String.valueOf(clothesEditView.getIdClothes()));
            clothesNameTextField.setText(clothesEditView.getClothesName());
            clothesSizeTextField.setText(clothesEditView.getClothesSize());
            clothesColorTextField.setText(clothesEditView.getClothesColour());
            clothesTypeTextField.setText(clothesEditView.getClothesType());
            clothesQuantityTextField.setText(String.valueOf(clothesEditView.getClothesQuantity()));
            clothesPriceTextField.setText(String.valueOf(clothesEditView.getClothesPrice()));
        }
    }

    @FXML
    public void handleEditClothesButton(ActionEvent event) {
        // Extract clothes details from the form
        Long idClothes = Long.valueOf(idClothesTextField.getText());
        String name = clothesNameTextField.getText();
        String size = clothesSizeTextField.getText();
        String color = clothesColorTextField.getText();
        String type = clothesTypeTextField.getText();
        int quantity = Integer.parseInt(clothesQuantityTextField.getText());
        double price = Double.parseDouble(clothesPriceTextField.getText());

        ClothesEditView clothesEditView = new ClothesEditView();
        clothesEditView.setIdClothes(idClothes);
        clothesEditView.setClothesName(name);
        clothesEditView.setClothesSize(size);
        clothesEditView.setClothesColour(color);
        clothesEditView.setClothesType(type);
        clothesEditView.setClothesQuantity(quantity);
        clothesEditView.setClothesPrice(price);

        clothesService.editClothes(clothesEditView);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        clothesEditedConfirmationDialog();
    }

    private void clothesEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clothes Edited Confirmation");
        alert.setHeaderText("Your clothes item was successfully edited.");

        // Close the confirmation dialog after 3 seconds
        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }
}
