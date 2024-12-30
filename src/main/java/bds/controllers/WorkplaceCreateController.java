package bds.controllers;

import bds.api.WorkplaceCreateView;
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
import bds.data.WorkplaceRepository;
import bds.services.WorkplaceService;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkplaceCreateController {

    private static final Logger logger = LoggerFactory.getLogger(WorkplaceCreateController.class);

    @FXML
    private Button newWorkplaceCreateButton;

    @FXML
    private TextField newWorkplaceCity;

    @FXML
    private TextField newWorkplaceAddress;

    @FXML
    private TextField newWorkplaceFloor;

    @FXML
    private TextField newWorkplaceSeatPlacement;

    @FXML
    private TextField newWorkplaceWorkerId;

    private WorkplaceService workplaceService;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        WorkplaceRepository workplaceRepository = new WorkplaceRepository();
        workplaceService = new WorkplaceService(workplaceRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newWorkplaceCity, Validator.createEmptyValidator("The city must not be empty."));
        validation.registerValidator(newWorkplaceAddress, Validator.createEmptyValidator("The building address must not be empty."));
        validation.registerValidator(newWorkplaceFloor, Validator.createRegexValidator("Floor must be a number.", "\\d+", null));
        validation.registerValidator(newWorkplaceSeatPlacement, Validator.createEmptyValidator("The seat placement must not be empty."));
        validation.registerValidator(newWorkplaceWorkerId, Validator.createRegexValidator("Worker ID must be a number.", "\\d+", null));

        newWorkplaceCreateButton.disableProperty().bind(validation.invalidProperty());
        logger.info("WorkplaceCreateController initialized");
    }

    @FXML
    void handleCreateNewWorkplace(ActionEvent event) {
        // Get the input values for all properties of workplace
        int city = Integer.parseInt(newWorkplaceCity.getText()); // City ID
        String buildingAddress = newWorkplaceAddress.getText(); // Building address
        int floor = Integer.parseInt(newWorkplaceFloor.getText()); // Floor
        String seatPlacement = newWorkplaceSeatPlacement.getText(); // Seat placement
        int workerId = Integer.parseInt(newWorkplaceWorkerId.getText()); // Worker ID

        // Create a WorkplaceCreateView object with the provided information
        WorkplaceCreateView workplaceCreateView = new WorkplaceCreateView();
        workplaceCreateView.setCity(city);
        workplaceCreateView.setBuildingAddress(buildingAddress);
        workplaceCreateView.setFloor(floor);
        workplaceCreateView.setSeatPlacement(seatPlacement);
        workplaceCreateView.setIdWorker(workerId);

        // Call the workplace service to handle the workplace creation logic
        workplaceService.createWorkplace(workplaceCreateView);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        // Show the confirmation dialog after workplace creation
        workplaceCreatedConfirmationDialog();
    }

    private void workplaceCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Workplace Created Confirmation");
        alert.setHeaderText("Your workplace item was successfully created.");

        // Automatically close the alert after 3 seconds
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