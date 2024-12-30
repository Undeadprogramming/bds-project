package bds.controllers;

import bds.api.CustomerCreateView;
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
import bds.data.CustomerRepository;
import bds.services.CustomerService;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerCreateController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCreateController.class);

    @FXML
    private Button newCustomerCreateButton;

    @FXML
    private TextField newCustomerFirstName;

    @FXML
    private TextField newCustomerLastName;

    private CustomerService customerService;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        CustomerRepository customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newCustomerFirstName, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(newCustomerLastName, Validator.createEmptyValidator("The last name must not be empty."));

        newCustomerCreateButton.disableProperty().bind(validation.invalidProperty());
        logger.info("CustomerCreateController initialized");
    }

    @FXML
    void handleCreateNewCustomer(ActionEvent event) {

        String firstName = newCustomerFirstName.getText();
        String lastName = newCustomerLastName.getText();


        CustomerCreateView customerCreateView = new CustomerCreateView();
        customerCreateView.setFirstName(firstName);
        customerCreateView.setLastName(lastName);


        customerService.createCustomer(customerCreateView);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();


        customerCreatedConfirmationDialog();
    }

    private void customerCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Customer Created Confirmation");
        alert.setHeaderText("Your customer was successfully created.");


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
