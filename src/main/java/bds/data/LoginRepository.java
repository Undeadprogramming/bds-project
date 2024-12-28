package bds.data;

import bds.api.*;
import bds.config.DataSourceConfig;
import bds.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginRepository {
    public void editLogin(LoginEditView loginEditView) {
        String updateLoginSQL = "UPDATE bds.login SET user_name = ?, password = ? WHERE id_worker = ?";
        String checkIfExists = "SELECT user_name FROM bds.login WHERE id_worker = ?";

        try (Connection connection = DataSourceConfig.getConnection()) {
            connection.setAutoCommit(false); // Disable autoCommit for manual transaction handling

            try (PreparedStatement checkStmt = connection.prepareStatement(checkIfExists)) {
                checkStmt.setLong(1, loginEditView.getIdWorker());
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new DataAccessException("This login for the worker does not exist.");
                    }
                }
            }

            try (PreparedStatement updateStmt = connection.prepareStatement(updateLoginSQL)) {
                updateStmt.setString(1, loginEditView.getUserName());
                updateStmt.setString(2, loginEditView.getPassword());
                updateStmt.setLong(3, loginEditView.getIdWorker());

                int affectedRows = updateStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Updating login failed, no rows affected.");
                }

                connection.commit(); // Commit the transaction if no exceptions occurred
            } catch (SQLException e) {
                connection.rollback(); // Rollback in case of errors
                throw new DataAccessException("Error updating login. Changes rolled back.", e);
            } finally {
                connection.setAutoCommit(true); // Restore autoCommit to default
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

    public LoginAuthView findPersonByEmail(String email) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT user_name, password" +
                             " FROM bds.login l" +
                             " WHERE l.user_name = ?")) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToLoginAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            // Log the exception
            System.err.println("SQL Exception occurred: " + e.getMessage()); // Quick debugging
            e.printStackTrace(); // For full stack trace (optional)
            // OR use a logger for better error management
            // logger.error("Find person by email failed: {}", email, e);
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }
    public LoginDetailView findLoginDetailView(Long workerId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT user_name, password, id_worker " +
                             "FROM bds.login " +
                             "WHERE id_worker = ?")) {
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

            // Nastavení parametrů do dotazu
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
        return loginDetailView;
    }
    private LoginBasicView mapToLoginBasicView(ResultSet rs) throws SQLException {
        LoginBasicView loginBasicView = new LoginBasicView();
        loginBasicView.setUserName(rs.getString("user_name"));
        loginBasicView.setPassword(rs.getString("password"));
        loginBasicView.setIdWorker(rs.getLong("id_worker"));
        return loginBasicView;
    }



}
