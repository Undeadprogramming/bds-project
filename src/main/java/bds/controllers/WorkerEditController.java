package bds.controllers;

import bds.data.WorkerRepository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import bds.api.WorkerBasicView;
import bds.api.WorkerEditView;
import bds.services.WorkerService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class WorkerEditController {

    private static final Logger logger = LoggerFactory.getLogger(WorkerEditController.class);

    @FXML
    public Button editWorkerButton;
    @FXML
    public TextField idTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField genderTextField;
    @FXML
    private TextField positionTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private TextField ageTextField;

    private WorkerService workerService;
    private WorkerRepository workerRepository;
    private ValidationSupport validation;


    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        workerRepository = new WorkerRepository();
        workerService = new WorkerService(workerRepository);


        validation = new ValidationSupport();
        validation.registerValidator(idTextField, Validator.createEmptyValidator("The ID must not be empty."));
        idTextField.setEditable(false);
        validation.registerValidator(firstNameTextField, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(lastNameTextField, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(positionTextField, Validator.createEmptyValidator("The position must not be empty."));
        validation.registerValidator(ageTextField, Validator.createRegexValidator("Age must be a number.", "\\d+", null));
        validation.registerValidator(salaryTextField, Validator.createRegexValidator("Salary must be a number.", "\\d+", null));

        editWorkerButton.disableProperty().bind(validation.invalidProperty());

        loadWorkerData();
        logger.info("WorkerEditController initialized");
    }


    private void loadWorkerData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof WorkerBasicView) {
            WorkerBasicView workerBasicView = (WorkerBasicView) stage.getUserData();
            idTextField.setText(String.valueOf(workerBasicView.getIdWorker()));
            firstNameTextField.setText(workerBasicView.getFirstName());
            middleNameTextField.setText(workerBasicView.getMiddleName());
            lastNameTextField.setText(workerBasicView.getLastName());
            genderTextField.setText(workerBasicView.getGender());
            positionTextField.setText(workerBasicView.getPosition());
            salaryTextField.setText(String.valueOf(workerBasicView.getSalary()));
            ageTextField.setText(String.valueOf(workerBasicView.getAge()));
        }
    }

    @FXML
    public void handleEditWorkerButton(ActionEvent event) {
        try {

            Long id = Long.valueOf(idTextField.getText());
            String firstName = firstNameTextField.getText();
            String middleName = middleNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String gender = genderTextField.getText();
            String position = positionTextField.getText();
            int age = Integer.parseInt(ageTextField.getText());
            int salary = Integer.parseInt(salaryTextField.getText());


            WorkerEditView workerEditView = new WorkerEditView();
            workerEditView.setIdWorker(id);
            workerEditView.setFirstName(firstName);
            workerEditView.setMiddleName(middleName);
            workerEditView.setLastName(lastName);
            workerEditView.setGender(gender);
            workerEditView.setPosition(position);
            workerEditView.setAge(age);
            workerEditView.setSalary(salary);


            workerService.editWorker(workerEditView);

            workerEditedConfirmationDialog();
        } catch (NumberFormatException e) {
            logger.error("Invalid number format: ", e);
            showErrorDialog("Invalid input", "Please ensure age and salary are valid numbers.");
        } catch (Exception e) {
            logger.error("Error editing worker: ", e);
            showErrorDialog("Error", "An unexpected error occurred while editing the worker.");
        }
    }

    private void workerEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Worker Edited Confirmation");
        alert.setHeaderText("Your worker was successfully edited.");

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

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.showAndWait();
    }
}
