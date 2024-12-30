package bds.data;

import bds.api.*;
import bds.config.DataSourceConfig;
import bds.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerRepository {

    // Fetch worker details by worker ID
    public WorkerDetailView findWorkerDetailView(Long workerId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_worker, first_name, middle_name, last_name, age, gender, position, salary" +
                             " FROM bds.worker w" +
                             " WHERE w.id_worker = ?")) {
            preparedStatement.setLong(1, workerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToWorkerDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find worker by ID failed.", e);
        }
        return null;
    }

    // Fetch a list of all workers (basic view)
    public List<WorkerBasicView> getWorkersBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_worker, first_name, middle_name, last_name, age, gender, position, salary" +
                             " FROM bds.worker");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<WorkerBasicView> workerBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                workerBasicViews.add(mapToWorkerBasicView(resultSet));
            }
            return workerBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Workers basic view could not be loaded.", e);
        }
    }

    // Create a new worker in the database
    public void createWorker(WorkerCreateView workerCreateView) {
        String insertWorkerSQL = "INSERT INTO bds.worker (first_name, middle_name, last_name, age, gender, position, salary) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertWorkerSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, workerCreateView.getFirstName());
            preparedStatement.setString(2, workerCreateView.getMiddleName());
            preparedStatement.setString(3, workerCreateView.getLastName());
            preparedStatement.setInt(4, workerCreateView.getAge());
            preparedStatement.setString(5, workerCreateView.getGender());
            preparedStatement.setString(6, workerCreateView.getPosition());
            preparedStatement.setInt(7, workerCreateView.getSalary());  // Set as int

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating worker failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating worker failed due to database operation error.", e);
        }
    }

    // Edit an existing worker's information
    public void editWorker(WorkerEditView workerEditView) {
        String updateWorkerSQL = "UPDATE bds.worker SET first_name = ?, middle_name = ?, last_name = ?, age = ?, gender = ?, position = ?, salary = ? WHERE id_worker = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateWorkerSQL)) {
            preparedStatement.setString(1, workerEditView.getFirstName());
            preparedStatement.setString(2, workerEditView.getMiddleName());
            preparedStatement.setString(3, workerEditView.getLastName());
            preparedStatement.setInt(4, workerEditView.getAge());
            preparedStatement.setString(5, workerEditView.getGender());
            preparedStatement.setString(6, workerEditView.getPosition());
            preparedStatement.setInt(7, workerEditView.getSalary());  // Set as int
            preparedStatement.setLong(8, workerEditView.getIdWorker());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Editing worker failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Editing worker failed due to database operation error.", e);
        }
    }

    // Delete a worker by their ID
    public void deleteWorker(Long workerId) {
        String deleteWorkerSQL = "DELETE FROM bds.worker WHERE id_worker = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteWorkerSQL)) {
            preparedStatement.setLong(1, workerId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Deleting worker failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Deleting worker failed due to database operation error.", e);
        }
    }

    // Map the result set to WorkerBasicView
    private WorkerBasicView mapToWorkerBasicView(ResultSet rs) throws SQLException {
        WorkerBasicView workerBasicView = new WorkerBasicView();
        workerBasicView.setIdWorker(rs.getLong("id_worker"));
        workerBasicView.setFirstName(rs.getString("first_name"));
        workerBasicView.setMiddleName(rs.getString("middle_name"));
        workerBasicView.setLastName(rs.getString("last_name"));
        workerBasicView.setAge(rs.getInt("age"));
        workerBasicView.setGender(rs.getString("gender"));
        workerBasicView.setPosition(rs.getString("position"));
        workerBasicView.setSalary(rs.getInt("salary"));  // Retrieve as int
        return workerBasicView;
    }

    // Map the result set to WorkerDetailView
    private WorkerDetailView mapToWorkerDetailView(ResultSet rs) throws SQLException {
        WorkerDetailView workerDetailView = new WorkerDetailView();
        workerDetailView.setIdWorker(rs.getLong("id_worker"));
        workerDetailView.setFirstName(rs.getString("first_name"));
        workerDetailView.setMiddleName(rs.getString("middle_name"));
        workerDetailView.setLastName(rs.getString("last_name"));
        workerDetailView.setAge(rs.getInt("age"));
        workerDetailView.setGender(rs.getString("gender"));
        workerDetailView.setPosition(rs.getString("position"));
        workerDetailView.setSalary(rs.getInt("salary"));  // Retrieve as int
        return workerDetailView;
    }

}
