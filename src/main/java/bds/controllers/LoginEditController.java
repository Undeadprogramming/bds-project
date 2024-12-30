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
import bds.api.LoginBasicView;
import bds.api.LoginEditView;
import bds.data.LoginRepository;
import bds.services.LoginService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Optional;

public class LoginEditController {

    private static final Logger logger = LoggerFactory.getLogger(LoginEditController.class);

    @FXML
    public Button editLoginButton;
    @FXML
    public TextField idWorkerTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField passwordTextField;

    private LoginService loginService;
    private LoginRepository loginRepository;
    private ValidationSupport validation;


    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        loginRepository = new LoginRepository();
        loginService = new LoginService(loginRepository);

        validation = new ValidationSupport();
        validation.registerValidator(idWorkerTextField, Validator.createEmptyValidator("The worker ID must not be empty."));
        idWorkerTextField.setEditable(false);
        validation.registerValidator(userNameTextField, Validator.createEmptyValidator("The username must not be empty."));
        validation.registerValidator(passwordTextField, Validator.createEmptyValidator("The password must not be empty."));

        editLoginButton.disableProperty().bind(validation.invalidProperty());

        loadLoginData();

        logger.info("LoginEditController initialized");
    }

    /**
     * Load passed data from the previous controller.
     */
    private void loadLoginData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof LoginBasicView) {
            LoginBasicView loginEditView = (LoginBasicView) stage.getUserData();
            idWorkerTextField.setText(String.valueOf(loginEditView.getIdWorker()));
            userNameTextField.setText(loginEditView.getUserName());
            passwordTextField.setText(loginEditView.getPassword());
        }
    }

    @FXML
    public void handleEditLoginButton(ActionEvent event) {

        Long idWorker = Long.valueOf(idWorkerTextField.getText());
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        LoginEditView loginEditView = new LoginEditView();
        loginEditView.setIdWorker(idWorker);
        loginEditView.setUserName(userName);
        loginEditView.setPassword(password);

        loginService.editLogin(loginEditView);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        loginEditedConfirmationDialog();
    }

    private void loginEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login Edited Confirmation");
        alert.setHeaderText("Your login was successfully edited.");


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
