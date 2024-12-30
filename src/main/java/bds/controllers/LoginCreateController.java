package bds.controllers;

import bds.api.LoginCreateView;
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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;
import bds.data.LoginRepository;
import bds.services.LoginService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginCreateController {

    private static final Logger logger = LoggerFactory.getLogger(LoginCreateController.class);

    @FXML
    private Button newLoginCreateButton;

    @FXML
    private TextField newLoginUserName;

    @FXML
    private TextField newLoginPassword;

    @FXML
    private TextField newLoginIdWorker;

    private LoginService loginService;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        LoginRepository loginRepository = new LoginRepository();
        loginService = new LoginService(loginRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newLoginUserName, Validator.createEmptyValidator("The username must not be empty."));
        validation.registerValidator(newLoginPassword, Validator.createEmptyValidator("The password must not be empty."));
        validation.registerValidator(newLoginIdWorker, Validator.createRegexValidator("ID Worker must be a number.", "\\d+", null));

        newLoginCreateButton.disableProperty().bind(validation.invalidProperty());
        logger.info("LoginCreateController initialized");
    }

    @FXML
    void handleCreateNewLogin(ActionEvent event) {

        String userName = newLoginUserName.getText();
        String password = newLoginPassword.getText();
        String workerId = newLoginIdWorker.getText();


        LoginCreateView loginCreateView = new LoginCreateView();
        loginCreateView.setUserName(userName);
        loginCreateView.setPassword(password.toCharArray());
        loginCreateView.setIdWorker(Integer.parseInt(workerId));

        System.out.println("created: " + loginCreateView.toString());

        loginService.createLogin(loginCreateView);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        loginCreatedConfirmationDialog();
    }

    private void loginCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login Created Confirmation");
        alert.setHeaderText("Your login was successfully created.");


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
