package bds.data;

import bds.config.DataSourceConfig;
import bds.exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AttackRepository {

    public void createDummyTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS bds.dummy_table";

        String createTableSQL = " CREATE TABLE IF NOT EXISTS bds.dummy_table (id SERIAL PRIMARY KEY,name VARCHAR(255),email VARCHAR(255))" ;

        String insertDummyDataSQL = "INSERT INTO bds.dummy_table ( name, email) VALUES ( ?, ?)" ;

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement createTableStmt = connection.prepareStatement(createTableSQL);
             PreparedStatement insertStmt = connection.prepareStatement(insertDummyDataSQL);
             PreparedStatement dropStmt = connection.prepareStatement(dropTableSQL)) {

            dropStmt.execute();
            createTableStmt.execute();


            insertStmt.setString(1, "Alice");
            insertStmt.setString(2, "alice@example.com");
            insertStmt.executeUpdate();

            insertStmt.setString(1, "Bob");
            insertStmt.setString(2, "bob@example.com");
            insertStmt.executeUpdate();

            insertStmt.setString(1, "Charlie");
            insertStmt.setString(2, "charlie@example.com");
            insertStmt.executeUpdate();

            System.out.println("Dummy table created and populated with test data.");

        } catch (SQLException e) {
            throw new DataAccessException("Failed to create or populate the dummy table.", e);
        }
    }


    public String executeQueryWithoutPreparedStatement(String query) {

        try (Connection connection = DataSourceConfig.getConnection();
             Statement statement = connection.createStatement()) {

             int result = statement.executeUpdate(query);

            return "Executed Query: " + query + "\nResult: " + result + " row(s) affected.";
        } catch (SQLException e) {
            return "Error executing query: " + e.getMessage();
        }
    }
}
