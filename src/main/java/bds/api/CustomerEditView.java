package bds.api;

public class CustomerEditView {

    private Long idCustomer;        // Represents the customer ID
    private String firstName;       // Represents the customer's first name
    private String lastName;        // Represents the customer's last name

    // Getter and Setter methods
    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // toString method for displaying information about the customer
    @Override
    public String toString() {
        return "CustomerEditView{" +
                "idCustomer=" + idCustomer +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
