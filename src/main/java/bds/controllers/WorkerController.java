package bds.controllers;

import bds.data.WorkerRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import bds.App;
import bds.api.WorkerBasicView;
import bds.services.WorkerService;
import bds.exceptions.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class WorkerController {

    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @FXML
    public Button addWorkerButton;
    @FXML
    public Button refreshButton;
    public TextField workerIdFilterField;
    public TextField firstNameFilterField;
    public TextField middleNameFilterField; // Added for middle name filter
    public TextField lastNameFilterField;
    public TextField ageFilterField;
    public TextField genderFilterField;
    public TextField positionFilterField;
    public TextField salaryFilterField;

    @FXML
    private TableColumn<WorkerBasicView, Long> workerId;
    @FXML
    private TableColumn<WorkerBasicView, String> firstName;
    @FXML
    private TableColumn<WorkerBasicView, String> middleName; // TableColumn for middle name
    @FXML
    private TableColumn<WorkerBasicView, String> lastName;
    @FXML
    private TableColumn<WorkerBasicView, Integer> age;
    @FXML
    private TableColumn<WorkerBasicView, String> gender;
    @FXML
    private TableColumn<WorkerBasicView, String> position;
    @FXML
    private TableColumn<WorkerBasicView, Integer> salary;
    @FXML
    private TableView<WorkerBasicView> workerTableView;

    private WorkerService workerService;
    private WorkerRepository workerRepository;
    private FilteredList<WorkerBasicView> filteredData;

    @FXML
    private void initialize() {
        workerRepository = new WorkerRepository();  // Instantiate WorkerRepository
        workerService = new WorkerService(workerRepository);  // Instantiate WorkerService with WorkerRepository

        workerId.setCellValueFactory(new PropertyValueFactory<>("idWorker"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        ObservableList<WorkerBasicView> observableWorkerList = initializeWorkerData();
        filteredData = new FilteredList<>(observableWorkerList, p -> true);
        workerTableView.setItems(filteredData);

        workerTableView.getSortOrder().add(workerId);

        initializeFilters();
        initializeTableViewSelection();

        logger.info("WorkerController initialized");
    }

    private void initializeFilters() {
        workerIdFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        firstNameFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        middleNameFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters)); // Added listener for middle name filter

        lastNameFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        ageFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        genderFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        positionFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        salaryFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));
    }

    private boolean applyFilters(WorkerBasicView worker) {
        // Filter by worker ID
        String workerIdText = workerIdFilterField.getText();
        if (workerIdText != null && !workerIdText.isEmpty()) {
            try {
                if (!String.valueOf(worker.getIdWorker()).contains(workerIdText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false; // Invalid input doesn't match any record
            }
        }

        // Filter by first name
        String firstNameText = firstNameFilterField.getText();
        if (firstNameText != null && !firstNameText.isEmpty()) {
            if (!worker.getFirstName().toLowerCase().contains(firstNameText.toLowerCase())) {
                return false;
            }
        }

        // Filter by middle name
        String middleNameText = middleNameFilterField.getText(); // Added filtering by middle name
        if (middleNameText != null && !middleNameText.isEmpty()) {
            if (!worker.getMiddleName().toLowerCase().contains(middleNameText.toLowerCase())) {
                return false;
            }
        }

        // Filter by last name
        String lastNameText = lastNameFilterField.getText();
        if (lastNameText != null && !lastNameText.isEmpty()) {
            if (!worker.getLastName().toLowerCase().contains(lastNameText.toLowerCase())) {
                return false;
            }
        }

        // Filter by age
        String ageText = ageFilterField.getText();
        if (ageText != null && !ageText.isEmpty()) {
            try {
                if (worker.getAge() != Integer.parseInt(ageText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        // Filter by gender
        String genderText = genderFilterField.getText();
        if (genderText != null && !genderText.isEmpty()) {
            if (!worker.getGender().toLowerCase().contains(genderText.toLowerCase())) {
                return false;
            }
        }

        // Filter by position
        String positionText = positionFilterField.getText();
        if (positionText != null && !positionText.isEmpty()) {
            if (!worker.getPosition().toLowerCase().contains(positionText.toLowerCase())) {
                return false;
            }
        }

        // Filter by salary
        String salaryText = salaryFilterField.getText();
        if (salaryText != null && !salaryText.isEmpty()) {
            try {
                if (worker.getSalary() != Integer.parseInt(salaryText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit worker");
        MenuItem delete = new MenuItem("Delete worker");

        edit.setOnAction((ActionEvent event) -> {
            WorkerBasicView workerView = workerTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/WorkerEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(workerView);
                stage.setTitle("BDS JavaFX Edit Worker");

                WorkerEditController controller = new WorkerEditController();
                controller.setStage(stage);

                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 700, 650);

                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        delete.setOnAction((ActionEvent event) -> {
            WorkerBasicView selectedWorker = workerTableView.getSelectionModel().getSelectedItem();
            if (selectedWorker != null) {
                // Confirm deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("Are you sure you want to delete this worker?");
                alert.setContentText("This action cannot be undone.");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            workerService.deleteWorker(selectedWorker);

                            handleRefreshButton(new ActionEvent());

                            logger.info("Worker with ID {} deleted successfully.", selectedWorker.getIdWorker());
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
        workerTableView.setContextMenu(menu);
    }

    private ObservableList<WorkerBasicView> initializeWorkerData() {
        List<WorkerBasicView> workers = workerService.getWorkerBasicView();
        return FXCollections.observableArrayList(workers);
    }

    public void handleAddWorkerButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/WorkerCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 650);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Create Worker");
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<WorkerBasicView> observableWorkerList = initializeWorkerData();
        filteredData = new FilteredList<>(observableWorkerList, p -> true);
        workerTableView.setItems(filteredData);
        workerTableView.refresh();
        workerTableView.sort();
    }
}
