package bds.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        systemPersonsTableView.setItems(observablePersonsList);

        systemPersonsTableView.getSortOrder().add(workersId);

        initializeTableViewSelection();
        loadIcons();

        logger.info("PersonsController initialized");
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit person");
        MenuItem detailedView = new MenuItem("Detailed person view");
        edit.setOnAction((ActionEvent event) -> {
            LoginBasicView loginView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(loginView);
                stage.setTitle("BDS JavaFX Edit Person");

                PersonsEditController controller = new PersonsEditController();
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
                stage.setTitle("BDS JavaFX Persons Detailed View");

                PersonsDetailViewController controller = new PersonsDetailViewController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });


        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView);
        systemPersonsTableView.setContextMenu(menu);
    }

    private ObservableList<LoginBasicView> initializePersonsData() {
        List<LoginBasicView> persons = LoginService.getLoginBasicView();
        return FXCollections.observableArrayList(persons);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("logos/vut-logo-eng.png"));
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

//            Stage stageOld = (Stage) signInButton.getScene().getWindow();
//            stageOld.close();
//
//            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/vut.jpg")));
//            authConfirmDialog();

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<LoginBasicView> observablePersonsList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonsList);
        systemPersonsTableView.refresh();
        systemPersonsTableView.sort();
    }
}
