package bds.api;

import java.util.Arrays;

public class CustomerCreateView {

    private String userName;
    private char[] password;
    private int idCustomer;

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    // toString method
    @Override
    public String toString() {
        return "CustomerLoginCreateView{" +
                "userName='" + userName + '\'' +
                ", password='" + Arrays.toString(password) + '\'' +
                ", idCustomer=" + idCustomer +
                '}';
    }
}
