package bds.controllers;

import bds.App;
import bds.exceptions.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    public Button WorkerButton;

    public void handleSubmitButton(ActionEvent actionEvent) {

    }

    public void loginButton(ActionEvent actionEvent) {
        showPersonsView();
    }


    private void showPersonsView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Persons.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("Database login table");
            stage.setScene(scene);



            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/logo.png")));

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void workerButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Worker.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1150, 600);
            Stage stage = new Stage();
            stage.setTitle("Database login table");
            stage.setScene(scene);



            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/logo.png")));

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void attackButton(ActionEvent actionEvent) {
    }

    public void workplaceButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Workplace.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("Database login table");
            stage.setScene(scene);



            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/logo.png")));

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void clothesButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Clothes.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("Database login table");
            stage.setScene(scene);



            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/logo.png")));

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void customerButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Customer.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("Database login table");
            stage.setScene(scene);



            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/logo.png")));

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }
}
