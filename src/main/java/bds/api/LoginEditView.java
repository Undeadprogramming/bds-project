package bds.api;

import java.util.Arrays;

public class LoginEditView {

    private Long idWorker;
    private String userName;
    private char[] password;

    public Long getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(Long idWorker) {
        this.idWorker = idWorker;
    }

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

    public void clearPassword() {
        if (this.password != null) {
            Arrays.fill(this.password, '\0');
        }
    }

    @Override
    public String toString() {
        return "LoginEditView{" +
                "userName='" + userName + '\'' +
                ", password='" + Arrays.toString(password) + '\'' +
                ", idWorker=" + idWorker +
                '}';
    }
}
