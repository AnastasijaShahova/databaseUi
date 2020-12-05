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
import java.util.List;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {
    @FXML
    private TableColumn<Result, Integer> tfKood;
    @FXML
    private TableColumn<Result, String> tfName;
    @FXML
    private TableColumn<Result, Integer> tfPrice;
    @FXML
    private TableColumn<Result, Integer> tfPlan;

    @FXML
    private TableView<Result> resultTable;

    public static List<Result> results;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultTable.setItems(FXCollections.observableList(results));
        tfKood.setCellValueFactory(new PropertyValueFactory<>("detailCode"));
        tfName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tfPlan.setCellValueFactory(new PropertyValueFactory<>("price"));
        tfPrice.setCellValueFactory(new PropertyValueFactory<>("plan"));

        tfKood.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfName.setCellFactory(TextFieldTableCell.forTableColumn());
        tfPlan.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfPrice.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

}
