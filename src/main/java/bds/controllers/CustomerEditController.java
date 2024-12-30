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
import bds.api.CustomerBasicView;
import bds.api.CustomerEditView;
import bds.data.CustomerRepository;
import bds.services.CustomerService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CustomerEditController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerEditController.class);

    @FXML
    public Button editCustomerButton;
    @FXML
    public TextField idCustomerTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private ValidationSupport validation;


    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);

        validation = new ValidationSupport();
        validation.registerValidator(idCustomerTextField, Validator.createEmptyValidator("The customer ID must not be empty."));
        idCustomerTextField.setEditable(false);
        validation.registerValidator(firstNameTextField, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(lastNameTextField, Validator.createEmptyValidator("The last name must not be empty."));

        editCustomerButton.disableProperty().bind(validation.invalidProperty());

        loadCustomerData();

        logger.info("CustomerEditController initialized");
    }


    private void loadCustomerData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof CustomerBasicView) {
            CustomerBasicView customerEditView = (CustomerBasicView) stage.getUserData();
            idCustomerTextField.setText(String.valueOf(customerEditView.getIdCustomer()));
            firstNameTextField.setText(customerEditView.getFirstName());
            lastNameTextField.setText(customerEditView.getLastName());
        }
    }

    @FXML
    public void handleEditCustomerButton(ActionEvent event) {

        Long idCustomer = Long.valueOf(idCustomerTextField.getText());
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        CustomerEditView customerEditView = new CustomerEditView();
        customerEditView.setIdCustomer(idCustomer);
        customerEditView.setFirstName(firstName);
        customerEditView.setLastName(lastName);

        customerService.editCustomer(customerEditView);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        customerEditedConfirmationDialog();
    }

    private void customerEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Customer Edited Confirmation");
        alert.setHeaderText("The customer record was successfully edited.");


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
