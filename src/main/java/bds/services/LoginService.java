package bds.services;

import bds.api.*;
import bds.data.LoginRepository;

import java.util.List;

import static bds.services.Argon2FactoryService.ARGON2;

/**
 * Class representing business logic on top of the Persons
 */
public class LoginService {



    private LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginDetailView getLoginDetailView(Long id) {
        return loginRepository.findLoginDetailView(id);
    }

    public List<LoginBasicView> getLoginBasicView() {
        return loginRepository.getLoginsBasicView();
    }

    public void createLogin(LoginCreateView loginCreateView) {

        char[] originalPassword = loginCreateView.getPassword();
        char[] hashedPassword = hashPassword(originalPassword);
        loginCreateView.setPassword(hashedPassword);

        loginRepository.createLogin(loginCreateView);
        System.out.println("Login was created successfully for user: " + loginCreateView.getUserName());
    }

    public void editLogin(LoginEditView loginEditView) {
        loginRepository.editLogin(loginEditView);
    }

    /**
     * <p>
     * Note: For implementation details see: https://github.com/phxql/argon2-jvm
     * </p>
     *
     * @param password to be hashed
     * @return hashed password
     */
    public char[] hashPassword(char[] password) {
        return ARGON2.hash(10, 65536, 1, password).toCharArray();
    }

    public void deleteLogin(LoginBasicView selectedLogin) {
        loginRepository.deleteLogin(selectedLogin.getIdWorker());
    }
}