package bds.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import bds.api.WorkerCreateView;
import bds.data.PersonRepository;
import bds.services.LoginService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonCreateController {

    private static final Logger logger = LoggerFactory.getLogger(PersonCreateController.class);

    @FXML
    public Button newPersonCreatePerson;

    @FXML
    private TextField newPersonEmail;

    @FXML
    private TextField newPersonGivenName;

    @FXML
    private TextField newPersonMiddleName;

    @FXML
    private TextField newPersonFamilyName;

    @FXML
    private TextField newPersonNickname;

    @FXML
    private TextField newPersonPwd;

    @FXML
    private TextField newPersonAge;

    @FXML
    private TextField newPersonGender;

    @FXML
    private TextField newPersonPosition;

    @FXML
    private TextField newPersonSalary;

    private LoginService loginService;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
       // PersonRepository personRepository = new PersonRepository();
       // loginService = new LoginService(loginRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newPersonEmail, Validator.createEmptyValidator("The email must not be empty."));
        validation.registerValidator(newPersonGivenName, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(newPersonFamilyName, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(newPersonNickname, Validator.createEmptyValidator("The nickname must not be empty."));
        validation.registerValidator(newPersonPwd, Validator.createEmptyValidator("The password must not be empty."));
        validation.registerValidator(newPersonAge, Validator.createRegexValidator("Age must be a number.", "\\d+", null));
        validation.registerValidator(newPersonSalary, Validator.createRegexValidator("Salary must be a number.", "\\d+(\\.\\d+)?", null));

        newPersonCreatePerson.disableProperty().bind(validation.invalidProperty());
        logger.info("PersonCreateController initialized");
    }

    @FXML
    void handleCreateNewPerson(ActionEvent event) {
        try {
            WorkerCreateView workerCreateView = new WorkerCreateView();
            workerCreateView.setFirstName(newPersonGivenName.getText());
            workerCreateView.setMiddleName(newPersonMiddleName.getText());
            workerCreateView.setLastName(newPersonFamilyName.getText());
            workerCreateView.setAge(Integer.parseInt(newPersonAge.getText()));
            workerCreateView.setGender(newPersonGender.getText());
            workerCreateView.setPosition(newPersonPosition.getText());
            workerCreateView.setSalary(Double.parseDouble(newPersonSalary.getText()));

            //personService.createPerson(workerCreateView);
            personCreatedConfirmationDialog();
        } catch (NumberFormatException e) {
            logger.error("Invalid number format: ", e);
            showErrorDialog("Invalid input", "Please ensure age and salary are valid numbers.");
        } catch (Exception e) {
            logger.error("Error creating person: ", e);
            showErrorDialog("Error", "An unexpected error occurred while creating the person.");
        }
    }

    private void personCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Person Created Confirmation");
        alert.setHeaderText("Your person was successfully created.");
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
