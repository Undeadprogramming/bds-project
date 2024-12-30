package bds.data;

import bds.api.*;
import bds.config.DataSourceConfig;
import bds.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothesRepository {

    public void editClothes(ClothesEditView clothesEditView) {
        String updateClothesSQL = "UPDATE bds.clothes SET clothes_name = ?, clothes_type = ?, clothes_colour = ?, clothes_quantity = ?, clothes_size = ?, clothes_price = ? WHERE id_clothes = ?";

        try (Connection connection = DataSourceConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement updateStmt = connection.prepareStatement(updateClothesSQL)) {
                updateStmt.setString(1, clothesEditView.getClothesName());
                updateStmt.setString(2, clothesEditView.getClothesType());
                updateStmt.setString(3, clothesEditView.getClothesColour());
                updateStmt.setInt(4, clothesEditView.getClothesQuantity());
                updateStmt.setString(5, clothesEditView.getClothesSize());
                updateStmt.setDouble(6, clothesEditView.getClothesPrice());
                updateStmt.setLong(7, clothesEditView.getIdClothes());

                int affectedRows = updateStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Updating clothes failed, no rows affected.");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Error updating clothes. Changes rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating clothes: Operation failed.", e);
        }
    }

    public List<ClothesBasicView> getClothesBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT clothes_name, clothes_type, clothes_colour, clothes_quantity, clothes_size, clothes_price, id_clothes " +
                             "FROM bds.clothes");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<ClothesBasicView> clothesBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                clothesBasicViews.add(mapToClothesBasicView(resultSet));
            }
            return clothesBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Clothes basic view could not be loaded.", e);
        }
    }


    public void createClothes(ClothesCreateView clothesCreateView) {
        String insertClothesSQL = "INSERT INTO bds.clothes (clothes_name, clothes_type, clothes_colour, clothes_quantity, clothes_size, clothes_price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertClothesSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, clothesCreateView.getClothesName());
            preparedStatement.setString(2, clothesCreateView.getClothesType());
            preparedStatement.setString(3, clothesCreateView.getClothesColour());
            preparedStatement.setInt(4, clothesCreateView.getClothesQuantity());
            preparedStatement.setString(5, clothesCreateView.getClothesSize());
            preparedStatement.setDouble(6, clothesCreateView.getClothesPrice());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating clothes failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Creating clothes failed, operation on the database failed.", e);
        }
    }

    public void deleteClothes(Long idClothes) {
        String deleteClothesSQL = "DELETE FROM bds.clothes WHERE id_clothes = ?";
        try (Connection connection = DataSourceConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteClothesSQL)) {
                deleteStmt.setLong(1, idClothes);

                int affectedRows = deleteStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new DataAccessException("Deleting clothes failed, no rows affected.");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Error deleting clothes. Changes rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting clothes: Operation failed.", e);
        }
    }


    private ClothesBasicView mapToClothesBasicView(ResultSet rs) throws SQLException {
        ClothesBasicView clothesBasicView = new ClothesBasicView();
        clothesBasicView.setClothesName(rs.getString("clothes_name"));
        clothesBasicView.setClothesType(rs.getString("clothes_type"));
        clothesBasicView.setClothesColour(rs.getString("clothes_colour"));
        clothesBasicView.setClothesQuantity(rs.getInt("clothes_quantity"));
        clothesBasicView.setClothesSize(rs.getString("clothes_size"));
        clothesBasicView.setClothesPrice(rs.getDouble("clothes_price"));
        clothesBasicView.setIdClothes(rs.getLong("id_clothes"));
        return clothesBasicView;
    }
}
