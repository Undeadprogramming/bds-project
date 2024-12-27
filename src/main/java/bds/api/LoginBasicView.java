package bds.api;

import javafx.beans.property.*;

public class LoginBasicView {
    private LongProperty idWorker = new SimpleLongProperty();
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();

    public Long getIdWorker() {
        return idWorkerProperty().get();
    }

    public void setIdWorker(Long idWorker) {
        this.idWorkerProperty().set(idWorker);
    }

    public String getUserName() {
        return userNameProperty().get();
    }

    public void setUserName(String userName) {
        this.userNameProperty().set(userName);
    }

    public String getPassword() {
        return passwordProperty().get();
    }

    public void setPassword(String password) {
        this.passwordProperty().set(password);
    }

    public LongProperty idWorkerProperty() {
        return idWorker;
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
