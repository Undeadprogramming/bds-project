package bds.data;

import bds.api.*;
import bds.config.DataSourceConfig;
import bds.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkplaceRepository {

    public void editWorkplace(WorkplaceEditView workplaceEditView) {
        String updateWorkplaceSQL = "UPDATE bds.workplace SET city = ?, building_address = ?, floor = ?, seat_placement = ?, id_worker = ? WHERE id_workplace = ?";

        try (Connection connection = DataSourceConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement updateStmt = connection.prepareStatement(updateWorkplaceSQL)) {
                updateStmt.setInt(1, workplaceEditView.getCity());
                updateStmt.setString(2, workplaceEditView.getBuildingAddress());
                updateStmt.setInt(3, workplaceEditView.getFloor());
                updateStmt.setString(4, workplaceEditView.getSeatPlacement());
                updateStmt.setInt(5, workplaceEditView.getIdWorker());
                updateStmt.setLong(6, workplaceEditView.getIdWorkplace());

                int affectedRows = updateStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Updating workplace failed, no rows affected.");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Error updating workplace. Changes rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating workplace: Operation failed.", e);
        }
    }

    public List<WorkplaceBasicView> getWorkplaceBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT city, building_address, floor, seat_placement, id_worker, id_workplace " +
                             "FROM bds.workplace");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<WorkplaceBasicView> workplaceBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                workplaceBasicViews.add(mapToWorkplaceBasicView(resultSet));
            }
            return workplaceBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Workplace basic view could not be loaded.", e);
        }
    }

    public void createWorkplace(WorkplaceCreateView workplaceCreateView) {
        String insertWorkplaceSQL = "INSERT INTO bds.workplace (city, building_address, floor, seat_placement, id_worker) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertWorkplaceSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, workplaceCreateView.getCity());
            preparedStatement.setString(2, workplaceCreateView.getBuildingAddress());
            preparedStatement.setInt(3, workplaceCreateView.getFloor());
            preparedStatement.setString(4, workplaceCreateView.getSeatPlacement());
            preparedStatement.setInt(5, workplaceCreateView.getIdWorker());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating workplace failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Creating workplace failed, operation on the database failed.", e);
        }
    }

    public void deleteWorkplace(Long idWorkplace) {
        String deleteWorkplaceSQL = "DELETE FROM bds.workplace WHERE id_workplace = ?";
        try (Connection connection = DataSourceConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteWorkplaceSQL)) {
                deleteStmt.setLong(1, idWorkplace);

                int affectedRows = deleteStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Deleting workplace failed, no rows affected.");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Error deleting workplace. Changes rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting workplace: Operation failed.", e);
        }
    }

    private WorkplaceBasicView mapToWorkplaceBasicView(ResultSet rs) throws SQLException {
        WorkplaceBasicView workplaceBasicView = new WorkplaceBasicView();
        workplaceBasicView.setCity(rs.getInt("city"));
        workplaceBasicView.setBuildingAddress(rs.getString("building_address"));
        workplaceBasicView.setFloor(rs.getInt("floor"));
        workplaceBasicView.setSeatPlacement(rs.getString("seat_placement"));
        workplaceBasicView.setIdWorker(rs.getInt("id_worker"));
        workplaceBasicView.setIdWorkplace(rs.getLong("id_workplace"));
        return workplaceBasicView;
    }
}
