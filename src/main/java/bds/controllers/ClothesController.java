package bds.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import bds.App;
import bds.api.ClothesBasicView;

import bds.data.ClothesRepository;
import bds.exceptions.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import bds.services.ClothesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ClothesController {

    private static final Logger logger = LoggerFactory.getLogger(ClothesController.class);

    @FXML
    public Button addClothesButton;
    @FXML
    public Button refreshButton;
    public TextField clothesIdFilterField;
    public TextField nameFilterField;
    public TextField typeFilterField;
    public TextField colourFilterField;
    public TextField quantityFilterField;
    public TextField sizeFilterField;
    public TextField priceFilterField;

    @FXML
    private TableColumn<ClothesBasicView, Long> clothesId;
    @FXML
    private TableColumn<ClothesBasicView, String> clothesName;
    @FXML
    private TableColumn<ClothesBasicView, String> clothesType;
    @FXML
    private TableColumn<ClothesBasicView, String> clothesColour;
    @FXML
    private TableColumn<ClothesBasicView, Integer> clothesQuantity;
    @FXML
    private TableColumn<ClothesBasicView, String> clothesSize;
    @FXML
    private TableColumn<ClothesBasicView, Double> clothesPrice;
    @FXML
    private TableView<ClothesBasicView> clothesTableView;

    private ClothesService clothesService;
    private ClothesRepository clothesRepository;
    private FilteredList<ClothesBasicView> filteredData;

    public ClothesController() {
    }

    @FXML
    private void initialize() {
        clothesRepository = new ClothesRepository();
        clothesService = new ClothesService(clothesRepository);

        clothesId.setCellValueFactory(new PropertyValueFactory<>("idClothes"));
        clothesName.setCellValueFactory(new PropertyValueFactory<>("clothesName"));
        clothesType.setCellValueFactory(new PropertyValueFactory<>("clothesType"));
        clothesColour.setCellValueFactory(new PropertyValueFactory<>("clothesColour"));
        clothesQuantity.setCellValueFactory(new PropertyValueFactory<>("clothesQuantity"));
        clothesSize.setCellValueFactory(new PropertyValueFactory<>("clothesSize"));
        clothesPrice.setCellValueFactory(new PropertyValueFactory<>("clothesPrice"));

        ObservableList<ClothesBasicView> observableClothesList = initializeClothesData();
        filteredData = new FilteredList<>(observableClothesList, p -> true);
        clothesTableView.setItems(filteredData);

        clothesTableView.getSortOrder().add(clothesId);

        initializeFilters();
        initializeTableViewSelection();
        loadIcons();

        logger.info("ClothesController initialized");
    }

    private void initializeFilters() {
        clothesIdFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        nameFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        typeFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        colourFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        quantityFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        sizeFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        priceFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));
    }

    private boolean applyFilters(ClothesBasicView clothes) {
        // Filter by clothes ID
        String clothesIdText = clothesIdFilterField.getText();
        if (clothesIdText != null && !clothesIdText.isEmpty()) {
            try {
                if (!String.valueOf(clothes.getIdClothes()).contains(clothesIdText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false; // Invalid input doesn't match any record
            }
        }

        // Filter by name
        String nameText = nameFilterField.getText();
        if (nameText != null && !nameText.isEmpty()) {
            if (!clothes.getClothesName().toLowerCase().contains(nameText.toLowerCase())) {
                return false;
            }
        }

        // Filter by type
        String typeText = typeFilterField.getText();
        if (typeText != null && !typeText.isEmpty()) {
            if (!clothes.getClothesType().toLowerCase().contains(typeText.toLowerCase())) {
                return false;
            }
        }

        // Filter by colour
        String colourText = colourFilterField.getText();
        if (colourText != null && !colourText.isEmpty()) {
            if (!clothes.getClothesColour().toLowerCase().contains(colourText.toLowerCase())) {
                return false;
            }
        }

        // Filter by quantity
        String quantityText = quantityFilterField.getText();
        if (quantityText != null && !quantityText.isEmpty()) {
            try {
                if (!String.valueOf(clothes.getClothesQuantity()).contains(quantityText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false; // Invalid input doesn't match any record
            }
        }

        // Filter by size
        String sizeText = sizeFilterField.getText();
        if (sizeText != null && !sizeText.isEmpty()) {
            if (!clothes.getClothesSize().toLowerCase().contains(sizeText.toLowerCase())) {
                return false;
            }
        }

        // Filter by price
        String priceText = priceFilterField.getText();
        if (priceText != null && !priceText.isEmpty()) {
            try {
                if (!String.valueOf(clothes.getClothesPrice()).contains(priceText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false; // Invalid input doesn't match any record
            }
        }

        return true;
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit clothes");
        MenuItem delete = new MenuItem("Delete clothes");

        edit.setOnAction((ActionEvent event) -> {
            ClothesBasicView clothesView = clothesTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/ClothesEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(clothesView);
                stage.setTitle("BDS JavaFX Edit Clothes");

                ClothesEditController controller = new ClothesEditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        delete.setOnAction((ActionEvent event) -> {
            ClothesBasicView selectedClothes = clothesTableView.getSelectionModel().getSelectedItem();
            if (selectedClothes != null) {
                // Confirm deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("Are you sure you want to delete this clothes item?");
                alert.setContentText("This action cannot be undone.");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            // Perform deletion in data source (e.g., repository or database)
                            clothesService.deleteClothes(selectedClothes); // Replace with your actual delete method

                            handleRefreshButton(new ActionEvent());

                            logger.info("Clothes with ID {} deleted successfully.", selectedClothes.getIdClothes());
                        } catch (Exception ex) {
                            ExceptionHandler.handleException(ex);
                        }
                    }
                });
            }
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(delete);
        clothesTableView.setContextMenu(menu);
    }

    private ObservableList<ClothesBasicView> initializeClothesData() {
        List<ClothesBasicView> clothes = clothesService.getClothesBasicView();
        return FXCollections.observableArrayList(clothes);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("logos/logo.png"));
        ImageView vutLogo = new ImageView(vutLogoImage);
        vutLogo.setFitWidth(150);
        vutLogo.setFitHeight(50);
    }

    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    public void handleAddClothesButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/ClothesCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Create Clothes");
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<ClothesBasicView> observableClothesList = initializeClothesData();
        filteredData = new FilteredList<>(observableClothesList, p -> true);
        clothesTableView.setItems(filteredData);
        clothesTableView.refresh();
        clothesTableView.sort();
    }
}
