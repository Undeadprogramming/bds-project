package bds.controllers;

import bds.api.LoginDetailView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import bds.App;
import bds.data.LoginRepository;
import bds.services.LoginService;
import bds.exceptions.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginDetailViewController {

    private static final Logger logger = LoggerFactory.getLogger(LoginDetailViewController.class);

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField idWorkerTextField;

    // used to reference the stage and to get passed data through it
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        userNameTextField.setEditable(false);
        passwordTextField.setEditable(false);
        idWorkerTextField.setEditable(false);

        loadLoginData();

        logger.info("LoginDetailedView initialized");
    }

    private void loadLoginData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof LoginDetailView) {
            LoginDetailView loginDetailView = (LoginDetailView) stage.getUserData();
            userNameTextField.setText(loginDetailView.getUserName());
            passwordTextField.setText(loginDetailView.getPassword());
            idWorkerTextField.setText(String.valueOf(loginDetailView.getIdWorker()));
        }
    }

}
