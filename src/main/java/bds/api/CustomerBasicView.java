package bds.api;

import javafx.beans.property.*;

public class CustomerBasicView {
    private LongProperty idCustomer = new SimpleLongProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();



    public Long getIdCustomer() {
        return idCustomerProperty().get();
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomerProperty().set(idCustomer);
    }

    public String getFirstName() {
        return firstNameProperty().get();
    }

    public void setFirstName(String firstName) {
        this.firstNameProperty().set(firstName);
    }

    public String getLastName() {
        return lastNameProperty().get();
    }

    public void setLastName(String lastName) {
        this.lastNameProperty().set(lastName);
    }



    public LongProperty idCustomerProperty() {
        return idCustomer;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    @Override
    public String toString() {
        return "CustomerBasicView{" +
                "idCustomer=" + idCustomer.get() +
                ", firstName='" + firstName.get() + '\'' +
                ", lastName='" + lastName.get() + '\'' +
                '}';
    }
}
