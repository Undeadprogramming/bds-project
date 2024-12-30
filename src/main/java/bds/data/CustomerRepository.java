package bds.data;

import bds.api.*;
import bds.config.DataSourceConfig;
import bds.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    public void editCustomer(CustomerEditView customerEditView) {
        String updatecustomerQL = "UPDATE bds.customer SET first_name = ?, last_name = ? WHERE id_customer = ?";

        try (Connection connection = DataSourceConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement updateStmt = connection.prepareStatement(updatecustomerQL)) {
                updateStmt.setString(1, customerEditView.getFirstName());
                updateStmt.setString(2, customerEditView.getLastName());
                updateStmt.setLong(3, customerEditView.getIdCustomer());

                int affectedRows = updateStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Updating customer failed, no rows affected.");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Error updating customer. Changes rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating customer: Operation failed.", e);
        }
    }

    public List<CustomerBasicView> getCustomerBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_customer, first_name, last_name FROM bds.customer");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<CustomerBasicView> customerBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                customerBasicViews.add(mapToCustomerBasicView(resultSet));
            }
            return customerBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Customer basic view could not be loaded.", e);
        }
    }

    public void createCustomer(CustomerCreateView customerCreateView) {
        String insertcustomerQL = "INSERT INTO bds.customer (first_name, last_name) VALUES (?, ?)";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertcustomerQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, customerCreateView.getFirstName());
            preparedStatement.setString(2, customerCreateView.getLastName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating customer failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Creating customer failed, operation on the database failed.", e);
        }
    }

    public void deleteCustomer(Long idCustomer) {
        String deletecustomerQL = "DELETE FROM bds.customer WHERE id_customer = ?";
        try (Connection connection = DataSourceConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteStmt = connection.prepareStatement(deletecustomerQL)) {
                deleteStmt.setLong(1, idCustomer);

                int affectedRows = deleteStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Deleting customer failed, no rows affected.");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Error deleting customer. Changes rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting customer: Operation failed.", e);
        }
    }

    private CustomerBasicView mapToCustomerBasicView(ResultSet rs) throws SQLException {
        CustomerBasicView customerBasicView = new CustomerBasicView();
        customerBasicView.setIdCustomer(rs.getLong("id_customer"));
        customerBasicView.setFirstName(rs.getString("first_name"));
        customerBasicView.setLastName(rs.getString("last_name"));
        return customerBasicView;
    }
}
