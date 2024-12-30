package bds.api;

public class CustomerEditView {

    private Long idCustomer;        // Represents the customer ID
    private String userName;        // Represents the username (login)
    private String password;        // Represents the password for the login

    // Getter and Setter methods
    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method for displaying information about the customer login
    @Override
    public String toString() {
        return "CustomerLoginEditView{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", idCustomer=" + idCustomer +
                '}';
    }
}
