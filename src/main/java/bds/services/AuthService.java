package bds.services;

import bds.api.LoginAuthView;

import bds.controllers.LoginController;
import bds.data.LoginRepository;
import bds.data.PersonRepository;
import bds.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static bds.services.Argon2FactoryService.ARGON2;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private PersonRepository personRepository;
    private LoginRepository loginRepository;

    public AuthService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    private LoginAuthView findloginByEmail(String email) {
        return loginRepository.findPersonByEmail(email);
    }

    public boolean authenticate(String username, String password) {


        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        LoginAuthView loginAuthView = findloginByEmail(username);


        if (loginAuthView== null) {
            throw new ResourceNotFoundException("Provided username is not found.");
        }
        String str = new String(ARGON2.hash(10, 65536, 1, password).toCharArray());
        loginAuthView.setPassword(str);
        //System.out.println(loginAuthView.getPassword());
        //System.out.println(password.toCharArray());
        //return ARGON2.verify(loginAuthView.getPassword(), password.toCharArray());
        logger.info(" password mine:{}",password.toCharArray());
        logger.info(" password data:{}",loginAuthView.getPassword());
        logger.info(" result:{}",ARGON2.verify(loginAuthView.getPassword(), password.toCharArray()));
        return ARGON2.verify(loginAuthView.getPassword(), password.toCharArray());
    }

}
