package bds.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import bds.App;
import bds.api.LoginBasicView;
import bds.api.LoginDetailView;
import bds.data.LoginRepository;
import bds.exceptions.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import bds.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
public class LoginsController {

    private static final Logger logger = LoggerFactory.getLogger(LoginsController.class);

    @FXML
    public Button addPersonButton;
    @FXML
    public Button refreshButton;
    public TextField workerIdFilterField;
    public TextField usernameFilterField;
    public TextField passwordFilterField;
    @FXML
    private TableColumn<LoginBasicView, Long> workersId;
    @FXML
    private TableColumn<LoginBasicView, String> username;
    @FXML
    private TableColumn<LoginBasicView, String> password;
    @FXML
    private TableView<LoginBasicView> systemPersonsTableView;
//    @FXML
//    public MenuItem exitMenuItem;

    private LoginService LoginService;
    private LoginRepository LoginRepository;
    private FilteredList<LoginBasicView> filteredData;

    public LoginsController() {
    }

    @FXML
    private void initialize() {
        LoginRepository = new LoginRepository();
        LoginService = new LoginService(LoginRepository);
//        GlyphsDude.setIcon(exitMenuItem, FontAwesomeIcon.CLOSE, "1em");

        workersId.setCellValueFactory(new PropertyValueFactory<LoginBasicView, Long>("idWorker"));
        username.setCellValueFactory(new PropertyValueFactory<LoginBasicView,String>("userName"));
        password.setCellValueFactory(new PropertyValueFactory<LoginBasicView, String>("password"));



        ObservableList<LoginBasicView> observablePersonsList = initializePersonsData();
        filteredData = new FilteredList<>(observablePersonsList, p -> true);
        systemPersonsTableView.setItems(filteredData);

        systemPersonsTableView.getSortOrder().add(workersId);

        workerIdFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        usernameFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        passwordFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(this::applyFilters));

        initializeTableViewSelection();
        loadIcons();

        logger.info("LoginsController initialized");
    }

    private boolean applyFilters(LoginBasicView login) {
        // Filter by worker ID
        String workerIdText = workerIdFilterField.getText();
        if (workerIdText != null && !workerIdText.isEmpty()) {
            try {
                if (!String.valueOf(login.getIdWorker()).contains(workerIdText)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false; // Invalid input doesn't match any record
            }
        }

        // Filter by username
        String usernameText = usernameFilterField.getText();
        if (usernameText != null && !usernameText.isEmpty()) {
            if (!login.getUserName().toLowerCase().contains(usernameText.toLowerCase())) {
                return false;
            }
        }

        // Filter by password
        String passwordText = passwordFilterField.getText();
        if (passwordText != null && !passwordText.isEmpty()) {
            if (!login.getPassword().toLowerCase().contains(passwordText.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit login");
        MenuItem detailedView = new MenuItem("Detailed login view");
        MenuItem delete = new MenuItem("Delete login");

        edit.setOnAction((ActionEvent event) -> {
            LoginBasicView loginView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(loginView);
                stage.setTitle("BDS JavaFX Edit login");

                LoginEditController controller = new LoginEditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        detailedView.setOnAction((ActionEvent event) -> {
            LoginBasicView loginView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonsDetailView.fxml"));
                Stage stage = new Stage();

                Long workerId = loginView.getIdWorker();
                LoginDetailView LoginDetailView = LoginService.getLoginDetailView(workerId);

                stage.setUserData(LoginDetailView);
                stage.setTitle("BDS JavaFX Login Detailed View");

                LoginDetailViewController controller = new LoginDetailViewController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        delete.setOnAction((ActionEvent event) -> {
            LoginBasicView selectedLogin = systemPersonsTableView.getSelectionModel().getSelectedItem();
            if (selectedLogin != null) {
                // Confirm deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("Are you sure you want to delete this login?");
                alert.setContentText("This action cannot be undone.");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            // Perform deletion in data source (e.g., repository or database)
                            LoginService.deleteLogin(selectedLogin); // Replace with your actual delete method

                            handleRefreshButton(new ActionEvent());

                            logger.info("Login with worker ID {} deleted successfully.", selectedLogin.getIdWorker());
                        } catch (Exception ex) {
                            ExceptionHandler.handleException(ex);
                        }
                    }
                });
            }
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView, delete);
        systemPersonsTableView.setContextMenu(menu);
    }

    private ObservableList<LoginBasicView> initializePersonsData() {
        List<LoginBasicView> persons = LoginService.getLoginBasicView();
        return FXCollections.observableArrayList(persons);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("logos/logo.png"));
        ImageView vutLogo = new ImageView(vutLogoImage);
        vutLogo.setFitWidth(150);
        vutLogo.setFitHeight(50);
    }

    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    public void handleAddPersonButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/PersonsCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Create Person");
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<LoginBasicView> observablePersonsList = initializePersonsData();
        filteredData = new FilteredList<>(observablePersonsList, p -> true);
        systemPersonsTableView.setItems(filteredData);
        systemPersonsTableView.refresh();
        systemPersonsTableView.sort();
    }
}
