package bds.api;

public class LoginEditView {

    private Long idWorker;
    private String userName;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginEditView{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", idWorker=" + idWorker +
                '}';
    }
}
