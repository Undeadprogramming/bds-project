package bds.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import bds.App;
import bds.api.CustomerBasicView;
import bds.data.CustomerRepository;
import bds.exceptions.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import bds.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @FXML
    public Button addCustomerButton;
    @FXML
    public Button refreshButton;
    public TextField customerIdFilterField;
    public TextField firstNameFilterField;
    public TextField lastNameFilterField;

    @FXML
    private TableColumn<CustomerBasicView, Long> customerId;
    @FXML
    private TableColumn<CustomerBasicView, String> firstName;
    @FXML
    private TableColumn<CustomerBasicView, String> lastName;
    @FXML
    private TableView<CustomerBasicView> customerTableView;

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private FilteredList<CustomerBasicView> filteredData;

    public CustomerController() {
    }

    @FXML
    private void initialize() {
        customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);

        customerId.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        ObservableList<CustomerBasicView> observableCustomerList = initializeCustomerData();
        filteredData = new FilteredList<>(observableCustomerList, p -> true);
        customerTableView.setItems(filteredData);

        customerTableView.getSortOrder().add(customerId);

        initializeFilters();
        initializeTableViewSelection();

        logger.info("CustomerController initialized");
    }

    private void initializeFilters() {
        customerIdFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        firstNameFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        lastNameFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));
    }

    private boolean applyFilters(CustomerBasicView customer) {
        // Filter by customer ID
        String customerIdText = customerIdFilterField.getText();
        if (customerIdText != null && !customerIdText.isEmpty()) {
            try {
                if (!String.valueOf(customer.getIdCustomer()).contains(customerIdText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false; // Invalid input doesn't match any record
            }
        }

        // Filter by first name
        String firstNameText = firstNameFilterField.getText();
        if (firstNameText != null && !firstNameText.isEmpty()) {
            if (!customer.getFirstName().toLowerCase().contains(firstNameText.toLowerCase())) {
                return false;
            }
        }

        // Filter by last name
        String lastNameText = lastNameFilterField.getText();
        if (lastNameText != null && !lastNameText.isEmpty()) {
            if (!customer.getLastName().toLowerCase().contains(lastNameText.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit customer");
        MenuItem delete = new MenuItem("Delete customer");

        edit.setOnAction((ActionEvent event) -> {
            CustomerBasicView customerView = customerTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/CustomerEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(customerView);
                stage.setTitle("BDS JavaFX Edit Customer");

                CustomerEditController controller = new CustomerEditController();
                controller.setStage(stage);

                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 400);

                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        delete.setOnAction((ActionEvent event) -> {
            CustomerBasicView selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                // Confirm deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("Are you sure you want to delete this customer?");
                alert.setContentText("This action cannot be undone.");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            customerService.deleteCustomer(selectedCustomer);

                            handleRefreshButton(new ActionEvent());

                            logger.info("Customer with ID {} deleted successfully.", selectedCustomer.getIdCustomer());
                        } catch (Exception ex) {
                            ExceptionHandler.handleException(ex);
                        }
                    }
                });
            }
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(delete);
        customerTableView.setContextMenu(menu);
    }

    private ObservableList<CustomerBasicView> initializeCustomerData() {
        List<CustomerBasicView> customers = customerService.getCustomerBasicView();
        return FXCollections.observableArrayList(customers);
    }

    public void handleAddCustomerButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/CustomerCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Create Customer");
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<CustomerBasicView> observableCustomerList = initializeCustomerData();
        filteredData = new FilteredList<>(observableCustomerList, p -> true);
        customerTableView.setItems(filteredData);
        customerTableView.refresh();
        customerTableView.sort();
    }
}
