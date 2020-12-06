package ru.vmk.shahova.databaseUi.ui.page;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller1 implements Initializable {
    @FXML
    private TableColumn<Result, Integer> tfKod;
    @FXML
    private TableColumn<Result, String> tfName;
    @FXML
    private TableColumn<Result, Integer> tfPrice;
    @FXML
    private TableColumn<Result, Integer> tfPlan;

    @FXML
    private TableView<Result> resultTable;

    //language=SQL
    private static final String SELECT_BY_DATE = "SELECT sd.kod_details, name_details, price_details, plan " +
            "FROM catalog_details " +
            "         LEFT JOIN supply_details sd on catalog_details.kod_details = sd.kod_details " +
            "         LEFT JOIN contract_supplier cs on sd.number_contract = cs.number_contract " +
            "WHERE data_contract > '01.05.2020'";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultTable.setItems(FXCollections.observableList(getByDate()));
        tfKod.setCellValueFactory(new PropertyValueFactory<>("detailCode"));
        tfName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tfPlan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        tfPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tfKod.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfName.setCellFactory(TextFieldTableCell.forTableColumn());
        tfPlan.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfPrice.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    public List<Result> getByDate() {
        List<Result> resultList = new ArrayList();
        Connection conn = Controller.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(SELECT_BY_DATE);
            Result result;
            while (rs.next()) {
                result = new Result(
                        rs.getInt("kod_details"),
                        rs.getString("name_details"),
                        rs.getInt("price_details"),
                        rs.getInt("plan"));
                resultList.add(result);
            }

        } catch (SQLException throwables) {
            throw new RuntimeException("Error get catalog");
        }
        return resultList;
    }
}
