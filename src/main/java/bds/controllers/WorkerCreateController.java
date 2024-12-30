package bds.controllers;

import bds.data.WorkerRepository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import bds.api.WorkerCreateView;
import bds.services.WorkerService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerCreateController {

    private static final Logger logger = LoggerFactory.getLogger(WorkerCreateController.class);

    @FXML
    public Button newWorkerCreateButton;

    @FXML
    private TextField newWorkerFirstName;

    @FXML
    private TextField newWorkerMiddleName;

    @FXML
    private TextField newWorkerLastName;

    @FXML
    private TextField newWorkerAge;

    @FXML
    private TextField newWorkerGender;

    @FXML
    private TextField newWorkerPosition;

    @FXML
    private TextField newWorkerSalary;

    private WorkerService workerService;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        WorkerRepository workerRepository = new WorkerRepository();
        workerService = new WorkerService(workerRepository);
        validation = new ValidationSupport();


        validation.registerValidator(newWorkerFirstName, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(newWorkerLastName, Validator.createEmptyValidator("The last name must not be empty."));


        validation.registerValidator(newWorkerAge, Validator.createRegexValidator("Age must be a number.", "\\d+", null));
        validation.registerValidator(newWorkerSalary, Validator.createRegexValidator("Salary must be a number.", "\\d+", null));
        validation.registerValidator(newWorkerPosition, Validator.createEmptyValidator("The position must not be empty."));


        newWorkerCreateButton.disableProperty().bind(validation.invalidProperty());
        logger.info("WorkerCreateController initialized");
    }

    @FXML
    void handleCreateNewWorker(ActionEvent event) {
        try {
            WorkerCreateView workerCreateView = new WorkerCreateView();
            workerCreateView.setFirstName(newWorkerFirstName.getText());
            workerCreateView.setMiddleName(newWorkerMiddleName.getText());
            workerCreateView.setLastName(newWorkerLastName.getText());
            workerCreateView.setAge(Integer.parseInt(newWorkerAge.getText()));
            workerCreateView.setGender(newWorkerGender.getText());
            workerCreateView.setPosition(newWorkerPosition.getText());
            workerCreateView.setSalary(Integer.parseInt(newWorkerSalary.getText()));


            workerService.createWorker(workerCreateView);

            workerCreatedConfirmationDialog();
        } catch (NumberFormatException e) {
            logger.error("Invalid number format: ", e);
            showErrorDialog("Invalid input", "Please ensure age and salary are valid numbers.");
        } catch (Exception e) {
            logger.error("Error creating worker: ", e);
            showErrorDialog("Error", "An unexpected error occurred while creating the worker.");
        }
    }

    private void workerCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Worker Created Confirmation");
        alert.setHeaderText("Your worker was successfully created.");
        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.hide()));
        idlestage.setCycleCount(1);
        idlestage.play();
        alert.showAndWait();
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.showAndWait();
    }
}
