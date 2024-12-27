package bds.api;

import java.util.Arrays;

public class LoginCreateView {

    private String userName;
    private char[] password;
    private int idWorker;

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

    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    // toString method
    @Override
    public String toString() {
        return "LoginCreateView{" +
                "userName='" + userName + '\'' +
                ", password='" + Arrays.toString(password) + '\'' +
                ", idWorker=" + idWorker +
                '}';
    }
}
