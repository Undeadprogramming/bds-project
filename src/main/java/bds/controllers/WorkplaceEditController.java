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
import bds.api.WorkplaceBasicView;
import bds.api.WorkplaceEditView;
import bds.data.WorkplaceRepository;
import bds.services.WorkplaceService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class WorkplaceEditController {

    private static final Logger logger = LoggerFactory.getLogger(WorkplaceEditController.class);

    @FXML
    public Button editWorkplaceButton;

    @FXML
    public TextField idWorkplaceTextField;

    @FXML
    private TextField workplaceCityTextField;

    @FXML
    private TextField workplaceAddressTextField;

    @FXML
    private TextField workplaceFloorTextField;

    @FXML
    private TextField workplaceSeatPlacementTextField;

    @FXML
    private TextField workplaceWorkerIdTextField;

    private WorkplaceService workplaceService;
    private WorkplaceRepository workplaceRepository;
    private ValidationSupport validation;


    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        workplaceRepository = new WorkplaceRepository();
        workplaceService = new WorkplaceService(workplaceRepository);

        validation = new ValidationSupport();
        validation.registerValidator(idWorkplaceTextField, Validator.createEmptyValidator("The workplace ID must not be empty."));
        idWorkplaceTextField.setEditable(false);

        validation.registerValidator(workplaceCityTextField, Validator.createEmptyValidator("The city must not be empty."));
        validation.registerValidator(workplaceAddressTextField, Validator.createEmptyValidator("The building address must not be empty."));
        validation.registerValidator(workplaceFloorTextField, Validator.createEmptyValidator("The floor must not be empty."));
        validation.registerValidator(workplaceSeatPlacementTextField, Validator.createEmptyValidator("The seat placement must not be empty."));
        validation.registerValidator(workplaceWorkerIdTextField, Validator.createEmptyValidator("The worker ID must not be empty."));

        editWorkplaceButton.disableProperty().bind(validation.invalidProperty());

        loadWorkplaceData();

        logger.info("WorkplaceEditController initialized");
    }


    private void loadWorkplaceData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof WorkplaceBasicView) {
            WorkplaceBasicView workplaceEditView = (WorkplaceBasicView) stage.getUserData();
            idWorkplaceTextField.setText(String.valueOf(workplaceEditView.getIdWorkplace()));
            workplaceCityTextField.setText(String.valueOf(workplaceEditView.getCity()));
            workplaceAddressTextField.setText(workplaceEditView.getBuildingAddress());
            workplaceFloorTextField.setText(String.valueOf(workplaceEditView.getFloor()));
            workplaceSeatPlacementTextField.setText(workplaceEditView.getSeatPlacement());
            workplaceWorkerIdTextField.setText(String.valueOf(workplaceEditView.getIdWorker()));
        }
    }

    @FXML
    public void handleEditWorkplaceButton(ActionEvent event) {

        Long idWorkplace = Long.valueOf(idWorkplaceTextField.getText());
        int city = Integer.parseInt(workplaceCityTextField.getText());
        String buildingAddress = workplaceAddressTextField.getText();
        int floor = Integer.parseInt(workplaceFloorTextField.getText());
        String seatPlacement = workplaceSeatPlacementTextField.getText();
        int workerId = Integer.parseInt(workplaceWorkerIdTextField.getText());

        WorkplaceEditView workplaceEditView = new WorkplaceEditView();
        workplaceEditView.setIdWorkplace(idWorkplace);
        workplaceEditView.setCity(city);
        workplaceEditView.setBuildingAddress(buildingAddress);
        workplaceEditView.setFloor(floor);
        workplaceEditView.setSeatPlacement(seatPlacement);
        workplaceEditView.setIdWorker(workerId);

        workplaceService.editWorkplace(workplaceEditView);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        workplaceEditedConfirmationDialog();
    }

    private void workplaceEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Workplace Edited Confirmation");
        alert.setHeaderText("Your workplace item was successfully edited.");


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
