package bds.services;

import bds.api.LoginAuthView;

import bds.data.LoginRepository;
import bds.data.WorkerRepository;
import bds.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static bds.services.Argon2FactoryService.ARGON2;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private WorkerRepository workerRepository;
    private LoginRepository loginRepository;

    public AuthService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    private LoginAuthView findloginByUsername(String username) {
        return loginRepository.findLoginByUsername(username);
    }

    public boolean authenticate(String username, String password) {


        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        LoginAuthView loginAuthView = findloginByUsername(username);


        if (loginAuthView== null) {
            throw new ResourceNotFoundException("Provided username is not found.");
        }
        System.out.println(loginAuthView.getPassword());
        System.out.println(password.toCharArray());

        if(!loginAuthView.getPassword().startsWith("$argon2"))
        {
            String str = new String(ARGON2.hash(10, 65536, 1, loginAuthView.getPassword()));
            loginAuthView.setPassword(str);
        }

        logger.info(" password mine:{}",password.toCharArray());
        logger.info(" password data:{}",loginAuthView.getPassword());
        logger.info(" result:{}",ARGON2.verify(loginAuthView.getPassword(), password.toCharArray()));
        return ARGON2.verify(loginAuthView.getPassword(), password.toCharArray());
    }

}
