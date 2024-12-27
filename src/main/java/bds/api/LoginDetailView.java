package bds.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginDetailView {
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private LongProperty idWorker = new SimpleLongProperty();

    // Getters and setters
    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Long getIdWorker() {
        return idWorker.get();
    }

    public void setIdWorker(Long idWorker) {
        this.idWorker.set(idWorker);
    }

    // Property accessors for binding
    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public LongProperty idWorkerProperty() {
        return idWorker;
    }
}
