package ru.vmk.shahova.databaseUi.database.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;


public class DynamicTable extends Application {

    private TableView catalogTableview;
    private TableView contractTableView;
    private TableView supplyTableView;

    private Queue<String> statements = new ArrayDeque<>();

    public static void main(String[] args) {
        launch(args);
    }

    public void buildData() {
        Connection c;
        try {
            c = DBConnect.connect();

            String catalogTable = "catalog_details";
            String contractTable = "contract_supplier";
            String supplyTable = "supply_details";

            fillData(c, catalogTable, catalogTableview);
            fillData(c, contractTable, contractTableView);
            fillData(c, supplyTable, supplyTableView);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    private void fillData(Connection c, String tableName, TableView tableView) throws SQLException {
        String sql = "SELECT * from " + tableName;

        ResultSet rs = c.createStatement().executeQuery(sql);
        ObservableList<ObservableList> data = FXCollections.observableArrayList();


        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {


            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

            col.setCellFactory(TextFieldTableCell.forTableColumn());
            col.setCellValueFactory((Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
            col.setEditable(true);

            col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent>) event -> {
                String update = "UPDATE " + tableName + " SET "
                        + event.getTablePosition().getTableColumn().getText()
                        + "=\'" + event.getNewValue() + "\' WHERE kod_details=" + ((ObservableList )event.getRowValue()).get(0) ;
                statements.add(update);
            });

            tableView.getColumns().addAll(col);
            System.out.println("Column [" + i + "] ");
        }

        while (rs.next()) {

            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            System.out.println("Row [1] added " + row);
            data.add(row);

        }


        tableView.setItems(data);
    }

    @Override
    public void start(Stage stage) throws Exception {

        catalogTableview = new TableView();
        contractTableView = new TableView();
        supplyTableView = new TableView();

        catalogTableview.setEditable(true);
        contractTableView.setEditable(true);
        supplyTableView.setEditable(true);

        buildData();


        BorderPane rootPane = new BorderPane();

        rootPane.setLeft(catalogTableview);
        rootPane.setCenter(supplyTableView);
        rootPane.setRight(contractTableView);

        Button update = new Button("Update");

        update.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent> () {

            @Override
            public void handle(MouseEvent event) {
                while (!statements.isEmpty()) {
                    String statementStr = statements.poll();
                    try {
                        Connection connection = DBConnect.getConnection();
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(statementStr);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });


        Button delete = new Button("delete");



        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.getChildren().addAll(update, delete);
        rootPane.setBottom(hbox);

        Scene scene = new Scene(rootPane);

        catalogTableview.getSelectionModel().selectFirst();

        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();
    }
}

