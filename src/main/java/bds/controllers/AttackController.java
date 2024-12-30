package bds.controllers;

import bds.data.AttackRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class AttackController {

    private AttackRepository attackRepository = new AttackRepository();

    @FXML
    private TextArea normalQueryInput, normalQueryOutput;
    @FXML
    private TextArea dropTableInput, dropTableOutput;
    @FXML
    private TextArea retrieveMoreDataInput, retrieveMoreDataOutput;

    @FXML
    private ListView<String> normalQueries;       // Normal queries
    @FXML
    private ListView<String> dropTableQueries;    // Drop table queries
    @FXML
    private ListView<String> oneToOneQueries;     // 1=1 queries

    public void initialize() {

        attackRepository.createDummyTable();
        normalQueries.getItems().addAll(
                "CREATE TABLE IF NOT EXISTS bds.attack (id SERIAL PRIMARY KEY,name VARCHAR(255),email VARCHAR(255))",
                "CREATE TABLE IF NOT EXISTS bds.dummy_table (id SERIAL PRIMARY KEY,name VARCHAR(255),email VARCHAR(255))",
                "DELETE FROM bds.dummy_table  " + "WHERE 1=1",
                "DELETE FROM bds.attack " + "WHERE 1=1",
                "INSERT INTO bds.attack (name, email) VALUES ('John Doe', 'john.doe@example.com')",
                "INSERT INTO bds.dummy_table (name, email) VALUES ('John Doe', 'john.doe@example.com')",
                "DROP TABLE " + "  bds.dummy_table; DROP TABLE bds.attack; --"
                   // Select based on salary condition
        );

    }

    @FXML
    public void insertNormalQuery(MouseEvent event) {
        String selectedQuery = normalQueries.getSelectionModel().getSelectedItem();
        if (selectedQuery != null) {
            normalQueryInput.setText(selectedQuery);
        }
    }


    public void executeNormalQuery() {
        String query = normalQueryInput.getText();
        String result = attackRepository.executeQueryWithoutPreparedStatement(query);
        normalQueryOutput.setText(result);
    }


}
