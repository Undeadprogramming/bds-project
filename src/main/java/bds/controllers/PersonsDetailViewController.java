package bds.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import bds.api.PersonDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonsDetailViewController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsDetailViewController.class);

    @FXML
    private TextField idTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField givenNameTextField;

    @FXML
    private TextField familyNameTextField;

    @FXML
    private TextField nicknameTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField houseNumberTextField;

    @FXML
    private TextField streetTextField;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        idTextField.setEditable(false);
        emailTextField.setEditable(false);
        givenNameTextField.setEditable(false);
        familyNameTextField.setEditable(false);
        nicknameTextField.setEditable(false);
        cityTextField.setEditable(false);
        houseNumberTextField.setEditable(false);
        streetTextField.setEditable(false);

        loadPersonsData();

        logger.info("PersonsDetailViewController initialized");
    }

    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof PersonDetailView) {
            PersonDetailView personBasicView = (PersonDetailView) stage.getUserData();
            idTextField.setText(String.valueOf(personBasicView.getId()));
            emailTextField.setText(personBasicView.getEmail());
            givenNameTextField.setText(personBasicView.getGivenName());
            familyNameTextField.setText(personBasicView.getFamilyName());
            nicknameTextField.setText(personBasicView.getNickname());
            cityTextField.setText(personBasicView.getCity());
            houseNumberTextField.setText(personBasicView.gethouseNumber());
            streetTextField.setText(personBasicView.getStreet());
        }
    }

}
