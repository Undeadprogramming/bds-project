package bds.data;

import bds.api.*;
import bds.config.DataSourceConfig;
import bds.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static bds.services.Argon2FactoryService.ARGON2;

public class LoginRepository {
    public void editLogin(LoginEditView loginEditView) {
        String updateLoginSQL = "UPDATE bds.login SET user_name = ?, password = ? WHERE id_worker = ?";

        try (Connection connection = DataSourceConfig.getConnection()) {
            connection.setAutoCommit(false);



            try (PreparedStatement updateStmt = connection.prepareStatement(updateLoginSQL)) {
                String text = String.valueOf( loginEditView.getPassword());
                updateStmt.setString(1, loginEditView.getUserName());
                updateStmt.setString(2, text);
                updateStmt.setLong(3, loginEditView.getIdWorker());

                int affectedRows = updateStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Updating login failed, no rows affected.");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Error updating login. Changes rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating login: Operation failed.", e);
        }
    }


    public List<LoginBasicView> getLoginsBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT user_name, password, id_worker " +
                             "FROM bds.login");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<LoginBasicView> loginBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                loginBasicViews.add(mapToLoginBasicView(resultSet));
            }
            return loginBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Logins basic view could not be loaded.", e);
        }
    }

    public LoginAuthView findLoginByUsername(String username) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT user_name, password" +
                             " FROM bds.login l" +
                             " WHERE l.user_name = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToLoginAuth(resultSet);
                }
            }
        } catch (SQLException e) {

            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();

            throw new DataAccessException("Find login by username with addresses failed.", e);
        }
        return null;
    }
    public LoginDetailView findLoginDetailView(Long workerId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT l.user_name, l.password, l.id_worker, w.first_name, w.last_name " +
                             "FROM bds.login AS l " +
                             "JOIN bds.worker AS w ON w.id_worker = l.id_worker " +
                             "WHERE l.id_worker = ?")) {
            preparedStatement.setLong(1, workerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToLoginDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find login by worker ID failed.", e);
        }
        return null;
    }


    public void createLogin(LoginCreateView loginCreateView) {
        String insertLoginSQL = "INSERT INTO bds.login (user_name, password, id_worker) VALUES (?, ?, ?)";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertLoginSQL, Statement.RETURN_GENERATED_KEYS)) {

            String text = String.valueOf( loginCreateView.getPassword());
            preparedStatement.setString(1, loginCreateView.getUserName());
            preparedStatement.setString(2, text);
            preparedStatement.setInt(3, loginCreateView.getIdWorker());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating login failed, no rows affected.");
            }


        } catch (SQLException e) {
            throw new DataAccessException("Creating login failed, operation on the database failed.", e);
        }
    }

    public void deleteLogin(Long idWorker) {
        String deleteLoginSQL = "DELETE FROM bds.login WHERE id_worker = ?";
        try (Connection connection = DataSourceConfig.getConnection()) {
            connection.setAutoCommit(false);


            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteLoginSQL)) {
                deleteStmt.setLong(1, idWorker);

                int affectedRows = deleteStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Deleting login failed, no rows affected.");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Error deleting login. Changes rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting login: Operation failed.", e);
        }
    }


    private LoginAuthView mapToLoginAuth(ResultSet rs) throws SQLException {
        LoginAuthView login = new LoginAuthView();
        login.setUserName(rs.getString("user_name"));
        login.setPassword(rs.getString("password"));
        return login;
    }

    private LoginDetailView mapToLoginDetailView(ResultSet rs) throws SQLException {
        LoginDetailView loginDetailView = new LoginDetailView();
        loginDetailView.setUserName(rs.getString("user_name"));
        loginDetailView.setPassword(rs.getString("password"));
        loginDetailView.setIdWorker(rs.getLong("id_worker"));
        loginDetailView.setFirstname(rs.getString("first_name"));
        loginDetailView.setLastname(rs.getString("last_name"));
        return loginDetailView;
    }
    private LoginBasicView mapToLoginBasicView(ResultSet rs) throws SQLException {
        LoginBasicView loginBasicView = new LoginBasicView();
        loginBasicView.setUserName(rs.getString("user_name"));
        loginBasicView.setPassword(rs.getString("password"));
        loginBasicView.setIdWorker(rs.getLong("id_worker"));

        if(!loginBasicView.getPassword().startsWith("$argon2"))
        {
            String str = new String(ARGON2.hash(10, 65536, 1, loginBasicView.getPassword()));
            loginBasicView.setPassword(str);
            LoginEditView loginEditView = new LoginEditView();
            loginEditView.setUserName(loginBasicView.getUserName());
            loginEditView.setPassword(loginBasicView.getPassword().toCharArray());
            loginEditView.setIdWorker(loginBasicView.getIdWorker());

            editLogin(loginEditView);
        }

        return loginBasicView;
    }
}
