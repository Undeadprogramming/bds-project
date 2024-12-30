package bds.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import bds.App;
import bds.api.WorkplaceBasicView;
import bds.data.WorkplaceRepository;
import bds.exceptions.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import bds.services.WorkplaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class WorkplaceController {

    private static final Logger logger = LoggerFactory.getLogger(WorkplaceController.class);

    @FXML
    public Button addWorkplaceButton;
    @FXML
    public Button refreshButton;
    public TextField workplaceIdFilterField;
    public TextField cityFilterField;
    public TextField addressFilterField;
    public TextField floorFilterField;
    public TextField seatPlacementFilterField;
    public TextField workerIdFilterField;

    @FXML
    private TableColumn<WorkplaceBasicView, Long> workplaceId;
    @FXML
    private TableColumn<WorkplaceBasicView, Integer> city;
    @FXML
    private TableColumn<WorkplaceBasicView, String> buildingAddress;
    @FXML
    private TableColumn<WorkplaceBasicView, Integer> floor;
    @FXML
    private TableColumn<WorkplaceBasicView, String> seatPlacement;
    @FXML
    private TableColumn<WorkplaceBasicView, Integer> workerId;
    @FXML
    private TableView<WorkplaceBasicView> workplaceTableView;

    private WorkplaceService workplaceService;
    private WorkplaceRepository workplaceRepository;
    private FilteredList<WorkplaceBasicView> filteredData;

    public WorkplaceController() {
    }

    @FXML
    private void initialize() {
        workplaceRepository = new WorkplaceRepository();
        workplaceService = new WorkplaceService(workplaceRepository);

        workplaceId.setCellValueFactory(new PropertyValueFactory<>("idWorkplace"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        buildingAddress.setCellValueFactory(new PropertyValueFactory<>("buildingAddress"));
        floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        seatPlacement.setCellValueFactory(new PropertyValueFactory<>("seatPlacement"));
        workerId.setCellValueFactory(new PropertyValueFactory<>("idWorker"));

        ObservableList<WorkplaceBasicView> observableWorkplaceList = initializeWorkplaceData();
        filteredData = new FilteredList<>(observableWorkplaceList, p -> true);
        workplaceTableView.setItems(filteredData);

        workplaceTableView.getSortOrder().add(workplaceId);

        initializeFilters();
        initializeTableViewSelection();
        loadIcons();

        logger.info("WorkplaceController initialized");
    }

    private void initializeFilters() {
        workplaceIdFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        cityFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        addressFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        floorFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        seatPlacementFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        workerIdFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));
    }

    private boolean applyFilters(WorkplaceBasicView workplace) {
        // Filter by workplace ID
        String workplaceIdText = workplaceIdFilterField.getText();
        if (workplaceIdText != null && !workplaceIdText.isEmpty()) {
            try {
                if (!String.valueOf(workplace.getIdWorkplace()).contains(workplaceIdText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false; // Invalid input doesn't match any record
            }
        }

        // Filter by city
        String cityText = cityFilterField.getText();
        if (cityText != null && !cityText.isEmpty()) {
            try {
                if (!String.valueOf(workplace.getCity()).contains(cityText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        // Filter by address
        String addressText = addressFilterField.getText();
        if (addressText != null && !addressText.isEmpty()) {
            if (!workplace.getBuildingAddress().toLowerCase().contains(addressText.toLowerCase())) {
                return false;
            }
        }

        // Filter by floor
        String floorText = floorFilterField.getText();
        if (floorText != null && !floorText.isEmpty()) {
            try {
                if (!String.valueOf(workplace.getFloor()).contains(floorText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        // Filter by seat placement
        String seatPlacementText = seatPlacementFilterField.getText();
        if (seatPlacementText != null && !seatPlacementText.isEmpty()) {
            if (!workplace.getSeatPlacement().toLowerCase().contains(seatPlacementText.toLowerCase())) {
                return false;
            }
        }

        // Filter by worker ID
        String workerIdText = workerIdFilterField.getText();
        if (workerIdText != null && !workerIdText.isEmpty()) {
            try {
                if (!String.valueOf(workplace.getIdWorker()).contains(workerIdText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit workplace");
        MenuItem delete = new MenuItem("Delete workplace");

        edit.setOnAction((ActionEvent event) -> {
            WorkplaceBasicView workplaceView = workplaceTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/WorkplaceEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(workplaceView);
                stage.setTitle("BDS JavaFX Edit Workplace");

                WorkplaceEditController controller = new WorkplaceEditController();
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
            WorkplaceBasicView selectedWorkplace = workplaceTableView.getSelectionModel().getSelectedItem();
            if (selectedWorkplace != null) {
                // Confirm deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("Are you sure you want to delete this workplace?");
                alert.setContentText("This action cannot be undone.");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            // Perform deletion in data source (e.g., repository or database)
                            workplaceService.deleteWorkplace(selectedWorkplace);

                            handleRefreshButton(new ActionEvent());

                            logger.info("Workplace with ID {} deleted successfully.", selectedWorkplace.getIdWorkplace());
                        } catch (Exception ex) {
                            ExceptionHandler.handleException(ex);
                        }
                    }
                });
            }
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().add(delete);
        workplaceTableView.setContextMenu(menu);
    }

    private ObservableList<WorkplaceBasicView> initializeWorkplaceData() {
        List<WorkplaceBasicView> workplaces = workplaceService.getWorkplaceBasicView();
        return FXCollections.observableArrayList(workplaces);
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

    public void handleAddWorkplaceButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/WorkplaceCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Create Workplace");
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<WorkplaceBasicView> observableWorkplaceList = initializeWorkplaceData();
        filteredData = new FilteredList<>(observableWorkplaceList, p -> true);
        workplaceTableView.setItems(filteredData);
        workplaceTableView.refresh();
        workplaceTableView.sort();
    }
}
